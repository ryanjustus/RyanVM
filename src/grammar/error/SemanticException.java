package grammar.error;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/10/13 3:58 PM
 */
public class SemanticException extends RuntimeException {

	public final ParserRuleContext context;
	public final String message;

	public SemanticException(ParserRuleContext context, String message){
		super();
		this.context=context;
		this.message=message;
	}

	@Override
	public String getMessage(){
		return message+ "@" +context.getStart().getLine()+":"+context.getStart().getCharPositionInLine();
	}
}
