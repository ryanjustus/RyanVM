package grammar;

import compiler.Constants;
import compiler.Op;
import compiler.Operation;
import grammar.error.SemanticException;
import grammar.expression.Identifier;
import grammar.statement.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.Tree;
import parser.KXIParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 1/29/13 4:48 PM
 */
public class MethodBody implements Scope{
	final Scope parent;
	public final List<VariableDeclaration> variables;
	public final List<Statement> statements;

	final List<Parameter> methodParams;
	private final ParserRuleContext context;

	public MethodBody(Method parent,KXIParser.Method_bodyContext context){
		this.context=context;
		this.parent= parent;
		statements = new ArrayList<Statement>();
		this.variables = new ArrayList<VariableDeclaration>();
		methodParams= parent.getParameters();
		init(context);
	}

	public MethodBody(Constructor parent,KXIParser.Method_bodyContext context){
		this.context=context;
		this.parent= parent;
		statements = new ArrayList<Statement>();
		this.variables = new ArrayList<VariableDeclaration>();
		methodParams= parent.getParameters();
		init(context);
	}

	private void init(KXIParser.Method_bodyContext body){

		//Parse out all the variable declarations
		for(KXIParser.Variable_declarationContext c: body.variable_declaration()){
			VariableDeclaration var = new VariableDeclaration(this, c);
			variables.add(var);
		}

		//Parse out all the statements
		for(KXIParser.StatementContext context: body.statement()){
			statements.add(StatementBuilder.build(this,context));
		}
	}



	public Scope getParent() {
		return parent;
	}

	public boolean exists(String var) {
		return existsLocal(var) || parent.exists(var);
	}

	/**
	 * returns the frame offset for a given variable on the stack, or -1 if it isn't found
	 * @param var
	 * @return
	 */
	public int getOffset(Identifier var){
		Scope parent = this.getParent();
		List<Parameter> parameters ;
		if(parent instanceof Method){
			parameters = ((Method) parent).getParameters();
		}else{
			parameters = ((Constructor)parent).getParameters();
		}
		return getOffset(var, parameters,variables);
	}

	private int getOffset(Identifier var,List<Parameter> parameters, List<VariableDeclaration> vars){
		int offset = 3;
		if(var.lexeme.equals("this")){
			return offset;
		}
		offset++;
		for(Parameter p: parameters){
			if(p.getId().equals(var.lexeme)){
				return offset;
			}
			offset++;
		}
		for(VariableDeclaration v: vars){
			if(v.lexeme.equals(var.lexeme)){
				return offset;
			}
			offset++;
		}

		return -1;
	}

	public boolean isPublic(String var) {
		if(existsLocal(var)){
			return false;
		}else{
			return parent.isPublic(var);
		}
	}

	public boolean existsLocal(String var){
		for(VariableDeclaration v: variables){
			if(v.lexeme.equals(var)){
				return true;
			}
		}
		return false;
	}

	public List<Operation> generateCode(){

		List<Operation> assembly = new ArrayList<Operation>();


		for(VariableDeclaration v: variables){
			List<Operation> ops = v.buildCode();
			ops.get(0).comment += "variable declaration "+v.lexeme;
			assembly.addAll(ops);
		}

		for(Statement s: statements){
			List<Operation> ops = s.buildCode();
			ops.get(0).comment += " statement "+s.toString();
			assembly.addAll(ops);
		}
		if(!this.hasReturn()){
			//push 'void' onto stack
			assembly.addAll(grammar.expression.Void.buildCode());
		}


		if(assembly.size()==0){
			assembly.add(new Operation(Op.MOV,"R0","R0"));
		}
		assembly.get(0).comment += ", Method body start, "+assembly.get(0).comment;
		return assembly;
	}

	public Type getType(String var) {
		if(existsLocal(var)){
			for(VariableDeclaration v: variables){
				if(v.lexeme.equals(var)){
					return v.t;
				}
			}
		}
		else if(parent.exists(var)){
			return parent.getType(var);
		}
		return null;
	}

	public boolean hasReturn(){
		if(parent instanceof Constructor)
			return true;
		for(Statement s: statements){
			if(s instanceof ReturnStatement){
				return true;
			}
		}
		return false;
	}

	public List<SemanticException> validateSemantics(List<Type> types) {

		List<SemanticException> errors = new ArrayList<SemanticException>();

		Set<String> ids = CompilationUnit.getReserved();
		for(Parameter p: this.methodParams){
			ids.add(p.idLexeme);
		}


		for(VariableDeclaration var: variables){

			//Check if the lexeme is used in the context
			if(ids.contains(var.lexeme)){
				errors.add(new SemanticException(var.context,"Identifier already used: "+var.lexeme));
			}else{
				ids.add(var.lexeme);
			}
			errors.addAll(var.validateSemantics(types));
		}

		for(Statement s: statements){
			errors.addAll(s.validateSemantics(types));
		}

		//Check if we have a return statement
		if(parent instanceof Method){
			Method m = (Method)parent;
			if(!m.getReturnType().equals(Type.Primitive.VOID.type())){
				boolean returnGuaranteed=false;
				for(int i=0;i<statements.size();i++){
					Statement s = statements.get(i);
					if(s.returns()){
						returnGuaranteed=true;
						if(i<statements.size()-1){
							Statement next = statements.get(i+1);
							errors.add(new SemanticException(next.getContext(),"Unreachable Statement"));
						}
						break;
					}
				}
				if(!returnGuaranteed){
					errors.add(new SemanticException(context,"Method does not return "+m.getReturnType()));
				}
			}
		}

		return errors;
	}

	public ClassDeclaration getClassDeclaration() {
		return parent.getClassDeclaration();
	}

	public CompilationUnit getCompilationUnit() {
		return getClassDeclaration().getCompilationUnit();
	}

	public String getName() {
		return parent.getName()+"{}";
	}

	public String getLabel() {
		return (parent==null ? "null" : parent.getLabel());
	}

	public String getLabel(Statement stmt) {
		return getLabel()+"_"+statements.indexOf(stmt);
	}
}
