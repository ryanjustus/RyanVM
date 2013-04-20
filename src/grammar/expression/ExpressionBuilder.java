package grammar.expression;

import com.sun.javaws.OperaSupport;
import com.sun.xml.internal.bind.v2.model.core.ID;
import compiler.Op;
import compiler.Operation;
import compiler.Precedence;
import grammar.ClassDeclaration;
import grammar.MethodBody;
import grammar.Scope;
import grammar.Type;
import parser.KXIParser;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/24/13 3:36 PM
 */
public class ExpressionBuilder {

	public static IExpression build(Scope parent, KXIParser.ExpressionContext context, boolean lhs){
		final IExpression left;
		if(context.LPAREN()!=null){
			left = new SubExpression(context.expression(),parent,lhs);
		}else if(context.BOOLEAN()!=null){
			left = new BooleanPrimitive(context,parent,context.BOOLEAN());
		}else if(context.NULL()!=null){
			left = new NullLiteral(context,parent);
		} else if(context.CHAR()!=null){
			left = new CharacterLiteral(context, parent,context.CHAR());
		}else if(context.ID()!=null || context.THIS()!=null){
			left = parseReference(context,parent);
		}else if(context.INT()!=null){
			left = new NumericLiteral(context,parent,context.INT());
		}else{
			throw new IllegalStateException("Building expression error: "+context.getText());
		}
		if(context.expressionz()!=null){
			return parseExpressionz(parent, left, context.expressionz(),lhs);
		}else{
			return left;
		}
	}


	public static IExpression parseReference(KXIParser.ExpressionContext context, Scope parent){
		//
		IExpression id;
		if(context.ID()!=null){
			id = new Identifier(context,null,parent,context.ID());
		}else{
			id = new This(context,parent);
		}

		int assign=0;
		IExpression left = id;
		if(context.fn_arr_member()!=null){
			//Set the proper scope for left
			left = parseFnArrMember(parent,id ,context.fn_arr_member());
		}

		if(context.member_refz()!=null){
			ClassDeclaration caller = null;
			//Locate the type of the variable
			//Check if parent is a MethodBody
			Scope s = left.getParent();
			if(s instanceof MethodBody){

				MethodBody body = (MethodBody)s;
				Type t = body.getType(id.toString());
			}
			left = parseMemberRefz(parent,context.member_refz(),left);
		}
		return left;
	}


	public static IExpression parseExpressionz(Scope parent, IExpression left, KXIParser.ExpressionzContext context, boolean lhs){

		IExpression expr;
		if(context.ASSIGN()!=null){
			expr = new AssignmentExpression(context.assignment_expression(),parent,left);
		}else if(context.LOGIC()!=null){
		 	expr = new LogicalExpression(context,parent,left, context.LOGIC(),build(parent, context.expression(),lhs));
		}else if(context.BOOL_EXPR()!=null){
			expr = new BooleanExpression(context,parent,left, context.BOOL_EXPR(), build(parent, context.expression(),lhs));
		}else if(context.MATH()!=null){
			expr = new ArithmeticExpression(context,parent,left, context.MATH(), build(parent, context.expression(),lhs));
		}else{
			throw new IllegalStateException("Unknown expression context: "+context.getText());
		}
		return expr;
	}


	public static IExpression parseFnArrMember(Scope parent, IExpression left, KXIParser.Fn_arr_memberContext context) {
		if(context.LPAREN()!=null){
			return new FunctionCall(context,parent,left);
		}else if(context.LSQUARE()!=null){
			return new ArrayIndex(context.expression(),parent,left);
		}else{
			throw new IllegalStateException("Unmatched fnArrMember: "+context.getText());
		}
	}

	public static List<IExpression> parseArgumentList(Scope parent, KXIParser.Argument_listContext context, boolean lhs) {
		if(context==null){
			return Collections.EMPTY_LIST;
		}
		List<IExpression> expressions = new ArrayList<IExpression>(context.expression().size());
		for(KXIParser.ExpressionContext c: context.expression()){
			expressions.add(build(parent,c,lhs));
		}
		return expressions;
	}


	private static IExpression parseMemberRefz(Scope parent, KXIParser.Member_refzContext context, IExpression left) {
		return new MemberReference(context,parent,left);
	}

	public static void binaryShunt(IExpression left, IExpression right, Precedence o, Stack ops, Queue args){

		left.shunt(ops,args);

		while(
			!ops.isEmpty() &&
			(ops.peek() instanceof Precedence) &&
			o.lessOrEqual((Precedence)ops.peek()) &&
			(ops.peek()) != Op.LPAREN
		){
			Precedence op2 = (Precedence)ops.pop();
			args.add(op2);
		}
		//Push op onto stack
		ops.push(o);


		right.shunt(ops,args);
	}

	public static List<Operation> buildCode(IExpression expr){

		List<Operation> assembly = new ArrayList<Operation>();
		LinkedList q = new LinkedList();
		Stack s = new Stack();
		expr.shunt(s,q);
		while(!s.isEmpty()){
			Object o = s.pop();
			q.add(o);
		}

		for(int i=0;i<q.size();i++){
			Object ele = q.get(i);

			if(ele instanceof Operation){
				assembly.add((Operation)ele);
			}else if(ele instanceof Op){
				//For an operation we get the two values from the stack top and apply the op to them
				//We then push the result onto the stack.
				//Pop two args from stack, evaluate

				Operation pop2 = new Operation(Op.POP,"R1");
				pop2.comment = "ARITHMETIC, "+ele;
				assembly.add(pop2);

				Operation pop1 = new Operation(Op.POP,"R0");
			    assembly.add(pop1);

				Operation op = new Operation((Op)ele,"R0","R1");
				assembly.add(op);
				//Push result on stack
				Operation push = new Operation(Op.PUSH,"R0");
				assembly.add(push);

			}else if(ele instanceof BooleanExpression){
				assembly.addAll(((BooleanExpression) ele).buildCode());
			}else if(ele instanceof AssignmentExpression){
				assembly.addAll(((AssignmentExpression) ele).buildCode());
			}else if(ele instanceof ArrayIndex){
				assembly.addAll(((ArrayIndex)ele).buildCode());
			}else if(ele instanceof PrimitiveFunction){
				assembly.addAll(((PrimitiveFunction) ele).buildCode());
			}else if(ele instanceof Identifier){
				assembly.addAll(((Identifier) ele).buildCode());
			}else if(ele instanceof FunctionCall){
				assembly.addAll(((FunctionCall) ele).buildCode());
			}else if(ele instanceof BooleanPrimitive){
				assembly.addAll(((BooleanPrimitive) ele).buildCode());
			}else if(ele instanceof NullLiteral){
				assembly.addAll(((NullLiteral) ele).buildCode());
			}else if(ele instanceof NumericLiteral){
				assembly.addAll(((NumericLiteral) ele).buildCode());
			}else if(ele instanceof CharacterLiteral){
				assembly.addAll(((CharacterLiteral) ele).buildCode());
			}else if(ele instanceof NewDeclaration){
				//At this point we should have all the parameters on the stack
				assembly.addAll(((NewDeclaration)ele).generateCode());
			}

			else if(ele instanceof This){
				This t = (This)ele;
				assembly.addAll(t.buildCode());
			}

			else if(ele instanceof MemberReference){
				List<Operation> memberRefOps = ((MemberReference) ele).buildCode();
				assembly.addAll(memberRefOps);
			}

			else if(ele instanceof Void){
				assembly.addAll(((Void)ele).buildCode());
			}else{
				throw new IllegalStateException("No match from shunting-yard queue:" +ele +"::"+ele.getClass());
			}
		}
		return assembly;
	}

}
