package grammar.expression;

import compiler.Op;
import compiler.Operation;
import grammar.Constant;
import grammar.MethodBody;
import grammar.Scope;
import grammar.Type;
import grammar.error.SemanticException;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Primitive function itoa or atoi
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/10/13 5:29 PM
 */
public class PrimitiveFunction extends ExpressionSkeleton {
	final IExpression expr;
	final String lexeme;

	private boolean lhs = false;

	public PrimitiveFunction(ParserRuleContext context, Scope parent, String lexeme, IExpression e){
		super(context,parent);
		this.lexeme=lexeme;
		this.expr=e;
	}

	public String getLexeme(){
		return lexeme;
	}

	public Type getType() {
	   if(lexeme.equals("atoi")){
		   return new Type(false,"int",true);
	   }else{
		   return new Type(false,"char",true);
	   }
	}

	//TODO fix to make sure proper type matches up
	public List<SemanticException> validateSemantics(List<Type> types) {
		List<SemanticException> errors = new ArrayList<SemanticException>();
		errors.addAll(expr.validateSemantics(types));
		if(!expr.getType().equals(Type.Primitive.CHAR.type())){
			errors.add(new SemanticException(context,"Expected type char, found "+expr.getType()));
		}
		return errors;
	}

	public void setLHS(boolean lhs) {
		this.lhs = lhs;
	}

	public void shunt(Stack ops, Queue args) {
		ops.push(this);

		ops.push(Op.LPAREN);
		//Shunt the subexpression
		expr.shunt(ops,args);

		//We are now at the right paren
		while( 	ops.peek()!=Op.LPAREN )
		{
			Object o = ops.pop();

			if(o!= Op.LPAREN){
				args.add(o);
			}
		}
		Object lparen = ops.pop();
	}

	public List<Operation> buildCode(){
		List<Operation> assembly = new ArrayList<Operation>();
		//Pop the value off the stack
		Operation pop = new Operation(Op.POP,"R0");
		pop.label = toString();
		assembly.add(pop);
		Operation trp;
		if(lexeme.equals("atoi")){
			trp = new Operation(Op.TRP,"11");//atoi
		}else{
			trp = new Operation(Op.TRP,"10");//itoa
		}
		assembly.add(trp);

		//Push value onto top of stack
		Operation push = new Operation(Op.PUSH,"IO");
		assembly.add(push);
		return assembly;
	}

	public String toString(){
		return lexeme+"("+expr+")";
	}
}
