package grammar.statement;

import compiler.Op;
import compiler.Operation;
import grammar.Constructor;
import grammar.Method;
import grammar.MethodBody;
import grammar.Type;
import grammar.error.SemanticException;
import grammar.expression.ExpressionBuilder;
import grammar.expression.IExpression;
import org.antlr.v4.runtime.ParserRuleContext;
import parser.KXIParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/23/13 4:32 PM
 */
public class ReturnStatement implements Statement {
   	final IExpression returnExpr;
	final MethodBody parent;
	final ParserRuleContext context;
	public ReturnStatement(MethodBody parent, KXIParser.StatementContext context){
		this.context=context;
		this.parent = parent;
		if(context.expression()!=null){
			returnExpr = ExpressionBuilder.build(parent, context.expression(),false);
		}else{
			returnExpr = new grammar.expression.Void(context,parent);
		}
	}

	public MethodBody getParent() {
		return parent;
	}

	public List<SemanticException> validateSemantics(List<Type> types) {
		//Validate return type matches parent return type
		List<SemanticException> errors = new ArrayList<SemanticException>();
		if(parent.getParent() instanceof Constructor && !returnExpr.getType().equals(Type.Primitive.VOID)){
			errors.add(new SemanticException(context,"Can't return value from a Constructor"));
		}else if(parent.getParent() instanceof Method){
			Method m = (Method)parent.getParent();
			if(!m.getReturnType().equals(returnExpr.getType())){
				errors.add(new SemanticException(context,
						"Return type mismatch, Expected "+m.getReturnType()+" returned "+returnExpr.getType()));
			}
		}else{
			throw new IllegalStateException("Returning from a non method");
		}
		errors.addAll(returnExpr.validateSemantics(types));
		return errors;
	}

	public boolean returns() {
		return true;
	}

	public ParserRuleContext getContext() {
		return context;
	}

	public List<Operation> buildCode() {
		List<Operation> assembly = ExpressionBuilder.buildCode(this.returnExpr);
		//JMP to end
		Operation jmpEnd = new Operation(Op.JMP,this.getParent().getLabel()+"_END");
		assembly.add(jmpEnd);
		return assembly;
	}
}
