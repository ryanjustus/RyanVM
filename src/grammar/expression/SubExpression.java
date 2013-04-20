package grammar.expression;

import com.sun.javaws.OperaSupport;
import compiler.Op;
import grammar.MethodBody;
import grammar.Scope;
import grammar.Type;
import grammar.error.SemanticException;
import org.antlr.v4.runtime.ParserRuleContext;
import parser.KXIParser;

import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/24/13 3:46 PM
 */
public class SubExpression extends ExpressionSkeleton {

	final IExpression child;

	private boolean lhs =false;

	public SubExpression(KXIParser.ExpressionContext context,Scope parent, boolean lhs){
		super(context,parent);
		child = ExpressionBuilder.build(parent,context,lhs);
	}

	public Type getType() {
		return child.getType();
	}

	public List<SemanticException> validateSemantics(List<Type> types) {
		return child.validateSemantics(types);
	}

	public void shunt(Stack ops, Queue args) {

		//Add the left paren onto the stack
		ops.push(Op.LPAREN);

		//Shunt the subexpression
		child.shunt(ops,args);

		//We are now at the right paren
		while( 	ops.peek()!=Op.LPAREN )
		{
			Object o = ops.pop();

			if(o!= Op.LPAREN){
				args.add(o);
			}
		}
		Object lparen = ops.pop();


	}

	public String toString(){
		return "("+child.toString()+")";
	}

	public void setLHS(boolean lhs) {
		this.lhs = lhs;
		child.setLHS(lhs);
	}
}
