package editor;

import editor.jconsole.JConsole;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rtextarea.*;
import ryanvm.RyanVM;
import ryanvm.assembler.ParserException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/11/13 1:17 PM
 */
public class KXITextEditor extends JFrame implements ActionListener{

	final JTextField searchField;
	final RSyntaxTextArea textArea;
	final RSyntaxTextArea output;
	final JConsole inout;

	public KXITextEditor() throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		JPanel cp = new JPanel(new BorderLayout());
		JSplitPane outer = new JSplitPane(JSplitPane.VERTICAL_SPLIT);


		JSplitPane mainPain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		outer.add(mainPain);

		cp.add(outer);



		textArea = new RSyntaxTextArea();
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);


		Class<?> clazz = Class.forName("editor.KXIRSyntaxTextAreaTokenMaker");
		TokenMaker tm = (TokenMaker)clazz.newInstance();
		RSyntaxDocument doc = (RSyntaxDocument)textArea.getDocument();
		doc.setSyntaxStyle(tm);



		RTextScrollPane left = new RTextScrollPane(textArea);
		mainPain.add(left);

		JPanel outputPanel = new JPanel(new BorderLayout());

		JToolBar toolBar = new JToolBar();


		final JButton run = new JButton("Run");
		run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				//System.out.println("Running program: "+output.getText());
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						run.setEnabled(false);
						inout.attach();
						try {
							RyanVM.runProgram(output.getText(),System.in,System.out);
						} catch (ParserException e1) {
							e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
						} catch (IOException e1) {
							e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
						}
						inout.unattach();
						run.setEnabled(true);
					}
				});
				t.start();

			}
		});
		run.setEnabled(false);
		run.setBackground(Color.GREEN);
		run.setSize(30,40);
		toolBar.add(run);



		searchField = new JTextField(30);
		toolBar.add(searchField);

		final JButton prevButton = new JButton("Find Previous");
		prevButton.setActionCommand("OutputFindPrev");
		prevButton.addActionListener(this);
		toolBar.add(prevButton);

		final JButton nextButton = new JButton("Find Next");
		nextButton.setActionCommand("OutputFindNext");
		nextButton.addActionListener(this);
		toolBar.add(nextButton);
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextButton.doClick(0);
			}
		});




		outputPanel.add(toolBar,BorderLayout.NORTH);


		output = new RSyntaxTextArea();
		output.setEditable(false);

		RTextScrollPane rightSP = new RTextScrollPane(output);
		outputPanel.add(rightSP,BorderLayout.CENTER);

		mainPain.add(outputPanel);

		textArea.addParser(new KXIEditorParser(left,output,run));

		inout = new JConsole();
		outer.add(inout);


		setContentPane(cp);

		outer.setDividerLocation(500);
		mainPain.setDividerLocation(700);
		setTitle("KXI Text Editor");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		this.setSize(1400, 800);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		// Start all Swing applications on the EDT.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new KXITextEditor().setVisible(true);
				} catch (ClassNotFoundException ex) {
					Logger.getLogger(KXITextEditor.class.getName()).log(Level.SEVERE, null, ex);
				} catch (InstantiationException ex) {
					Logger.getLogger(KXITextEditor.class.getName()).log(Level.SEVERE, null, ex);
				} catch (IllegalAccessException ex) {
					Logger.getLogger(KXITextEditor.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// "FindNext" => search forward, "FindPrev" => search backward
		//System.out.println("Searching for text: "+searchField.getText());
		String command = e.getActionCommand();
		RSyntaxTextArea searchArea;
		if(command.startsWith("Output")){
			searchArea = output;
		}else{
			searchArea = textArea;
		}

		boolean forward = command.endsWith("FindNext");

		// Create an object defining our search parameters.
		SearchContext context = new SearchContext();
		String text = searchField.getText();
		if (text.length() == 0) {
			return;
		}
		context.setSearchFor(text);
		context.setMatchCase(true);
		context.setRegularExpression(false);
		context.setSearchForward(forward);
		context.setWholeWord(false);

		boolean found = SearchEngine.find(searchArea, context);
		if (!found) {
			JOptionPane.showMessageDialog(this, "Text not found");
		}
	}
}
