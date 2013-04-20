package grammar.expression;

import compiler.Operation;
import grammar.CompilationUnit;
import grammar.Method;
import grammar.statement.ExprStatement;
import grammar.statement.Statement;
import junit.framework.TestCase;
import parser.KXICompiler;
import ryanvm.RyanVM;
import ryanvm.assembler.ParserException;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 4/15/13 12:54 PM
 */
public class MemberRefTest extends TestCase{

	public void testMemberRefChainWithArr() throws IOException, ParserException {

		//Test function
		String program =
				"class A{" +
						"public B b[]; "+

							"A(){"+
								"b = new B[5]; "+
								"b[3] = new B(); "+
							"}"+
						"}"+

						"class B{" +
                        	"public int i; "+
							"B(){"+
								"i = 5; "+
							"}"+

							"public int getI(int k){" +
								"return i; "+
							"}"+
						"}"+

				"void main(){" +
						"A a = new A(); "+
						//"a.b[3].i = 6; "+
						"a.b[3].getI(5 + 1); "+
				"}";

		CompilationUnit cu = KXICompiler.compileSyntax(program);
		System.out.println("COMPLETED: " + cu.toString());

		Method main = cu.main;
		for(Statement s: main.body.statements){
			 System.out.println(s);
			 if(s instanceof ExprStatement){
				 IExpression expr = ((ExprStatement) s).getExpression();
				 System.out.println("\t"+expr.getType());
			 }
		}

		System.out.println("\nComplete");
	}
}
