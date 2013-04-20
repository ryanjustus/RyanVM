package grammar.expression;

import compiler.Constants;
import compiler.Op;
import compiler.Operation;
import grammar.*;
import grammar.error.SemanticException;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/10/13 5:16 PM
 */
public class This extends ExpressionSkeleton {

	public boolean lhs = false;
	public ClassDeclaration clazz;

	/*
	public This(ParserRuleContext context, Scope parent, ClassDeclaration clazz){
		super(context,parent);
		this.clazz=clazz;
	}
	*/

	public This(ParserRuleContext context, Scope parent){
		super(context,parent);
	}

	public void setClass(ClassDeclaration clazz){
		this.clazz=clazz;
	}

	public Type getType() {
		return super.getClassDeclaration().getType();
	}

	public List<SemanticException> validateSemantics(List<Type> types) {
		return Collections.emptyList();
	}

	public void shunt(Stack ops, Queue args) {
		args.add(this);
	}

	public String toString(){
		return "this";
	}

	/**
	 * Code to reference 'this' in the current frame on the stack, push to top of stack
	 * @return
	 */
	public List<Operation> buildCode(){
		List<Operation> assembly = new ArrayList<Operation>();
		//Reference 'this' on the current stack frame, push it onto stack
		Operation ref = new Operation(Op.MOV,"R0","FP");
		ref.comment = "Pushing 'this' onto stack";
		assembly.add(ref);
		Operation ref2 = new Operation(Op.ADI,"R0",3* Constants.STACK_ELEMENT_SIZE); //this is in offset 3
		assembly.add(ref2);

		if(!lhs){
			Operation ref5  = new Operation(Op.LDR,"R0","R0"); //Load the value at address R0 into R0
			assembly.add(ref5);
		}

		Operation push = new Operation(Op.PUSH,"R0");
		push.comment = "'this' on top of stack";
		assembly.add(push);
		return assembly;
	}

	public void setLHS(boolean lhs) {
		this.lhs = lhs;
	}
}
