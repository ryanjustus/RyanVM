package grammar.statement;

import compiler.Operation;
import grammar.expression.IExpression;
import grammar.statement.Statement;
import junit.framework.TestCase;
import parser.KXICompiler;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 1/29/13 9:58 AM
 */
public class StatementTest extends TestCase {

	public void testConstructor() {
		{
			//Test function

			String s1 = "a.f()";

			Statement stmt = KXICompiler.compileStatement(s1);
			System.out.println("COMPLETED: " + stmt.toString());
			List<Operation> ops = stmt.buildCode();
			for(Operation op: ops){
				System.out.println(op);
			}
		}
	}
}
