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

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/23/13 4:25 PM
 */
public class IfStatement implements Statement {
	final IExpression ifExpr;
	final Statement ifStmt;
	final Statement elseStmt;
	final boolean hasElse;
	final MethodBody parent;
	final ParserRuleContext context;

	public IfStatement(MethodBody parent, KXIParser.StatementContext context){
		this.context=context;
		this.parent = parent;
		ifExpr = ExpressionBuilder.build(parent,context.expression(),false);
		ifStmt = StatementBuilder.build(parent,context.statement(0));
		if(context.ELSE()!=null){
			hasElse=true;
			elseStmt = StatementBuilder.build(parent, context.statement(1));
		}else{
			hasElse=false;
			elseStmt = null;
		}
	}

	public MethodBody getParent() {
		return parent;
	}

	public List<SemanticException> validateSemantics(List<Type> types) {
		List<SemanticException> errors = new ArrayList<SemanticException>();
		errors.addAll(ifExpr.validateSemantics(types));
		if(ifExpr.getType()!=null){
			if(!ifExpr.getType().equals(Type.Primitive.BOOL.type())){
				errors.add(new SemanticException(context,"If Expression must be type bool"));
			}
			errors.addAll(ifStmt.validateSemantics(types));
			if(hasElse){
				errors.addAll(elseStmt.validateSemantics(types));
			}
		}else{
			errors.add(new SemanticException(ifStmt.getContext(),"Non boolean type for if expression"));
		}
		return errors;
	}

	public boolean returns() {
		if(elseStmt!=null){
			return ifStmt.returns() && elseStmt.returns();
		}else{
			if(ifExpr instanceof BooleanPrimitive && ((BooleanPrimitive) ifExpr).value){
				return ifStmt.returns();
			}
		}

		return false;
	}

	public ParserRuleContext getContext() {
		return context;
	}

	public List<Operation> buildCode() {
		List<Operation> ops = new ArrayList<Operation>();

		String elseLabel = parent.getCompilationUnit().getNextLabel();
		String endLabel = parent.getCompilationUnit().getNextLabel();

		//Evaluate conditional
		List<Operation> ifExprOps = ExpressionBuilder.buildCode(ifExpr);
		ifExprOps.get(0).comment = "if("+ifExpr.toString()+")";
		ops.addAll(ifExprOps);

		//Branch code
		Operation popCond = new Operation(Op.POP,"R0");
		ops.add(popCond);
		Operation brCond;
		if(hasElse){
			brCond = new Operation(Op.BRZ,"R0",elseLabel);
		}else{
			brCond = new Operation(Op.BRZ,"R0",endLabel);
		}
		ops.add(brCond);

		//Add in if statement
		List<Operation> ifStmtOps = ifStmt.buildCode();
		ops.addAll(ifStmtOps);

		if(hasElse){
			//Add jump to end op if there is an else
			Operation jmpEnd = new Operation(Op.JMP,endLabel);
			ops.add(jmpEnd);

			List<Operation> elseOps = elseStmt.buildCode();
			elseOps.get(0).label = elseLabel;
			ops.addAll(elseOps);
		}

		//Dummy Operation at end to jump to
		Operation dummy = new Operation(Op.ADI,"R0",0);
		dummy.label = endLabel;
		if(hasElse){
			dummy.comment = "else stmt";
		}else{
			dummy.comment = "if stmt end";
		}
		ops.add(dummy);

		return ops;
	}

}
