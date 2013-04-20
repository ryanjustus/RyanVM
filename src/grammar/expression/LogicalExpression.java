package grammar.expression;

import compiler.Op;
import grammar.Scope;
import grammar.Type;
import grammar.error.SemanticException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/24/13 4:16 PM
 */
public class LogicalExpression extends ExpressionSkeleton {

	public enum Symbol{
		AND("&&"),
		OR("||");

		public final String lexeme;
		private Symbol(String symbol){
			this.lexeme=symbol;
		}

		public static Symbol parse(String symbol){
			for(Symbol s: Symbol.values()){
				if(s.lexeme.equals(symbol)){
					return s;
				}
			}
			throw new IllegalStateException("UNKNOWN ARITHMETIC SYMBOL: "+symbol);
		}
	}

	public final IExpression left;
	public final IExpression right;
	public final Symbol s;

	private boolean lhs = false;

	public LogicalExpression(ParserRuleContext context, Scope parent, IExpression left, TerminalNode symbol,IExpression right){
		super(context,parent);
		this.left=left;
		this.right=right;
		this.s = Symbol.parse(symbol.getText());
	}

	public Type getType() {
		return Type.Primitive.BOOL.type();
	}

	public void setLHS(boolean lhs) {
		this.lhs = lhs;
		left.setLHS(lhs);
		right.setLHS(lhs);
	}


	public List<SemanticException> validateSemantics(List<Type> types) {
		List<SemanticException> errors = new ArrayList<SemanticException>();
		errors.addAll(left.validateSemantics(types));
		errors.addAll(right.validateSemantics(types));
		if(!left.getType().equals(Type.Primitive.BOOL.type())){
			errors.add(new SemanticException(context,"Expected type bool, found "+left.getType()));
		}
		if(!right.getType().equals(Type.Primitive.BOOL.type())){
			errors.add(new SemanticException(context,"Expected type bool, found "+right.getType()));
		}
		return errors;
	}

	public void shunt(Stack ops, Queue args) {
		Op op = Op.fromSymbol(s.lexeme);
		ExpressionBuilder.binaryShunt(left, right, op, ops, args);
	}

	public String toString(){
		return left.toString() +" "+s + " "+right.toString();
	}
}
