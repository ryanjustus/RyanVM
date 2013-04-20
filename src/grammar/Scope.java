package grammar;

import grammar.error.SemanticException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/10/13 6:09 PM
 */
public interface Scope {
	public Scope getParent();

	public boolean exists(String var);
	public boolean isPublic(String var);
	public Type getType(String var);

	public List<SemanticException> validateSemantics(List<Type> types);

	public ClassDeclaration getClassDeclaration();
	public CompilationUnit getCompilationUnit();

	public String getName();

	public String getLabel();
}
