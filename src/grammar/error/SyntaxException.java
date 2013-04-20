package grammar.error;

import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/11/13 2:23 PM
 */
public class SyntaxException extends Exception{

	public final String message;
	public final int line;
	public final int offset;
	private RecognitionException exception;

	public SyntaxException(RecognitionException ex, String message){
		super();
		this.message=message;
		this.exception=ex;
		if(ex.getOffendingToken()!=null){
			this.line=ex.getOffendingToken().getLine();
			this.offset=ex.getOffendingToken().getCharPositionInLine();
		}else{
			line = 0;
			offset = 0;
		}
	}

	public SyntaxException(int line, int offset, String message){
		this.message=message+", "+line+":"+offset;
		this.line=line;
		this.offset=offset;
	}


	@Override
	public String getMessage(){
		if(exception!=null){
			Token bad = exception.getOffendingToken();
			if(bad!=null){
				return exception.getMessage()+ " at " +bad.getLine()+"."+bad.getCharPositionInLine();
			}else{
				return exception.getMessage();
			}
		}else{
			return message;
		}
	}
}
