package grammar.expression;

import compiler.Constants;
import compiler.Op;
import compiler.Operation;
import compiler.Precedence;
import grammar.MethodBody;
import grammar.Scope;
import grammar.Type;
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
 * Time: 2/24/13 5:59 PM
 */
public class ArrayIndex extends ExpressionSkeleton implements Precedence {
	final IExpression left;
	final IExpression index;
	boolean lhs = false;

	public ArrayIndex(KXIParser.ExpressionContext context, Scope parent,IExpression left){
		super(context,parent);
		this.left=left;
		this.index=ExpressionBuilder.build(parent,context,lhs);
	}

	public Type getType() {
		Type t = left.getType();
		Type dereferenced = new Type(false,t.lexeme,t.primitive);
		return dereferenced;
	}

	public List<SemanticException> validateSemantics(List<Type> types) {
		List<SemanticException> errors = new ArrayList<SemanticException>();
		//Check index
		errors.addAll(index.validateSemantics(types));
		if(!left.getType().isArray){
			errors.add(new SemanticException(left.getContext(),"Can't index non array"));
		}
		if(!index.getType().equals(Type.Primitive.INT.type())){
			errors.add(new SemanticException(index.getContext(),"Array index must be type int, found "+index.getType()));
		}

		errors.addAll(left.validateSemantics(types));
		return errors;
	}

	public List<Operation> buildCode(){
		List<Operation> assembly = new ArrayList<Operation>();

		//Push left onto stack
		assembly.addAll(ExpressionBuilder.buildCode(left));
		//Deref

		index.setLHS(false);
		assembly.addAll(ExpressionBuilder.buildCode(index));

		Operation popIdx = new Operation(Op.POP,"R1");
		popIdx.comment = "Array index "+index;
		assembly.add(popIdx);

		Operation eleSize = new Operation(Op.LDR,"R0","ZERO");
		assembly.add(eleSize);

		int size = Constants.HEAP_ELEMENT_SIZE;

		Operation eleSize3 = new Operation(Op.ADI,"R0",size);
		assembly.add(eleSize3);



		Operation mul = new Operation(Op.MUL,"R1","R0");
		mul.comment = "R1 now has memory offset in array";
		assembly.add(mul);

		Operation popLeft = new Operation(Op.POP,"R0");
		assembly.add(popLeft);

		//Dereference left
		if(lhs){
			Operation derefLeft = new Operation(Op.LDR,"R0","R0");
			assembly.add(derefLeft);
			derefLeft.comment = "R0 now has "+left.toString().split("\\[")[0]+"[0]";
		}


		Operation addIdx = new Operation(Op.ADD,"R0","R1");
		assembly.add(addIdx);

		//Dereference right
		if(!lhs){
			Operation deref;
			if(getType().equals(Type.Primitive.CHAR.type())){
				deref = new Operation(Op.LDR,"R0","R0");
			}else{
				deref = new Operation(Op.LDR,"R0","R0");
			}
			deref.comment = "Dereference "+this.toString();
			assembly.add(deref);
		}


		Operation push = new Operation(Op.PUSH,"R0");
		assembly.add(push);
		return assembly;
	}

	public void shunt(Stack ops, Queue args) {
		args.add(this);
	}

	public String toString(){
		return left.toString()+"["+index.toString()+"]";
	}

	public boolean lessOrEqual(Precedence other) {
		return Op.INDEX.lessOrEqual(other);
	}

	public void setLHS(boolean lhs) {
		this.lhs = lhs;
		this.left.setLHS(lhs);
		this.index.setLHS(false);
	}

	public int getPrecedence() {
		return Op.INDEX.getPrecedence();
	}

}
