package editor;

import grammar.error.SemanticException;
import grammar.error.SyntaxException;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParserNotice;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/11/13 1:59 PM
 */
class KXIEditorParserNotice extends DefaultParserNotice {

	enum Type{
		WARN(Color.YELLOW),
		ERROR(Color.RED);

		private final Color c;
		Type(Color c){
			this.c=c;
		}
		Color getColor(){
			return c;
		}
	}



	public KXIEditorParserNotice(KXIEditorParser parser,LineOffsets offsets, String msg, int line,  int offset, int length){
		super(parser,msg, line, offsets.getStartOffset(line,offset),length);
		//System.err.println("adding bshParserNotice: "+msg);
		setLevel(INFO);
	}

	public KXIEditorParserNotice(KXIEditorParser parser,LineOffsets offsets, SyntaxException ex){
		super(parser,
				//Message
				ex.getMessage(),
				//Line
				ex.line,
				//Offset
				offsets.getStartOffset(ex.line,ex.offset),
				//Length
				10);

		//System.err.println("adding bshParserNotice: " + ex.getMessage());
		setLevel(INFO);
	}

	public KXIEditorParserNotice(KXIEditorParser parser,LineOffsets offsets, SemanticException ex){
		super(parser,
				//Message
				ex.getMessage(),
				//Line
				ex.context.getStart().getLine(),
				//Offset
				ex.context.getStart().getStartIndex(),
				//Length
				(ex.context.getText().length()==0)?2:ex.context.getText().length());

		//System.err.println("adding bshParserNotice: "+ex.getMessage());
		setLevel(INFO);
	}

	@Override
	public Color getColor() {
		return Color.RED;
	}

	@Override
	public String getToolTipText() {
		return super.getMessage();
	}


}
