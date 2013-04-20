package editor.jconsole;

import javax.swing.*;

import editor.jconsole.JConsole;

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Test extends JComponent {
	public static void main(String[] args) throws Exception {
		JConsole.launch("Test");
		System.out.println("fooey");
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter a #: ");
		int n = scanner.nextInt();
		System.out.println("You entered: " + n);
		JConsole.exit();
	}
}