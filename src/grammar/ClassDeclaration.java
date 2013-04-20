package grammar;

import compiler.Operation;
import grammar.error.SemanticException;
import grammar.expression.FunctionCall;
import grammar.expression.Identifier;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import parser.KXIParser;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 1/29/13 4:47 PM
 */
public class ClassDeclaration implements Scope {
	public final List<Field> fields;
	public final List<Method> methods;
	public Constructor constructor;
	public final String idLexeme;
	public final Scope parent;
	public final ParserRuleContext context;
	public final List<SemanticException> semanticExceptions = new ArrayList<SemanticException>();

	//Main class (empty container for consistency)
	public ClassDeclaration(CompilationUnit parent){
		fields = Collections.EMPTY_LIST;
		methods = Collections.EMPTY_LIST;
		idLexeme = "Main";
		this.parent=parent;
		this.context = null;
	}


	public ClassDeclaration(CompilationUnit parent, KXIParser.Class_declarationContext context){
		this.context=context;
		this.parent=parent;
   	    idLexeme = context.class_name().ID().getText();
		fields = new ArrayList<Field>();
		methods = new ArrayList<Method>();


		for(KXIParser.Class_member_declarationContext c: context.class_member_declaration()){
			//Check if it is a constructor
			if(c.constructor_declaration()!=null){
				KXIParser.Constructor_declarationContext conContext =
						c.constructor_declaration();
				if(constructor==null){
					constructor = new Constructor(this,conContext);
				}else{
					semanticExceptions.add(new SemanticException(conContext,"Only 1 constructor allowed"));
				}
			}
			//It is a field or method
			else{
				//Get the modifiers
				TerminalNode modifier = c.MODIFIER();
				//Check if it is a field
				if(c.field_declaration().SEMICOLON()!=null){
					Field f = new Field(this,c);
					fields.add(f);
				}
				//It is a method
				else{

					Method m = new Method(this,c);
					methods.add(m);
				}
			}
		}

	}

	public Constructor getConstructor(){
		return constructor;
	}

	public int getElementCount(){
		return this.fields.size();
	}

	public boolean callMatches(FunctionCall function){
		ClassDeclaration parentClass = function.getClassDeclaration();
		boolean isLocal = parentClass.idLexeme.equals(idLexeme);
		for(Method m: methods){
			//Check if name matches
			if(m.lexeme.equals(function.id.toString())){
				//Check public/private
				if(!m.isPublic && !isLocal){ //Private and not local call
					return false;
				}
				//Check if params match
				if(m.getParameters().size()==function.getNumArgs()){
					for(int i=0;i<m.getParameters().size();i++){
						if(!m.parameters.get(i).getType().equals(function.args.get(i).getType())){
							return false;
						}
					}
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}

	public int getOffset(Identifier identifier){
		for(int i=0;i<fields.size();i++){
			if(fields.get(i).lexeme.equals(identifier.lexeme)){
				return i;
			}
		}
		return -1;
	}

	public int getOffset(String identifier){
		for(int i=0;i<fields.size();i++){
			if(fields.get(i).lexeme.equals(identifier)){
				return i;
			}
		}
		return -1;
	}


	public Type getReturnType(String method){
		for(Method m: methods){
			if(m.lexeme.equals(method)){
				return m.getReturnType();
			}
		}
		return null;
	}

	public Type getType(){
		return new Type(false,idLexeme,false);
	}


	public Scope getParent() {
		return parent;
	}

	public boolean isPrivate(String var){
		for(Field f: fields){
			if(f.lexeme.equals(var)){
				return !f.isPublic;
			}
		}
		return false;
	}

	public boolean exists(String var) {
		return existsLocal(var) || parent.exists(var);
	}

	public boolean isPublic(String var) {
		return !isPrivate(var);
	}

	public boolean isMethodPublic(String name){
		for(Method m: methods){
			if(m.lexeme.equals(name)){
				return m.isPublic;
			}
		}
		return false;
	}


	public boolean existsLocal(String var){
		for(Field f: fields){
			if(f.lexeme.equals(var)){
				return true;
			}
		}
		for(Method m: methods){
			if(m.lexeme.equals(var)){
				return true;
			}
		}
		return false;
	}

	public Type getType(String var) {
		if(existsLocal(var)){
			for(Field f: fields){
				if(f.lexeme.equals(var)){
					return f.type;
				}
			}
		}else if(parent.exists(var)){
			return parent.getType(var);
		}
		return null;
	}

	public List<SemanticException> validateSemantics(List<Type> types) {

		//We use this to verify unique identifiers
		Set<String> localIds = CompilationUnit.getReserved();
		localIds.add(idLexeme);
		for(Field f: fields){

			//Check if we have already used the identifier
			if(localIds.contains(f.lexeme)){
				//Throw exception
				semanticExceptions.add(new SemanticException(f.context,"Identifier already defined: "+f.lexeme));
			}else{
				localIds.add(f.lexeme);
			}

			semanticExceptions.addAll(f.validateSemantics(types));
		}
		for(Method m: methods){

			//Check if we have already used the identifier
			if(localIds.contains(m.lexeme)){
				//Throw exception
				semanticExceptions.add(new SemanticException(m.context,"Identifier already defined"));
			}else{
				localIds.add(m.lexeme);
			}

			//Check if the type is good
			Type lhs = m.t;
			if(!types.contains(lhs)){
				semanticExceptions.add(new SemanticException(m.context,"Undefined type: "+lhs.lexeme));
			}
			semanticExceptions.addAll(m.validateSemantics(types));
		}
		if(constructor!=null){
			semanticExceptions.addAll(constructor.validateSemantics(types));
		}
		return semanticExceptions;
	}

	public ClassDeclaration getClassDeclaration() {
		return this;
	}

	public CompilationUnit getCompilationUnit() {
		return (CompilationUnit)this.parent;
	}

	public String getName() {
		return idLexeme;
	}

	public String getLabel() {
		return this.getName();
	}

	public List<Operation> buildFieldInitCode(){
		List<Operation> ops = new ArrayList<Operation>();
		for(Field f: fields){
			ops.addAll(f.buildCode());
		}

		return ops;
	}

	public List<Operation> generateCode() {
		List<Operation> ops = new ArrayList<Operation>();

		if(constructor!=null){
			ops.addAll(constructor.generateCode());
		}

		for(Method m: methods){
			ops.addAll(m.generateCode());
		}
		return ops;
	}
}
