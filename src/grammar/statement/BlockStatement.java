package grammar.statement;

import compiler.Operation;
import grammar.*;
import grammar.error.SemanticException;
import org.antlr.v4.runtime.ParserRuleContext;
import parser.KXIParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/23/13 4:25 PM
 */
public class BlockStatement implements Statement,Scope {

	final MethodBody parent;
	final List<Statement> statements;
	private final ParserRuleContext context;

	public BlockStatement(MethodBody parent, KXIParser.StatementContext context){
		this.context=context;
		this.parent=parent;
		this.statements = new ArrayList<Statement>();
		for(KXIParser.StatementContext c: context.statement()){
			statements.add(StatementBuilder.build(parent,c));
		}
	}

	public MethodBody getParent() {
		return parent;
	}

	public boolean exists(String var) {
		return parent.exists(var);
	}

	public boolean isPublic(String var) {
		return parent.isPublic(var);
	}

	public Type getType(String var) {
		return parent.getType(var);
	}

	public List<SemanticException> validateSemantics(List<Type> types) {
		List<SemanticException> errors = new ArrayList<SemanticException>();
		for(Statement s: statements){
			errors.addAll(s.validateSemantics(types));
		}
		return errors;
	}

	public boolean returns() {
		for(Statement s: statements){
			if(s.returns()){
				return true;
			}
		}
		return false;
	}

	public ParserRuleContext getContext() {
		return context;
	}

	public List<Operation> buildCode() {
		List<Operation> ops = new ArrayList<Operation>();
		for(Statement s: statements){
			ops.addAll(s.buildCode());
		}
		return ops;
	}

	public ClassDeclaration getClassDeclaration() {
		Scope parent = getParent();
		while(!(parent instanceof ClassDeclaration)) {
			parent = parent.getParent();
		}
		return (ClassDeclaration)parent;
	}

	public CompilationUnit getCompilationUnit() {
		return getClassDeclaration().getCompilationUnit();
	}

	public String getName() {
		return parent.getName()+".{}";
	}

	public String getLabel() {
		return "";
	}
}
