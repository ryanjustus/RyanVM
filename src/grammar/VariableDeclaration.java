package grammar;

import compiler.Constants;
import compiler.Op;
import compiler.Operation;
import grammar.error.SemanticException;
import grammar.expression.*;
import grammar.statement.Statement;
import org.antlr.v4.runtime.ParserRuleContext;
import parser.KXIParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 1/29/13 5:24 PM
 */
public class VariableDeclaration implements Statement {
	final MethodBody parent;
	final String lexeme;
	final Type t;
	final IExpression rhs;
	final KXIParser.Variable_declarationContext context;
	public VariableDeclaration(MethodBody parent, KXIParser.Variable_declarationContext context){
		this.context=context;
		this.parent=parent;
		boolean isArray = (context.LSQUARE()!=null);
		t = new Type(isArray,context.type());
		lexeme = context.ID().getText();

		if(context.ASSIGN()!=null){
			rhs = parseAssignment(parent,context.assignment_expression());
		}else{
			rhs=null;
		}
	}

	public IExpression parseAssignment(Scope parent, KXIParser.Assignment_expressionContext context){
		final IExpression rhs;
		if(context.NEW()!=null){
			Type t = new Type(false, context.type());
			rhs = new NewDeclaration(context.new_declaration(),parent,t);
		}else if(context.KEYFUNC()!=null){
			rhs = new PrimitiveFunction(context,parent,context.KEYFUNC().getText(),
					ExpressionBuilder.build(parent,context.expression(),false)
			);
		}else{
			rhs = ExpressionBuilder.build(parent,context.expression(),false);
		}
		return rhs;
	}

	public MethodBody getParent() {
		return parent;
	}

	public List<SemanticException> validateSemantics(List<Type> types) {
		List<SemanticException> errors = new ArrayList<SemanticException>();
		if(!parent.getCompilationUnit().typeDefined(t)){
			errors.add(new SemanticException(context,"Undefined type: "+t));
		}
		if(rhs!=null){
			errors.addAll(rhs.validateSemantics(types));
			if(rhs.getType()!=null && !rhs.getType().equals(Type.Primitive.NULL.type()) && !rhs.getType().equals(t)){
				errors.add(new SemanticException(context,"Can't assign "+rhs.getType()+" to "+t.toString()+" "+lexeme));
			}
		}
		return errors;
	}

	public boolean returns() {
		return false;
	}

	public ParserRuleContext getContext() {
		return context;
	}

	public List<Operation> buildCode() {
		List<Operation> assembly = new ArrayList<Operation>();
		//R5 has current offset
		if(rhs!=null){
			assembly.addAll(ExpressionBuilder.buildCode(rhs));
		}else{
			//Allocate space on stack for this variable
			Operation allocateSpace = new Operation(Op.ADI,"SP", Constants.STACK_ELEMENT_SIZE);
			allocateSpace.comment += " Allocate stack space for "+this.lexeme;
			assembly.add(allocateSpace);
		}
		return assembly;
	}
}
