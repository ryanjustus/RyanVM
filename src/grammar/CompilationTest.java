package grammar;

import compiler.Operation;
import grammar.statement.Statement;
import junit.framework.TestCase;
import parser.KXICompiler;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 4/3/13 4:41 PM
 */
public class CompilationTest extends TestCase {

	public void testProgram() {
		{
			//Test function
			String program =
					"class A{" +
						"public int i[];"+
						"public int j;"+

						"A(){" +
						"}"+

						"public void set(){" +
							"i = new int[100];"+
							"i[0];"+
						"}"+
					"}" +


					"void main(){" +
					 	"A a = new A();"+
					"}";

			CompilationUnit cu = KXICompiler.compileSyntax(program);
			System.out.println("COMPLETED: " + cu.toString());
			List<Operation> ops = cu.generateCode();
			for(Operation op: ops){
				System.out.println(op);
			}
		}
	}

	public void testProgramFunctionCall() {
		{
			//Test function
			String program =
					"class A{" +

						"A(){}" +

						"public void f(){" +
							"g();"+
						"}" +

						"public void g(){" +
						"}" +

					"}"+

					"void main(){" +
					"}";

			CompilationUnit cu = KXICompiler.compileSyntax(program);
			System.out.println("COMPLETED: " + cu.toString());
			List<Operation> ops = cu.generateCode();
			for(Operation op: ops){
				System.out.println(op);
			}
		}
	}

	public void testProgramConstructor() {
		{
			//Test function
			String program =
					"class A{" +

							"A(){" +
							"}" +

							"public void f(){" +
							    "return;"+
								"g();"+
							"}" +

							"public void g(){" +
							"}" +

							"}"+

							"void main(){" +
							"}";

			CompilationUnit cu = KXICompiler.compileSyntax(program);
			System.out.println("COMPLETED: " + cu.toString());
			List<Operation> ops = cu.generateCode();
			for(Operation op: ops){
				System.out.println(op);
			}
		}
	}

	public void testProgramBinaryOp() {
		{
			//Test function
			String program =
				"void main(){ \n" +
						"int i=0; \n"+
						"int j=5; \n"+
						"bool b = true; \n"+
						"return ; \n"+
						"b = i<j; \n" +

				"}";

			CompilationUnit cu = KXICompiler.compileSyntax(program);
			System.out.println("COMPLETED: " + cu.toString());
			List<Operation> ops = cu.generateCode();
			for(Operation op: ops){
				System.out.println(op);
			}
		}
	}

	public void testConstructor() {
		{
			//Test function
			String program =
					"class A{" +
						"private int i = 1 + 3;"+

						"A(){" +
							"i = i + 5;"+
						"}" +
					"}"+

					"void main(){" +
						"A a = new A();"+
					"}";

			CompilationUnit cu = KXICompiler.compileSyntax(program);
			System.out.println("COMPLETED: " + cu.toString());
			List<Operation> ops = cu.generateCode();
			for(Operation op: ops){
				System.out.println(op);
			}

			System.out.println("*********");
			ops = CompilationUnit.convertToTarget(ops);
			for(Operation op: ops){
				System.out.println(op);
			}
		}
	}
}
