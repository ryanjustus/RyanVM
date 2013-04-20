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
 * Time: 2/24/13 7:22 PM
 */
public class Void extends ExpressionSkeleton {

	public Void(ParserRuleContext context,Scope parent){
		super(context,parent);
	}

	public Type getType() {
		return Type.Primitive.VOID.type();
	}

	public List<SemanticException> validateSemantics(List<Type> types) {
		return Collections.emptyList();
	}

	public void shunt(Stack ops, Queue args) {
		args.add(this);
	}

	public String toString(){
		return "void";
	}

	public static List<Operation> buildCode(){
		List<Operation> assembly = new ArrayList<Operation>();
		//Push void onto the stack
		Operation loadVoid = new Operation(Op.LDR,"R0","ZERO");
		loadVoid.comment = "push void onto stack";
		assembly.add(loadVoid);

		Operation push = new Operation(Op.PUSH,"R0");
		assembly.add(push);
		return assembly;
	}

	public void setLHS(boolean lhs) {}
}
