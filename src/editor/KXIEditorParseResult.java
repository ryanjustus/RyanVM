package editor;

import org.fife.ui.rsyntaxtextarea.parser.DefaultParseResult;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/11/13 1:57 PM
 */
class KXIEditorParseResult extends DefaultParseResult{
	KXIEditorParseResult(KXIEditorParser p){
		super(p);
		this.clearNotices();
	}
}
