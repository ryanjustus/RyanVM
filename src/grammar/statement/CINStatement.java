package grammar.statement;

import compiler.Constants;
import compiler.Op;
import compiler.Operation;
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
 * Time: 2/23/13 4:33 PM
 */
public class CINStatement implements Statement {
	final IExpression expr;
	public final MethodBody parent;
	public final ParserRuleContext context;
	public CINStatement(MethodBody parent, KXIParser.StatementContext context){
		this.context=context;
		this.parent = parent;
	 	expr = ExpressionBuilder.build(parent,context.expression(),false);
	}

	public MethodBody getParent() {
		return parent;
	}

	public List<SemanticException> validateSemantics(List<Type> types) {
		List<SemanticException> errors = new ArrayList<SemanticException>();
		if(expr.getType()==null){

		}else if(expr.getType().equals(Type.Primitive.CHAR) || expr.getType().equals(Type.Primitive.INT)){
			errors.add(new SemanticException(context,"CIN must assign to char or int"));
		}
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

		List<Operation> ops = new ArrayList<Operation>();
		expr.setLHS(true);
		ops.addAll(ExpressionBuilder.buildCode(expr));
		//The identifier address is now on the top of the stack

		Operation pop = new Operation(Op.POP,"R0");//R0 contains address of target var
		ops.add(pop);
		//Read in IO

		if(expr.getType().equals(Type.Primitive.CHAR.type())){
			Operation read = new Operation(Op.TRP,"4");//Read a char
			ops.add(read);
			Operation adi = new Operation(Op.ADI,"R0",3);
			ops.add(adi);
			Operation save = new Operation(Op.STB,"R0","IO");
			ops.add(save);
		}else{
			Operation read = new Operation(Op.TRP,"2");//Read an int
			ops.add(read);
			Operation save = new Operation(Op.STR,"R0","IO");
			ops.add(save);
		}
		ops.get(0).comment = this.toString();
		return ops;
	}

	public String toString(){
		return "cin >> "+expr.toString();
	}
}
