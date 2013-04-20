package grammar.expression;

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
 * Time: 2/24/13 3:58 PM
 */
public class NumericLiteral extends ExpressionSkeleton {
	public final int value;
	final List<SemanticException> semanticExceptions;
	public NumericLiteral(ParserRuleContext context, Scope parent, TerminalNode value){
		super(context,parent);
		semanticExceptions = new ArrayList<SemanticException>();
		int temp = 0;
		try{
			temp= Integer.parseInt(value.getText());
		}catch(NumberFormatException e){
			semanticExceptions.add(new SemanticException(context,"Integer value "+value.getText()+" out of range: "+Integer.MIN_VALUE+" to "+Integer.MAX_VALUE));
		}
		this.value=temp;
	}

	public Type getType() {
		return Type.Primitive.INT.type();
	}

	public List<SemanticException> validateSemantics(List<Type> types) {
		return semanticExceptions;
	}

	public String toString(){
		return String.valueOf(value);
	}

	public void shunt(Stack ops, Queue args){
		args.add(this);
	}

	public List<Operation> buildCode(){
		List<Operation> assembly = new ArrayList<Operation>();
		//Push value onto stack
		String label;
		if(value == 0){
			label = "ZERO";
		}else if(value ==1){
			label = "ONE";
		}else{
			label = ("I"+value).replaceAll("-","n");
			getCompilationUnit().constants.put(new Constant(value, Constant.Type.INT),label);
		}

		Operation load = new Operation(Op.LDR,"R0",label);
		load.comment = "Push "+ this.toString()+" onto stack";
		assembly.add(load);
		assembly.add(new Operation(Op.PUSH,"R0"));
		return assembly;
	}

	public void setLHS(boolean lhs) {}
}
