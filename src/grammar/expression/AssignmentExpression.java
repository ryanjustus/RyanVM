package grammar.expression;

import compiler.Op;
import compiler.Operation;
import compiler.Precedence;
import grammar.MethodBody;
import grammar.Scope;
import grammar.Type;
import grammar.error.SemanticException;
import org.antlr.v4.runtime.ParserRuleContext;
import parser.KXIParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/24/13 4:53 PM
 */
public class AssignmentExpression extends ExpressionSkeleton implements Precedence{
	final IExpression left;
	final IExpression right;

	public boolean lhs = false;

	public AssignmentExpression(KXIParser.Assignment_expressionContext context,Scope parent, IExpression left){
		super(context,parent);

		this.left=left;
		left.setLHS(true);

		if(context.NEW()!=null){
			right = new NewDeclaration(context.new_declaration(),parent,new Type(false,context.type().getText(),true));
		}else if(context.KEYFUNC()!=null){
			right = new PrimitiveFunction(context,parent,context.KEYFUNC().getText(),ExpressionBuilder.build(parent,context.expression(),false));
		}
		else{
			right = ExpressionBuilder.build(parent,context.expression(),false);
		}
	}

	public Type getType() {
		return left.getType();
	}

	public List<SemanticException> validateSemantics(List<Type> types) {
		List<SemanticException> errors = new ArrayList<SemanticException>();
		errors.addAll(left.validateSemantics(types));
		errors.addAll(right.validateSemantics(types));
		if(errors.isEmpty()){
			if(right.getType()!=null && !right.getType().equals(Type.Primitive.NULL.type()) && !right.getType().equals(left.getType())){
				errors.add(new SemanticException(context,"Can't assign "+right.getType() +" to "+left.getType()));
			}
		}
		return errors;
	}

	public void shunt(Stack ops, Queue args) {
		ExpressionBuilder.binaryShunt(left,right,this,ops,args);
	}

	public List<Operation> buildCode(){
		List<Operation> assembly = new ArrayList<Operation>();
	   	Operation popR = new Operation(Op.POP,"R1");
		popR.comment = "assignment "+this.toString();
		assembly.add(popR);
		Operation popL = new Operation(Op.POP,"R0");
		assembly.add(popL);

		Operation store = new Operation(Op.STR,"R0","R1"); //Store the contents of R1 into address at R0;
		store.comment+=" Store "+right.toString()+" to "+left.toString();

		assembly.add(store);
		return assembly;
	}

	public String toString(){
		return left.toString()+"="+right.toString();
	}

	public boolean lessOrEqual(Precedence other) {
		return Op.ASSIGN.lessOrEqual(other);
	}

	public int getPrecedence() {
		return Op.ASSIGN.getPrecedence();
	}

	public void setLHS(boolean lhs) {
		this.lhs = lhs;
	}
}
