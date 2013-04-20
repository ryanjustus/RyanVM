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
 * Time: 2/24/13 3:20 PM
 */
public class COUTStatement implements Statement{
	final IExpression expr;
	final MethodBody parent;
	final ParserRuleContext context;

	public COUTStatement(MethodBody parent, KXIParser.StatementContext context){
		this.context=context;
		this.parent=parent;
		expr = ExpressionBuilder.build(parent,context.expression(),false);
	}

	public MethodBody getParent() {
		return parent;
	}

	public List<SemanticException> validateSemantics(List<Type> types) {
		List<SemanticException> errors = new ArrayList<SemanticException>();
		Type t = expr.getType();

		if(t==null || t.equals(Type.Primitive.CHAR) || t.equals(Type.Primitive.INT)){
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

		//TODO build COUT Code
		List<Operation> ops = new ArrayList<Operation>();
		ops.addAll(ExpressionBuilder.buildCode(expr));
		//The identifier address is now at the top of the stack

		//If we are an integer convert it to a character for printing
		if(!expr.getType().equals(Type.Primitive.CHAR.type())){
			Operation pop1 = new Operation(Op.LDR,"IO","SP");
			ops.add(pop1);
			Operation pop2 = new Operation(Op.ADI,"SP",-1* Constants.STACK_ELEMENT_SIZE);
			ops.add(pop2);

			Operation print = new Operation(Op.TRP,"1");
			ops.add(print);
		}else{
			Operation mv = new Operation(Op.MOV,"R0","SP");
			ops.add(mv);
			Operation adi = new Operation(Op.ADI,"R0",3);
			ops.add(adi);
			Operation pop1 = new Operation(Op.LDB,"IO","R0");
			ops.add(pop1);

			Operation pop2 = new Operation(Op.ADI,"SP", -1*Constants.STACK_ELEMENT_SIZE);
			ops.add(pop2);

			Operation print = new Operation(Op.TRP,"3");
			ops.add(print);
		}

		return ops;
	}

	public String toString(){
		return "cout << "+this.expr.toString().replaceAll("\n","\\\\n");
	}
}
