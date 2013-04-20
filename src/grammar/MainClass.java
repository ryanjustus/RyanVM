package grammar;

import grammar.error.SemanticException;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/10/13 5:43 PM
 */
public class MainClass implements Scope {
	final CompilationUnit parent;
	final List<Field> fields = Collections.EMPTY_LIST;
	final List<Method> methods = Collections.EMPTY_LIST;

	public MainClass(CompilationUnit parent){
		this.parent=parent;
	}

	public Scope getParent() {
		return parent;
	}

	public boolean exists(String var) {
		return false;
	}

	public boolean isPublic(String var) {
		return false;
	}

	public Type getType(String var) {
		return null;
	}

	public List<SemanticException> validateSemantics(List<Type> types) {
		return Collections.EMPTY_LIST;
	}

	public ClassDeclaration getClassDeclaration() {
		return null;
	}

	public CompilationUnit getCompilationUnit() {
		return parent;
	}

	public String getName(){
		return "Main";
	}

	public String getLabel() {
		return parent.getLabel();
	}
}
