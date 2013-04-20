package grammar.statement;

import compiler.Op;
import compiler.Operation;
import grammar.MethodBody;
import grammar.Type;
import grammar.error.SemanticException;
import grammar.expression.*;
import grammar.expression.Void;
import org.antlr.v4.runtime.ParserRuleContext;
import parser.KXIParser;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/23/13 4:28 PM
 */
public class ExprStatement implements Statement {
	final IExpression expr;
	final MethodBody parent;
	final ParserRuleContext context;
	public ExprStatement(MethodBody parent, KXIParser.StatementContext context){
		this.context=context;
		this.parent=parent;
		expr = ExpressionBuilder.build(parent,context.expression(),false);
	}

	public IExpression getExpression(){
		return expr;
	}

	public MethodBody getParent() {
		return parent;
	}

	public List<SemanticException> validateSemantics(List<Type> types) {
		List<SemanticException> errors = new ArrayList<SemanticException>();
		errors.addAll(expr.validateSemantics(types));
		return errors;
	}

	public boolean returns() {
		return false;
	}

	public ParserRuleContext getContext() {
		return context;
	}

	public List<Operation> buildCode() {
		return ExpressionBuilder.buildCode(expr);
	}


	public String toString(){
		return expr.toString();
	}
}
