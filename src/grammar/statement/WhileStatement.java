package grammar.statement;

import compiler.Op;
import compiler.Operation;
import grammar.MethodBody;
import grammar.Type;
import grammar.error.SemanticException;
import grammar.expression.BooleanPrimitive;
import grammar.expression.ExpressionBuilder;
import grammar.expression.IExpression;
import org.antlr.v4.runtime.ParserRuleContext;
import parser.KXIParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/23/13 4:25 PM
 */
public class WhileStatement implements Statement {
	public final IExpression expr;
	public final Statement stmt;
	public final MethodBody parent;
	public final ParserRuleContext context;
	public WhileStatement(MethodBody parent, KXIParser.StatementContext context){
		this.context=context;
		expr = ExpressionBuilder.build(parent, context.expression(),false);
		this.parent=parent;
		stmt = StatementBuilder.build(parent, context.statement(0));
	}

	public MethodBody getParent() {
		return parent;
	}

	public List<SemanticException> validateSemantics(List<Type> types) {

		List<SemanticException> errors = new ArrayList<SemanticException>();
		errors.addAll(expr.validateSemantics(types));

		//verify while expression is type boolean
		if(expr.getType()!=null && !expr.getType().equals(Type.Primitive.BOOL.type())){
			errors.add(new SemanticException(context,"while expression must be of type bool"));
		}
		errors.addAll(stmt.validateSemantics(types));
		return errors;
	}

	public boolean returns() {
		if(expr instanceof BooleanPrimitive && ((BooleanPrimitive) expr).value){
			return stmt.returns();
		}
		return false;
	}

	public ParserRuleContext getContext() {
		return context;
	}

	public List<Operation> buildCode() {
		List<Operation> ops = new ArrayList<Operation>();

		String whileLabel = this.parent.getCompilationUnit().getNextLabel();
		String endLabel = this.parent.getCompilationUnit().getNextLabel();

		//Evaluate while conditional
		List<Operation> whileExpr = ExpressionBuilder.buildCode(expr);
		whileExpr.get(0).label = whileLabel;
		ops.addAll(whileExpr);

		//Pop the value off the top of the stack, if it is zero then we jump to end
		Operation popCond = new Operation(Op.POP,"R0");
		ops.add(popCond);
		Operation brCond = new Operation(Op.BRZ,"R0",endLabel);
		ops.add(brCond);

		//Add in the while statement ops
		List<Operation> whileStatement = stmt.buildCode();
		ops.addAll(whileStatement);

		//Add a jmp to the beginning of the while
		Operation jmpWhile = new Operation(Op.JMP,whileLabel);
		ops.add(jmpWhile);

		//Dummy Operation at end to jump to
		Operation dummyEnd = new Operation(Op.ADI,"R0",0);
		dummyEnd.label = endLabel;
		ops.add(dummyEnd);

		return ops;
	}
}
