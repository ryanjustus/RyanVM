package grammar.expression;


import com.sun.org.apache.xpath.internal.Arg;
import compiler.Op;
import grammar.*;
import grammar.error.SemanticException;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 1/29/13 5:35 PM
 */
public interface IExpression {
	public Type getType();
	public Scope getParent();
	public List<SemanticException> validateSemantics(List<Type> types);
	public ParserRuleContext getContext();
	public CompilationUnit getCompilationUnit();
	public ClassDeclaration getClassDeclaration();
	public void shunt(Stack ops, Queue args);
	public void setLHS(boolean lhs);
}
