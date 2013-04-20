package grammar.statement;

import compiler.Operation;
import grammar.MethodBody;
import grammar.Type;
import grammar.error.SemanticException;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/24/13 3:08 PM
 */
public interface Statement {

	public MethodBody getParent();
	public List<SemanticException> validateSemantics(List<Type> types);
	public boolean returns();
	ParserRuleContext getContext();

	List<Operation> buildCode();


}
