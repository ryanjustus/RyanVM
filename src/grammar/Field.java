package grammar;

import compiler.Constants;
import compiler.Op;
import compiler.Operation;
import grammar.error.SemanticException;
import grammar.expression.*;
import parser.KXIParser;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 1/29/13 6:01 PM
 */
public class Field{
	public final boolean isPublic;
	public final String lexeme;
	public final Type type;
	public final IExpression rhs;
	final KXIParser.Class_member_declarationContext context;
	final ClassDeclaration parent;


	public Field(ClassDeclaration clazz, KXIParser.Class_member_declarationContext context) {
		this.parent=clazz;
		this.context=context;
		isPublic = context.MODIFIER().getText().equals("public");
		lexeme = context.ID().getText();
		//Check if array
		boolean array = context.field_declaration().LSQUARE()!=null;
		type = new Type(array,context.type());

		if(context.field_declaration().assignment_expression()!=null){
			this.rhs = parseAssignment(context.field_declaration().assignment_expression());
		}else{
			this.rhs=null;
		}
	}



	public IExpression parseAssignment(KXIParser.Assignment_expressionContext context){
		final IExpression rhs;
		if(context.NEW()!=null){
			final Type t;
			if(context.new_declaration().LSQUARE()!=null) {
				t = new Type(true, context.type());
			}else{
				t = new Type(false, context.type());
			}
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

	public List<Operation> buildCode(){
		List<Operation> assembly = new ArrayList<Operation>();
		if(rhs!=null){
			assembly.addAll(ExpressionBuilder.buildCode(rhs));
			int offset = parent.getOffset(lexeme);
			Operation refThis = new Operation(Op.MOV,"R0","FP");
			assembly.add(refThis);
			Operation refThis2 = new Operation(Op.ADI,"R0",3* Constants.STACK_ELEMENT_SIZE);
			assembly.add(refThis2);
			Operation loadThis = new Operation(Op.LDR,"R0","R0");
			assembly.add(loadThis);
			Operation refVar = new Operation(Op.ADI,"R0",offset*Constants.HEAP_ELEMENT_SIZE);
			assembly.add(refVar);
			Operation popVal = new Operation(Op.POP,"R1");
			assembly.add(popVal);
			Operation save = new Operation(Op.STR,"R0","R1");
			assembly.add(save);
		}
		return assembly;
	}

	public List<SemanticException> validateSemantics(List<Type> types)
	{
		List<SemanticException> errors = new ArrayList<SemanticException>();
		if(!parent.getCompilationUnit().typeDefined(type)){
			errors.add(new SemanticException(context,"Undefined type: "+type));
		}
		if(rhs!=null){
			errors.addAll(rhs.validateSemantics(types));
			if(!type.equals(rhs.getType())){
				errors.add(new SemanticException( context, "Can't assign "+rhs.getType()+" to "+type));
			}
		}
		return errors;
	}
}
