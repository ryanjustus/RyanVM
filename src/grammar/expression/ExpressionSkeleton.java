package grammar.expression;

import grammar.ClassDeclaration;
import grammar.CompilationUnit;
import grammar.Scope;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 3/3/13 2:38 PM
 */
public abstract class ExpressionSkeleton implements IExpression{

	public final ParserRuleContext context;
	public final Scope parent;

	public ExpressionSkeleton(ParserRuleContext context, Scope parent){
		this.context=context;
		this.parent=parent;
	}

	public CompilationUnit getCompilationUnit(){
		Scope parent = this.getParent();
		if(parent==null){
			return null;
		}
		while(!(parent instanceof CompilationUnit)){
			parent = parent.getParent();
		}
		return (CompilationUnit)parent;
	}

	public ClassDeclaration getClassDeclaration(){
		Scope parent = this.getParent();
		if(parent==null){
			return null;
		}
		while(!(parent instanceof ClassDeclaration)){
			parent = parent.getParent();
		}
		return (ClassDeclaration)parent;
	}

	public Scope getParent() {
		return parent;
	}


	public ParserRuleContext getContext() {
		return context;
	}

	public String toString(){
		return context.getText();
	}

}
