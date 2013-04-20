package grammar.expression;

import compiler.Constants;
import compiler.Op;
import compiler.Operation;
import compiler.Precedence;
import grammar.*;
import grammar.error.SemanticException;
import org.antlr.v4.runtime.ParserRuleContext;
import parser.KXIParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/24/13 5:44 PM
 */
public class FunctionCall extends ExpressionSkeleton implements Precedence{
	public final IExpression id;
	public final List<IExpression> args;

	private boolean lhs = false;

	//Keeps track of if we are chained
	 public ClassDeclaration cd = null;

	public FunctionCall(KXIParser.Fn_arr_memberContext context, Scope parent, IExpression id){
		super(context,parent);
		this.id=id;
		this.args = ExpressionBuilder.parseArgumentList(parent,context.argument_list(),lhs);
	}

	public String getLabel(){
		if(cd == null){
			return getClassDeclaration().getName()+"_"+id.toString();
		}else{
			return cd.getName()+"_"+id.toString();
		}
	}

	public int getNumArgs(){
		return args.size();
	}

	public void setClassDeclaration(ClassDeclaration cd){
		this.cd = cd;
	}


	public Type getType() {
		return parent.getClassDeclaration().getReturnType(id.toString());
	}

	public List<SemanticException> validateSemantics(List<Type> types) {

		List<SemanticException> errors = new ArrayList<SemanticException>();
		errors.addAll(id.validateSemantics(types));

		for(IExpression e: args){
			errors.addAll(e.validateSemantics(types));
		}
		if(!errors.isEmpty()){
			return errors;
		}

		ClassDeclaration cd = parent.getClassDeclaration();
		if(!cd.callMatches(this)){
			errors.add(new SemanticException(context,"No matching method signature for call "+cd.getType()+"."+toString()));
		}

		return errors;
	}

	public void shunt(Stack ops, Queue args){
		args.add(this);
	}

	public List<Operation> buildCode(){

		setClassDeclaration(parent.getClassDeclaration());
		List<Operation> assembly = new ArrayList<Operation>();

		//Save the current stack pointer, this will become FP
		/*
		Operation saveSP = new Operation(Op.MOV,"R3","SP");
		assembly.add(saveSP);
		saveSP.comment += " "+", tmp save SP";
		*/

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
		This t = new This(context,parent);
		List<Operation> pushCaller = t.buildCode();
		pushCaller.get(0).comment = "push caller to stack (this -> FP+4)";
		assembly.addAll(pushCaller);

		for(IExpression arg: args){
			assembly.addAll(ExpressionBuilder.buildCode(arg));
		}

		//Set FP
		Operation saveFP1 = new Operation(Op.MOV,"FP","SP");
		assembly.add(saveFP1);
		Operation addOffset = new Operation(Op.ADI,"FP",-1*Constants.STACK_ELEMENT_SIZE*(args.size()+3));
		addOffset.comment = " set FP";
		assembly.add(addOffset);

		/*
		Operation setFP = new Operation(Op.MOV,"FP","R3");
		assembly.add(setFP);
		Operation incFP = new Operation(Op.ADI,"FP",Constants.STACK_ELEMENT_SIZE);
		assembly.add(incFP);
		incFP.comment = " set FP";
		*/

		//Call function
		String label = getLabel();
		Operation jsr = new Operation(Op.JMP,label); //Jump to the sub-routine
		assembly.add(jsr);

		return assembly;
	}

	public String toString(){
		return id+context.getText();
	}

	public void setLHS(boolean lhs) {
		this.lhs = lhs;
		for(IExpression arg:args){
			arg.setLHS(lhs);
		}
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
