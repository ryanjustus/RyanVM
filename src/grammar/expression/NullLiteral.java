package grammar.expression;

import compiler.Op;
import compiler.Operation;
import grammar.MethodBody;
import grammar.Scope;
import grammar.Type;
import grammar.error.SemanticException;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/24/13 3:49 PM
 */
public class NullLiteral extends ExpressionSkeleton{

	public NullLiteral(ParserRuleContext context, Scope parent){
		super(context,parent);
	}
	public Type getType() {
		return Type.Primitive.NULL.type();
	}

	public List<SemanticException> validateSemantics(List<Type> types) {
		return Collections.EMPTY_LIST;
	}

	public void shunt(Stack ops, Queue args) {
		args.add(this);
	}

	public List<Operation> buildCode(){
		List<Operation> assembly = new ArrayList<Operation>();
		Operation op = new Operation(Op.LDR,"R0","ZERO");
		op.comment = "Push 'null' onto stack";
		assembly.add(op);
		Operation push = new Operation(Op.PUSH,"R0");
		assembly.add(push);
		return assembly;
	}

	public String toString(){
		return "null";
	}

	public void setLHS(boolean lhs) {}
}
