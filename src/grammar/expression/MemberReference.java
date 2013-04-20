package grammar.expression;

import compiler.Constants;
import compiler.Op;
import compiler.Operation;
import grammar.ClassDeclaration;
import grammar.MethodBody;
import grammar.Scope;
import grammar.Type;
import grammar.error.SemanticException;
import org.antlr.v4.runtime.ParserRuleContext;
import parser.KXIParser;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/24/13 6:10 PM
 */
public class MemberReference extends ExpressionSkeleton{

	public final IExpression head;
	public final List<IExpression> tail;
	public final Identifier id;

	public boolean lhs = false;
	public MemberReference(KXIParser.Member_refzContext context,Scope parent, IExpression left){


		super(context,parent);
		this.head= left;

		tail = new ArrayList<IExpression>();

		id = new Identifier(context,null,parent,context.ID());
		IExpression expr;


		if(context.fn_arr_member()!=null){
			expr = ExpressionBuilder.parseFnArrMember(parent,id,context.fn_arr_member());
		}else{
			expr = id;
		}

		tail.add(expr);
		if(context.member_refz()!=null){
			MemberReference rest = new MemberReference(context.member_refz(),parent,expr);
			tail.addAll(rest.tail);
		}
	}

	public Type getType() {

		Type t = head.getType();
		ClassDeclaration callerClass = getCompilationUnit().getClass(t);
		for(IExpression next: tail){
			if(t.primitive){
				throw new IllegalStateException("Can't chain primitives: " + context.toString());
			}

			if(callerClass == null){
				return null;
			}

			if(next instanceof Identifier){
				Identifier i = (Identifier)next;
				t =   callerClass.getType(i.lexeme);
			}else if(next instanceof FunctionCall){
				FunctionCall f = (FunctionCall)next;
				t = callerClass.getReturnType(f.id.toString());
			}else if(next instanceof ArrayIndex){
				ArrayIndex aIdx = (ArrayIndex)next;
				t = callerClass.getType(aIdx.left.toString());
			}else{
				throw new IllegalStateException("Unexpected next expression: "+next);
			}

			if(!t.primitive){
				callerClass = getCompilationUnit().getClass(t);
			}else{

			}
		}
		return t;
	}

	public List<SemanticException> validateSemantics(List<Type> types) {

		List<SemanticException> errors = new ArrayList<SemanticException>();
		errors.addAll(head.validateSemantics(types));
		IExpression next = head;
		Type t = next.getType();
		ClassDeclaration callerClass = getCompilationUnit().getClass(t);
		for(int i = 0;i<tail.size() && errors.isEmpty(); i++){
			if(callerClass == null){
				errors.add(new SemanticException(next.getContext(),"Caller type,"+t+", cannot be reference for "+next.toString()));
				continue;
			}
			//Check if variable exists
			next = tail.get(i);
			if(next instanceof Identifier){
				Identifier id = (Identifier)next;
				//Same class, private only
				if(callerClass.getName().equals(this.getClassDeclaration().getName())){
					if(callerClass.getOffset(id)<0){
						errors.add(new SemanticException(next.getContext(),"No such field "+id.lexeme+ " in "+callerClass.getName()));
					}
				}
				//Different class
				else{
					if(callerClass.getOffset(id)<0){
						errors.add(new SemanticException(next.getContext(),"No such field "+id.lexeme+ " in "+callerClass.getName()));
					}else if(callerClass.isPrivate(id.lexeme)){
						errors.add(new SemanticException(id.getContext(),"Cannot access "+id.lexeme+" scope is private"));
					}
				}
				t =   callerClass.getType(id.lexeme);
			}else if(next instanceof FunctionCall){
				FunctionCall f = (FunctionCall)next;
				for(IExpression arg: f.args){
					errors.addAll(arg.validateSemantics(types));
				}
				if(!errors.isEmpty()){
					continue;
				}
				if(callerClass.callMatches(f)){
					//Private
					if(!callerClass.getName().equals(this.getClassDeclaration().getName())){
						if(!callerClass.isMethodPublic(f.id.toString())){
							errors.add(new SemanticException(f.getContext(),"Method "+f.id.toString()+" is private in "+callerClass.getName()));
						}
					}
				}else{
					errors.add(new SemanticException(f.getContext(),"No matching method definition for "+f.toString()+" defined in class "+callerClass.getName()));
				}
				t = callerClass.getReturnType(f.id.toString());
			}else if(next instanceof ArrayIndex){
				ArrayIndex aIdx = (ArrayIndex)next;
				errors.addAll(aIdx.index.validateSemantics(types));
				if(!aIdx.index.getType().equals(Type.Primitive.INT.type())){
					errors.add(new SemanticException(aIdx.index.getContext(),"Array index must be of type 'int'"));
				}
				//Same class, private only
				if(callerClass.getName().equals(this.getClassDeclaration().getName())){
					if(callerClass.getOffset(id)<0){
						errors.add(new SemanticException(next.getContext(),"No such field "+id.lexeme+ " in "+callerClass.getName()));
					}
				}
				//Different class
				else{
					if(callerClass.getOffset(id)<0){
						errors.add(new SemanticException(next.getContext(),"No such field "+id.lexeme+ " in "+callerClass.getName()));
					}else if(callerClass.isPrivate(id.lexeme)){
						errors.add(new SemanticException(id.getContext(),"Cannot access "+id.lexeme+" scope is private"));
					}
				}

				t = callerClass.getType(aIdx.left.toString());
			}else{
				throw new IllegalStateException("Unexpected next expression: "+next);
			}

			if(t!=null && !t.primitive){
				callerClass = getCompilationUnit().getClass(t);
			}else{
				callerClass = null;
			}
		}
		return errors;
	}


	public void shunt(Stack ops, Queue args){
		args.add(this);
	}

	public List<Operation> buildCode(){


		List<Operation> assembly = new ArrayList<Operation>();
		//Add the push 'head' is on the stack
		if(this.head instanceof This){
			This t = (This)head;
			assembly.addAll(t.buildCode());
		}else{
			Identifier head = (Identifier)this.head;
			assembly.addAll(head.buildCode());
		}

		Type t = head.getType();
		ClassDeclaration callerClass =  getCompilationUnit().getClass(t);
		for(IExpression next: tail){
			if(next instanceof Identifier){
				Identifier i = (Identifier)next;
				assembly.addAll(buildIdentifier(callerClass, i));
				t =   callerClass.getType(i.lexeme);
			}else if(next instanceof ArrayIndex){
				ArrayIndex aIdx = (ArrayIndex)next;
				assembly.addAll(buildArrayIndex(callerClass,aIdx));
				t = callerClass.getType(aIdx.left.toString());
			}else if(next instanceof FunctionCall){
				FunctionCall f = (FunctionCall)next;
				assembly.addAll(buildFunctionCall(callerClass,f));
				t = callerClass.getReturnType(f.id.toString());
			}else{
				throw new IllegalStateException("Unexpected expression type in ref chain: "+next);
			}

			if(!t.primitive){
				callerClass = getCompilationUnit().getClass(t);
			}else{

			}
		}
		return assembly;
	}

	public List<Operation> buildIdentifier(ClassDeclaration caller, Identifier id){
		//Previous identifier is on the stack
		List<Operation> assembly = new ArrayList<Operation>();
		int index = caller.getOffset(id);
		Operation pop = new Operation(Op.POP,"R0");
		pop.comment = "ref id "+caller.getName()+"."+id.toString();
		assembly.add(pop);
		Operation ref = new Operation(Op.ADI,"R0",index*Constants.HEAP_ELEMENT_SIZE);
		ref.comment = caller.getName()+"."+id+" located at offset "+index;
		assembly.add(ref);

		if(!id.lhs){
			Operation deref = new Operation(Op.LDR,"R0","R0");
			deref.comment = "deref " + caller.getName()+"."+id;
			assembly.add(deref);
		}

		Operation push = new Operation(Op.PUSH,"R0");
		assembly.add(push);
		return assembly;
	}

	public List<Operation> buildArrayIndex(ClassDeclaration caller, ArrayIndex arr){
		//Previous identifier is on the stack
		List<Operation> assembly = new ArrayList<Operation>();
		Operation pop = new Operation(Op.POP,"R0");
		pop.comment = "POP caller "+caller.getName();
		assembly.add(pop);
		//Operation load = new Operation(Op.LDR,"R0","R0");
		//load.comment = "deref caller";
		//assembly.add(load);

		int offset = caller.getOffset(arr.left.toString());
		Operation adi = new Operation(Op.ADI,"R0", offset*Constants.HEAP_ELEMENT_SIZE);
		adi.comment = "ref arr "+caller.getName()+"."+arr.left.toString();
		assembly.add(adi);

		Operation ref = new Operation(Op.LDR,"R0","R0");
		assembly.add(ref);

		Operation push = new Operation(Op.PUSH,"R0");
		assembly.add(push);

		IExpression index = arr.index;
		List<Operation> idxOps = ExpressionBuilder.buildCode(index);
		assembly.addAll(idxOps);

		Operation popIdx = new Operation(Op.POP,"R1");
		assembly.add(popIdx);
		Operation clearR0 = new Operation(Op.LDR,"R0","ZERO");
		assembly.add(clearR0);
		Operation setSize = new Operation(Op.ADI,"R0",Constants.HEAP_ELEMENT_SIZE);
		assembly.add(setSize);
		Operation mult = new Operation(Op.MUL,"R1","R0");
		assembly.add(mult);

		Operation popBase = new Operation(Op.POP,"R0");
		assembly.add(popBase);

		Operation add = new Operation(Op.ADD,"R0","R1");
		assembly.add(add);

		if(!arr.lhs){
			Operation deref = new Operation(Op.LDR,"R0","R0");
			deref.comment= " deref "+arr.toString();
			assembly.add(deref);
		}

		Operation pushResult = new Operation(Op.PUSH,"R0");
		pushResult.comment = " "+arr.toString()+" on stack top";
		assembly.add(pushResult);

		return assembly;
	}

	public List<Operation> buildFunctionCall(ClassDeclaration caller, FunctionCall fun){

		fun.setClassDeclaration(caller);
		List<Operation> assembly = new ArrayList<Operation>();

		//Pop caller from stack
		Operation popCaller = new Operation(Op.POP,"R0");
		popCaller.comment = "pop caller from stack";
		assembly.add(popCaller);


		//Save the current stack pointer, this will become FP
		Operation saveSP = new Operation(Op.MOV,"R3","SP");
		assembly.add(saveSP);
		saveSP.comment += " "+", tmp save SP";

		//Build the frame header
		//Make space for Return val, return addr
		Operation alloc = new Operation(Op.ADI,"SP",2*Constants.STACK_ELEMENT_SIZE);
		alloc.comment = " Allocate space for ret val, ret addr on stack";
		assembly.add(alloc);

		//Save frame pointer to PFP
		Operation pushPFP = new Operation(Op.PUSH,"FP");
		pushPFP.comment = " push PFP";
		assembly.add(pushPFP);

		//Push 'this' for the function call
		Operation pushCaller = new Operation(Op.PUSH,"R0");
		pushCaller.comment = "push caller to stack (this -> FP+4)";
		assembly.add(pushCaller);

		for(IExpression arg: fun.args){
			assembly.addAll(ExpressionBuilder.buildCode(arg));
		}

		//Set FP
		Operation setFP = new Operation(Op.MOV,"FP","R3");
		assembly.add(setFP);
		Operation incFP = new Operation(Op.ADI,"FP",Constants.STACK_ELEMENT_SIZE);
		assembly.add(incFP);
		incFP.comment = " set FP";

		//Call function
		String label = fun.getLabel();
		Operation jsr = new Operation(Op.JMP,label); //Jump to the sub-routine
		assembly.add(jsr);

		return assembly;
	}

	public String toString(){
		String output = head.toString();
		for(IExpression next: tail){
			output+="."+next.toString();
		}
		return output;
	}

	public void setLHS(boolean lhs) {
		this.lhs = lhs;
		this.tail.get(tail.size()-1).setLHS(lhs);
	}
}
