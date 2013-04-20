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
 * Time: 2/24/13 3:49 PM
 */
public class ArithmeticExpression extends ExpressionSkeleton {

	public enum Symbol{
		PLUS("+"),
		MIN("-"),
		MUL("*"),
		DIV("/");
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
	final IExpression left,right;
	final Symbol symbol;

	private boolean lhs = false;

	public ArithmeticExpression(ParserRuleContext context,Scope parent, IExpression left, TerminalNode node, IExpression right){
		super(context,parent);
		this.left=left;
		this.right=right;
		symbol = Symbol.parse(node.getText());
	}


	public Type getType() {
		return Type.Primitive.INT.type();
	}

	public List<SemanticException> validateSemantics(List<Type> types) {
		List<SemanticException> errors = new ArrayList<SemanticException>();
		errors.addAll(left.validateSemantics(types));
		errors.addAll(right.validateSemantics(types));
		if(left.getType()==null || !left.getType().equals(Type.Primitive.INT.type())){
			errors.add(new SemanticException(left.getContext(),"Expected type int, evaluated: "+left.getType()));
		}
		if(!Type.Primitive.INT.type().equals(right.getType())){
			errors.add(new SemanticException(right.getContext(),"Expected type int, evaluated: "+right.getType()));
		}
		return errors;
	}

	public void shunt(Stack ops, Queue args){
		Op op = Op.fromSymbol(symbol.lexeme);
		ExpressionBuilder.binaryShunt(left, right, op, ops, args);
	}

	public void setLHS(boolean lhs) {
		this.lhs = lhs;
		left.setLHS(lhs);
		right.setLHS(lhs);
	}

	public String toString(){
		return left.toString() + symbol.lexeme + right.toString();
	}
}
