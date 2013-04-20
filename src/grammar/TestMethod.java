package grammar;

import compiler.Operation;
import grammar.statement.Statement;
import junit.framework.TestCase;
import parser.KXICompiler;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 3/16/13 4:05 PM
 */

public class TestMethod extends TestCase {

	public void testFunctionCall() {
		{
			//Test function
			String s1 = "e = f(a+1/b ,c,d);";
			Statement stmt = KXICompiler.compileStatement(s1);
			System.out.println("COMPLETED: " + stmt.toString());
			List<Operation> ops = stmt.buildCode();
			for (Operation op : ops) {
				System.out.println(op);
			}
		}
	}
}

