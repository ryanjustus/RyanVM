package grammar.expression;

import compiler.Op;
import compiler.Operation;
import grammar.MethodBody;
import grammar.Scope;
import grammar.Type;
import grammar.error.SemanticException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import parser.KXIParser;

import javax.smartcardio.TerminalFactory;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/24/13 3:49 PM
 */
public class BooleanPrimitive extends ExpressionSkeleton {
	public final boolean value;

	public BooleanPrimitive(ParserRuleContext context, Scope parent, TerminalNode value){
		super(context,parent);
		this.value = Boolean.parseBoolean(value.getText());
	}

	public Type getType() {
		return Type.Primitive.BOOL.type();
	}

	public List<SemanticException> validateSemantics(List<Type> types) {
		return Collections.emptyList();
	}

	public void shunt(Stack ops, Queue args) {
		args.add(this);
	}

	public List<Operation> buildCode(){
		List<Operation> assembly = new ArrayList<Operation>();
		//Push value onto stack
		Operation load;
		if(value){
			load = new Operation(Op.LDR,"R0","ONE");
		}else{
			load = new Operation(Op.LDR,"R0","ZERO");
		}
		assembly.add(load);
		load.comment = "Push "+ this.toString()+" onto stack";
		assembly.add(new Operation(Op.PUSH,"R0"));
		return assembly;
	}

	public void setLHS(boolean lhs) {}
}
