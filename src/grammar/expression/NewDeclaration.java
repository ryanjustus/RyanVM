package grammar.expression;

import com.sun.javaws.OperaSupport;
import compiler.Constants;
import compiler.Op;
import compiler.Operation;
import compiler.Precedence;
import grammar.*;
import grammar.error.SemanticException;
import org.antlr.v4.runtime.ParserRuleContext;
import parser.KXIParser;
import sun.reflect.generics.tree.ClassTypeSignature;

import java.nio.ReadOnlyBufferException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/10/13 5:10 PM
 */
public class NewDeclaration extends ExpressionSkeleton implements Precedence{

	final List<IExpression> arguments;
	final Type type;
	final boolean isArray;


	public NewDeclaration(KXIParser.New_declarationContext context,Scope parent, Type type){
		super(context,parent);
		this.type = type;
		if(context.LPAREN()!=null){
			isArray = false;
			this.arguments = ExpressionBuilder.parseArgumentList(parent, context.argument_list(),false);
		}
		//Array declaration
		else{
			arguments = new ArrayList();
			isArray=true;
			IExpression arrCount = ExpressionBuilder.build(parent,context.expression(),false);
			arguments.add(arrCount);
		}
	}

	public Type getType() {
		type.isArray = isArray;
		return type;
	}

	public void setLHS(boolean lhs) {

	}

	public List<SemanticException> validateSemantics(List<Type> types) {
		List<SemanticException> errors = new ArrayList<SemanticException>();

		if(isArray){
			IExpression index = arguments.get(0);
			errors.addAll(index.validateSemantics(types));
			if(!index.getType().equals(Type.Primitive.INT.type())){
				errors.add(new SemanticException(context,"Array index must be of type int, found "+index.getType()));
			}
		}else{
			//Check parameters
			List<Type> signature = new ArrayList<Type>();
			for(IExpression e: arguments){
				errors.addAll(e.validateSemantics(types));
				signature.add(e.getType());
			}
			//Validate constructor signature matches
			ClassDeclaration classType = super.getCompilationUnit().getClass(type);
			Constructor constructor;
			if(classType == null){
				errors.add(new SemanticException(context,"No valid constructr does not match parameters"));
			}else{
				constructor = classType.getConstructor();
				if(!constructor.signatureMatches(signature)){
					errors.add(new SemanticException(context,"Constructor " + constructor + " does not match parameters"));
				}
			}

		}
		return errors;
	}

	public void shunt(Stack ops, Queue args) {
		ops.push(this);
	}


	//Allocates data on the heap then pushes the heap address onto the stack
	public List<Operation> generateCode(){
		List<Operation> ops = new ArrayList<Operation>();
		if(isArray){
            ops.addAll(buildNewArray());
		}else{
			ops.addAll(buildNewObject());
		}
		return ops;
	}

	public List<Operation> buildNewObject(){

		List<Operation> ops = new ArrayList<Operation>();

		//Push the "this" reference onto the heap, then increment heap location
		//Save the previous heap into 'this' on stack frame (FP+3)

		//Push return 'this' as return value
		Operation pushThis = new Operation(Op.PUSH,"HP");
		ops.add(pushThis);
		pushThis.comment=" push 'this' into ret val";


		//Add space for return address
		Operation addRetAddr = new Operation(Op.ADI,"SP",Constants.STACK_ELEMENT_SIZE);
		ops.add(addRetAddr);
		addRetAddr.comment = " allocate space for ret addr";

		//Set PFP
		Operation pushPFP = new Operation(Op.PUSH,"FP");
		ops.add(pushPFP);
		pushPFP.comment = " push PFP";

		//Push heap into 'this' on stack
		Operation pushHeap = new Operation(Op.PUSH,"HP");
		ops.add(pushHeap);
		pushHeap.comment=" push 'this' FP+3";

		//Calculate size necessary on heap
		int size = this.getCompilationUnit().getClass(this.type).getElementCount();
		if(size==0){
			System.out.println(this.getClassDeclaration().idLexeme);
			System.out.println("SIZE ZERO OBJECT!!");
			size=1;
		}

		//Increment the heap
		Operation incHeap = new Operation(Op.ADI,"HP",size*Constants.HEAP_ELEMENT_SIZE);
		ops.add(incHeap);
		incHeap.comment = " increment HP by 4*"+size;

		for(IExpression arg: this.arguments){
			ops.addAll(ExpressionBuilder.buildCode(arg));
		}

		Operation saveFP1 = new Operation(Op.MOV,"FP","SP");
		ops.add(saveFP1);
		Operation addOffset = new Operation(Op.ADI,"FP",-1*Constants.STACK_ELEMENT_SIZE*(arguments.size()+3));
		addOffset.comment = " set FP";
		ops.add(addOffset);


		//Jump to subroutine
		String label = getCompilationUnit().getClass(type).getLabel();
		label+="_"+label;
		Operation jsr = new Operation(Op.JMP,label); //Jump to the sub-routine
		ops.add(jsr);

		return ops;
	}

	private List<Operation>  buildNewArray(){
		List<Operation> ops = new ArrayList<Operation>();
		Operation pushHp = new Operation(Op.PUSH,"HP");
		ops.add(pushHp);

		ops.addAll(ExpressionBuilder.buildCode(arguments.get(0)));

		//Pop off size
		Operation arrSize = new Operation(Op.POP,"R1");
		arrSize.comment = "Allocating array "+toString()+" on heap";
		ops.add(arrSize);

		Operation clearR0 = new Operation(Op.LDR,"R0","ZERO");
		ops.add(clearR0);

		Operation setSize1 = new Operation(Op.ADI,"R0", Constants.HEAP_ELEMENT_SIZE);
		setSize1.comment = "size of "+type.lexeme;
		ops.add(setSize1);
		Operation setSize2 = new Operation(Op.MUL,"R0","R1");
		ops.add(setSize2);

		Operation incHeap = new Operation(Op.ADD,"HP","R0");
		ops.add(incHeap);
		return ops;
	}

	private String getLabel() {
		return this.getClassDeclaration().getLabel();
	}

	public String toString(){
		return type+context.getText();
	}

	@Override
	public boolean lessOrEqual(Precedence other) {
		return Op.CALL.lessOrEqual(other);
	}

	@Override
	public int getPrecedence() {
		return Op.CALL.getPrecedence();
	}
}
