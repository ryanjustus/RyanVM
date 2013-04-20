package grammar.expression;

import compiler.Op;
import compiler.Operation;
import compiler.Precedence;
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
 * Time: 2/24/13 4:46 PM
 */
public class BooleanExpression extends ExpressionSkeleton implements Precedence{


	public enum Symbol{
		EQUAL("=="),
		NEQUAL("!="),
		GTE(">="),
		LTE("<="),
		LT("<"),
		GT(">");

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
			throw new IllegalStateException("UNKNOWN BOOLEAN SYMBOL: "+symbol);
		}
	}
	public final Symbol symbol;
	public final Op op;
	public final IExpression left, right;

	private boolean lhs = false;

	public BooleanExpression(ParserRuleContext context,Scope parent, IExpression left, TerminalNode symbol, IExpression right){
		super(context,parent);
		this.symbol = Symbol.parse(symbol.getText());
		this.left=left;
		this.right=right;
		op = Op.fromSymbol(this.symbol.lexeme);
	}
	public Type getType() {
		return Type.Primitive.BOOL.type();
	}

	public boolean lessOrEqual(Precedence other) {
		return op.lessOrEqual(other);
	}

	public int getPrecedence() {
		return op.getPrecedence();
	}

	public List<SemanticException> validateSemantics(List<Type> types) {
		List<SemanticException> errors = new ArrayList<SemanticException>();
		errors.addAll(left.validateSemantics(types));
		errors.addAll(right.validateSemantics(types));
		if(symbol==Symbol.EQUAL || symbol==Symbol.NEQUAL ){
			//Check that lhs and rhs are the same
			if(left.getType() != null &&
				!left.getType().equals(Type.Primitive.NULL.type()) &&
				!right.getType().equals(Type.Primitive.NULL.type()) &&
				!left.getType().equals(right.getType())){
				errors.add(new SemanticException(context,"lhs is "+left.getType()+", right is "+right.getType()));
			}
		}else{
			//Must be numeric
			if(left.getType()!= null && !left.getType().equals(Type.Primitive.INT.type())){
				errors.add(new SemanticException(left.getContext(),"expected int, found "+left.getType()));
			}
			if(right.getType()!= null && !right.getType().equals(Type.Primitive.INT.type())){
				errors.add(new SemanticException(right.getContext(),"expected int, found "+right.getType()));
			}
		}
		return errors;
	}

	public void shunt(Stack ops, Queue args) {
		ExpressionBuilder.binaryShunt(left, right, this, ops, args);
	}

	public List<Operation> buildCode(){
		List<Operation> ops = new ArrayList<Operation>();
		String trueLabel = getCompilationUnit().getNextLabel();
		String falseLabel = getCompilationUnit().getNextLabel();

		Operation pop1 = new Operation(Op.POP,"R1");
		pop1.comment = "POP "+this.right.toString();
		ops.add(pop1);
		Operation pop0 = new Operation(Op.POP,"R0");
		pop0.comment = "POP "+this.left.toString();
		ops.add(pop0);

		Operation cmp = new Operation(Op.CMP,"R0","R1");
		cmp.comment = "Compare";
		ops.add(cmp);

		switch(symbol){
			case EQUAL:{
				Operation branch = new Operation(Op.BRZ,"R0",trueLabel);
				ops.add(branch);
			}break;

			case NEQUAL:{
				Operation branch   = new Operation(Op.BNZ,"R0",trueLabel);
				ops.add(branch);
			}break;

			case LTE:{
				Operation blt = new Operation(Op.BLT,"R0",trueLabel);
				ops.add(blt);
				Operation brz = new Operation(Op.BRZ,"R0",trueLabel);
				ops.add(brz);
			}break;

			case LT:{
				Operation branch = new Operation(Op.BLT,"R0",trueLabel);
				ops.add(branch);
			}break;

			case GT:{
				Operation branch = new Operation(Op.BGT,"R0",trueLabel);
				ops.add(branch);
			}break;

			case GTE:{
				Operation bgt = new Operation(Op.BGT,"R0",trueLabel);
				ops.add(bgt);
				Operation brz = new Operation(Op.BRZ,"R0",trueLabel);
				ops.add(brz);
			}break;
		}

		Operation setFalse = new Operation(Op.LDR,"R0","ZERO");
		ops.add(setFalse);
		Operation jmp = new Operation(Op.JMP,falseLabel);
		ops.add(jmp);

		Operation setTrue = new Operation(Op.LDR,"R0","ONE");
		setTrue.label = trueLabel;
		ops.add(setTrue);

		Operation push = new Operation(Op.PUSH,"R0");
		push.label = falseLabel;

		ops.add(push);
		return ops;
	}

	public String toString(){
		return left.toString()+" " + op.name() + " " + right.toString();
	}

	public void setLHS(boolean lhs) {
		this.lhs = lhs;
	}
}
