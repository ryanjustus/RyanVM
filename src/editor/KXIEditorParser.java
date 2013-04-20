package editor;

import compiler.Operation;
import grammar.CompilationUnit;
import grammar.error.SemanticException;
import grammar.error.SyntaxException;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.TokenMaker;
import org.fife.ui.rsyntaxtextarea.parser.ParseResult;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import parser.KXICompiler;
import ryanvm.RyanVM;
import ryanvm.assembler.ParserException;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/11/13 1:46 PM
 */
class KXIEditorParser extends org.fife.ui.rsyntaxtextarea.parser.AbstractParser{

	final RTextScrollPane sp;
	final RTextArea output;
	final JButton run;
	public KXIEditorParser(RTextScrollPane sp, RTextArea output, JButton run){
		this.run = run;
		this.sp=sp;
		this.output = output;
	}

	public ParseResult parse(RSyntaxDocument rsd, String text) {

		String program = "";

		KXIEditorParseResult pr = new KXIEditorParseResult(this);

		sp.getTextArea().getHighlighter().removeAllHighlights();
		pr.clearNotices();
		run.setEnabled(false);
		try {
			program = rsd.getText(0, rsd.getLength());
		} catch (BadLocationException ex) {
			Logger.getLogger(KXIEditorParser.class.getName()).log(Level.SEVERE, null, ex);
		}
		final LineOffsets lineOffsets = new LineOffsets(program);

		//For now at least syntax exceptions only appear one at a time.

		StringBuilder output = new StringBuilder();
		CompilationUnit cu = KXICompiler.compileSyntax(program);
		if(!cu.parserExceptions.isEmpty()){
			output.append("SYNTAX EXCEPTIONS DETECTED: \n");
		}
		for(SyntaxException err: cu.parserExceptions){
			pr.addNotice(new KXIEditorParserNotice(this,lineOffsets,err));
			output.append(err.getMessage()).append("\n");
		}

		if(cu.parserExceptions.isEmpty()){
			cu.validateSemantics();
		}

		if(!cu.semanticExceptions.isEmpty()){
			output.append("SEMANTIC EXCEPTIONS DETECTED: \n");
		}

		for(SemanticException err: cu.semanticExceptions){
			//System.out.println("SHOWING SEMANTIC ERROR: "+err.getMessage());
			//System.out.println("SHOWING SEMANTIC ERROR: "+err.getMessage());
			pr.addNotice(new KXIEditorParserNotice(this,lineOffsets,err));
			output.append(err.getMessage()).append("\n");
		}


		if(cu.parserExceptions.isEmpty() && cu.semanticExceptions.isEmpty()){
			//Compile and run

			Class<?> clazz = null;
			try {
				clazz = Class.forName("editor.AssemblyTextAreaTokenMaker");
				TokenMaker tm = (TokenMaker)clazz.newInstance();
				RSyntaxDocument doc = (RSyntaxDocument)this.output.getDocument();
				doc.setSyntaxStyle(tm);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			} catch (InstantiationException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			} catch (IllegalAccessException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}

			List<Operation> optimized = CompilationUnit.optimize(cu.generateCode());
			List<Operation> target = CompilationUnit.convertToTarget(
					optimized
			);


			for(Operation t: target){
				output.append(t.toString()).append("\n");
			}

			run.setEnabled(true);

			/*
			try {
				RyanVM.runProgram(sb.toString(), System.in, System.out);
			} catch (ParserException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			} catch (IOException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
			*/
		}else{
			RSyntaxDocument doc = (RSyntaxDocument)this.output.getDocument();
			doc.setSyntaxStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
		}

		this.output.setText(output.toString());
		this.output.setCaretPosition(0);

		pr.setParsedLines(0,program.split("\n").length);

		return pr;
	}
}
