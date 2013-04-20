package grammar.expression;

import compiler.Constants;
import compiler.Op;
import compiler.Operation;
import grammar.Constant;
import grammar.MethodBody;
import grammar.Scope;
import grammar.Type;
import grammar.error.SemanticException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import parser.KXIParser;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/24/13 3:50 PM
 */
public class CharacterLiteral extends ExpressionSkeleton{
	public final char value;

	public CharacterLiteral(ParserRuleContext context,Scope parent, TerminalNode character){
		super(context,parent);

		if(character.getText().equals("'\\n'")){
			value = '\n';
		}else{
			value = character.getText().charAt(1);
		}
		//value = character.getText().charAt(1);
	}

	public Type getType() {
		return Type.Primitive.CHAR.type();
	}

	public List<SemanticException> validateSemantics(List<Type> types) {
		return Collections.emptyList();
	}

	public void shunt(Stack ops, Queue args) {
		args.add(this);
	}

	public String toString(){
		return String.valueOf(value);
	}

	public List<Operation> buildCode(){
		List<Operation> assembly = new ArrayList<Operation>();
		//Push value onto stack
		String label = "C"+(int)value;

		String v;
		if(value < 'A' || (value > 'Z' && value < 'a') || value > 'z'){
			v = String.format("0x"+"%1$02x", (value & 0xFF));
		}else{
			v = "\'"+value+"\'";
		}

		getCompilationUnit().constants.put(new Constant(v, Constant.Type.CHAR),label);

		Operation load = new Operation(Op.LDB,"R0",label);
		load.comment = "Push "+ label +" onto stack";
		assembly.add(load);

		Operation incStack = new Operation(Op.ADI,"R31", Constants.STACK_ELEMENT_SIZE);
		assembly.add(incStack);

		Operation mv = new Operation(Op.MOV,"R1","R31");
		assembly.add(mv);

		Operation adi = new Operation(Op.ADI,"R1",3);
		assembly.add(adi);

		Operation str = new Operation(Op.STB,"R1","R0");
		assembly.add(str);

		return assembly;
	}

	public void setLHS(boolean lhs) {}

}
