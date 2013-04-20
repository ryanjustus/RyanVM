import compiler.Operation;
import grammar.CompilationUnit;
import parser.KXICompiler;
import ryanvm.RyanVM;
import ryanvm.assembler.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 4/14/13 1:39 PM
 */
public class TestReadIO {

	public static void main(String[] args) throws IOException, ParserException {

		//Test function
		String program =
				"void main(){" +
						"int c; " +
						"cin >> c; "+
						"cout << c; "+
				"}";

		CompilationUnit cu = KXICompiler.compileSyntax(program);
		System.out.println("COMPLETED: " + cu.toString());
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		String c = r.readLine();
		System.out.println("IN: "+c);
		List<Operation> target = CompilationUnit.convertToTarget(cu.generateCode());
		StringBuilder sb = new StringBuilder();
		System.out.println("Assembly:\n");
		for(Operation t: target){
			System.out.println(t.toString());
			sb.append(t.toString()+"\n");
		}
		System.out.println("Program output:\n");
		RyanVM.runProgram(sb.toString(), System.in, System.out);
		System.out.println("\nComplete");
	}
}
