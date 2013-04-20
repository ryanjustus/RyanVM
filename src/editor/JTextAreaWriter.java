package editor;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.security.PublicKey;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 4/16/13 4:07 PM
 */
public class JTextAreaWriter extends Writer {

	final JTextArea textArea;

	public JTextAreaWriter(JTextArea textArea){
		this.textArea = textArea;
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		textArea.append(new String(cbuf, off, len));
	}

	@Override
	public void flush() throws IOException {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void close() throws IOException {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
