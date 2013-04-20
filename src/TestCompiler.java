import compiler.Operation;
import grammar.CompilationUnit;
import junit.framework.TestCase;
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
 * Time: 4/13/13 3:46 PM
 */
public class TestCompiler extends TestCase {
	public void testProgram() throws IOException, ParserException {
		{
			//Test function
			String program =
					"void main(){" +
							"int i = 5;"+
							"int j = 10;"+
							"cout << (i + j) / i;"+
					"}";

			CompilationUnit cu = KXICompiler.compileSyntax(program);
			System.out.println("COMPLETED: " + cu.toString());

			List<Operation> target = CompilationUnit.convertToTarget(cu.generateCode());
			StringBuilder sb = new StringBuilder();
			System.out.println("Assembly:\n");
			for(Operation t: target){
				System.out.println(t.toString());
				sb.append(t.toString()+"\n");
			}
			System.out.println("Program output:\n");
			RyanVM.runProgram(sb.toString(),System.in,System.out);

			System.out.println("\nComplete");
		}
	}

	public void testSimpleOutChar() throws IOException, ParserException {

		//Test function
		String program =
				"void main(){" +
					"cout << 'a';"+
				"}";

		CompilationUnit cu = KXICompiler.compileSyntax(program);
		System.out.println("COMPLETED: " + cu.toString());

		List<Operation> target = CompilationUnit.convertToTarget(cu.generateCode());
		StringBuilder sb = new StringBuilder();
		System.out.println("Assembly:\n");
		for(Operation t: target){
			System.out.println(t.toString());
			sb.append(t.toString()+"\n");
		}
		System.out.println("Program output:\n");
		RyanVM.runProgram(sb.toString(),System.in,System.out);

		System.out.println("\nComplete");
	}

	public void testSimpleOutChar2() throws IOException, ParserException {

		//Test function
		String program =
				"void main(){" +
					"char c = 'a'; "+
					"cout << c;"+
				"}";

		CompilationUnit cu = KXICompiler.compileSyntax(program);
		System.out.println("COMPLETED: " + cu.toString());

		List<Operation> target = CompilationUnit.convertToTarget(cu.generateCode());
		StringBuilder sb = new StringBuilder();
		System.out.println("Assembly:\n");
		for(Operation t: target){
			System.out.println(t.toString());
			sb.append(t.toString()+"\n");
		}
		System.out.println("Program output:\n");
		RyanVM.runProgram(sb.toString(),System.in,System.out);

		System.out.println("\nComplete");
	}

	public void testSimpleOutInt() throws IOException, ParserException {

		//Test function
		String program =
				"void main(){" +
					"cout << 666;"+
				"}";

		CompilationUnit cu = KXICompiler.compileSyntax(program);
		System.out.println("COMPLETED: " + cu.toString());

		List<Operation> target = CompilationUnit.convertToTarget(cu.generateCode());
		StringBuilder sb = new StringBuilder();
		System.out.println("Assembly:\n");
		for(Operation t: target){
			System.out.println(t.toString());
			sb.append(t.toString()+"\n");
		}
		System.out.println("Program output:\n");
		RyanVM.runProgram(sb.toString(),System.in,System.out);

		System.out.println("\nComplete");
	}



	public void testIntArraySet() throws IOException, ParserException {

		//Test function
		String program =
				"void main(){" +
					"int msg[] = new int[10];"+
					"int offset = 1; "+
					"int j; "+
					"msg[offset + 5] = 666; "+
					"j = msg[offset + 5] + 1; "+
					"cout << msg[offset + 5];" +
					"cout << j; "+
				"}";

		CompilationUnit cu = KXICompiler.compileSyntax(program);
		System.out.println("COMPLETED: " + cu.toString());

		List<Operation> target = CompilationUnit.convertToTarget(cu.generateCode());
		StringBuilder sb = new StringBuilder();
		System.out.println("Assembly:\n");
		for(Operation t: target){
			System.out.println(t.toString());
			sb.append(t.toString()+"\n");
		}
		System.out.println("Program output:\n");
		RyanVM.runProgram(sb.toString(),System.in,System.out);

		System.out.println("\nComplete");
	}

	public void testCharArraySet() throws IOException, ParserException {

		//Test function
		String program =
				"void main(){" +
					"char msg[] = new char[10];"+
					"msg[5] = 'a';"+
					"cout << msg[5];" +
				"}";

		CompilationUnit cu = KXICompiler.compileSyntax(program);
		System.out.println("COMPLETED: " + cu.toString());

		List<Operation> target = CompilationUnit.convertToTarget(cu.generateCode());
		StringBuilder sb = new StringBuilder();
		System.out.println("Assembly:\n");
		for(Operation t: target){
			System.out.println(t.toString());
			sb.append(t.toString()+"\n");
		}
		System.out.println("Program output:\n");
		RyanVM.runProgram(sb.toString(),System.in,System.out);

		System.out.println("\nComplete");
	}

	public void testProgram3() throws IOException, ParserException {

		//Test function
		String program =
				"void main(){" +
					"bool b;"+
					"b = (1==5) || (6 == 6);" +
					"cout << b; "+
				"}";

		CompilationUnit cu = KXICompiler.compileSyntax(program);
		System.out.println("COMPLETED: " + cu.toString());

		List<Operation> target = CompilationUnit.convertToTarget(cu.generateCode());
		StringBuilder sb = new StringBuilder();
		System.out.println("Assembly:\n");
		for(Operation t: target){
			System.out.println(t.toString());
			sb.append(t.toString()+"\n");
		}
		System.out.println("Program output:\n");
		RyanVM.runProgram(sb.toString(),System.in,System.out);

		System.out.println("\nComplete");
	}


	//Read in a character and print it out
	public void main(String[] args) throws IOException, ParserException {

		//Test function
		String program =
				"void main(){" +
					"char c; " +
					"cin >> c; "+
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
		RyanVM.runProgram(sb.toString(),System.in,System.out);

		System.out.println("\nComplete");
	}


	public void testIf() throws IOException, ParserException {

		//Test function
		String program =
				"void main(){" +
					"char y='f'; " +
					"if( y=='t' ){ "+
					"   y='t'; "+
					"}" +
					"else{"+
						"y='e'; " +
					"} " +
					"cout << y; "+
				"}";

		CompilationUnit cu = KXICompiler.compileSyntax(program);
		System.out.println("COMPLETED: " + cu.toString());

		List<Operation> target = CompilationUnit.convertToTarget(cu.generateCode());
		StringBuilder sb = new StringBuilder();
		System.out.println("Assembly:\n");
		for(Operation t: target){
			System.out.println(t.toString());
			sb.append(t.toString()+"\n");
		}
		System.out.println("Program output:\n");
		RyanVM.runProgram(sb.toString(),System.in,System.out);

		System.out.println("\nComplete");
	}

	public void testWhile() throws IOException, ParserException {

		//Test function
		String program =
				"void main(){" +
					"int i=10; " +
					"int sum=0; "+
					"while( i>0 ){ "+
						"sum = sum + i; "+
						"i = i - 1; "+
					"}" +
					"cout << sum; "+
				"}";

		CompilationUnit cu = KXICompiler.compileSyntax(program);
		System.out.println("COMPLETED: " + cu.toString());

		List<Operation> target = CompilationUnit.convertToTarget(cu.generateCode());
		StringBuilder sb = new StringBuilder();
		System.out.println("Assembly:\n");
		for(Operation t: target){
			System.out.println(t.toString());
			sb.append(t.toString()+"\n");
		}
		System.out.println("Program output:\n");
		RyanVM.runProgram(sb.toString(),System.in,System.out);

		System.out.println("\nComplete");
	}

	public void testNew() throws IOException, ParserException {

		//Test function
		String program =
				"class A{" +
					"A(){"+
						"cout << 'a';"+
					"}"+
				"}"+
				"void main(){" +
					"char b = 'b'; "+
					"A a = new A(); "+
					"cout << b; "+
					"b = 'c';"+
					"cout << b; "+
				"}";

		CompilationUnit cu = KXICompiler.compileSyntax(program);
		System.out.println("COMPLETED: " + cu.toString());

		List<Operation> target = CompilationUnit.convertToTarget(cu.generateCode());
		StringBuilder sb = new StringBuilder();
		System.out.println("Assembly:\n");
		for(Operation t: target){
			System.out.println(t.toString());
			sb.append(t.toString()+"\n");
		}
		System.out.println("Program output:\n");
		RyanVM.runProgram(sb.toString(),System.in,System.out);

		System.out.println("\nComplete");
	}

	public void testNewParam() throws IOException, ParserException {

		//Test function
		String program =
				"class A{ \n" +
					"public char c1 = 'a'; \n"+
					"A(char c1, char c2){ \n"+
						"cout << this.c1; \n"+
						"cout << c1; \n"+
						"cout << c2; \n"+
						"this.c1 = c1 ; \n "+
						"cout << this.c1; \n"+
					"} \n"+
				"} \n"+
				"void main(){ \n" +
					"A a = new A('b','c'); \n"+
				"} \n";

		CompilationUnit cu = KXICompiler.compileSyntax(program);
		System.out.println("COMPLETED: " + cu.toString());

		List<Operation> target = CompilationUnit.convertToTarget(cu.generateCode());
		StringBuilder sb = new StringBuilder();
		System.out.println("Assembly:\n");
		for(Operation t: target){
			System.out.println(t.toString());
			sb.append(t.toString()+"\n");
		}
		System.out.println("Program output:\n");
		RyanVM.runProgram(sb.toString(),System.in,System.out);

		System.out.println("\nComplete");
	}

	public void testNewParamExpr() throws IOException, ParserException {

		//Test function
		String program =
				"class A{ \n" +

						"A(int i){ \n"+
							"cout << i; \n"+
						"} \n"+
				"} \n"+
				"void main(){ \n" +
					"A a = new A(1+3); \n"+
				"} \n";

		CompilationUnit cu = KXICompiler.compileSyntax(program);
		System.out.println("COMPLETED: " + cu.toString());

		List<Operation> target = CompilationUnit.convertToTarget(cu.generateCode());
		StringBuilder sb = new StringBuilder();
		System.out.println("Assembly:\n");
		for(Operation t: target){
			System.out.println(t.toString());
			sb.append(t.toString()+"\n");
		}
		System.out.println("Program output:\n");
		RyanVM.runProgram(sb.toString(),System.in,System.out);

		System.out.println("\nComplete");
	}


	public void testNew2() throws IOException, ParserException {

		//Test function
		String program =
				"class A{" +
					"public int j[]; "+
					"A(int i){"+
						"int idx = 0; "+
						"this.j = new int[5]; "+
						"this.j[idx + 0] = i; "+
						"this.j[idx + 1] = i + 1; "+
						"this.j[idx + 2] = i + 2; "+

					"}"+

				"}"+

				"void main(){" +
					"char b = 'b'; "+
					"A a = new A(10); "+
					"cout << a.j[0]; "+
					"cout << a.j[1]; "+
					"cout << a.j[2]; "+
				"}";

		CompilationUnit cu = KXICompiler.compileSyntax(program);
		System.out.println("COMPLETED: " + cu.toString());

		List<Operation> target = CompilationUnit.convertToTarget(cu.generateCode());
		StringBuilder sb = new StringBuilder();
		System.out.println("Assembly:\n");
		for(Operation t: target){
			System.out.println(t.toString());
			sb.append(t.toString()+"\n");
		}
		System.out.println("Program output:\n");
		RyanVM.runProgram(sb.toString(),System.in,System.out);

		System.out.println("\nComplete");
	}

	public void testMemberRef() throws IOException, ParserException {

		//Test function
		String program =
				"class A{" +
					"public int i; "+
					"A(){"+
						"i = 5; "+
					"}"+
				"}"+

				"void main(){" +
					//"int j; "+
					"A a = new A(); "+
					//"j = a.i; "+
					"cout << a.i; "+
					"a.i = 6;"+
					"cout << a.i; "+
				"}";

		CompilationUnit cu = KXICompiler.compileSyntax(program);
		System.out.println("COMPLETED: " + cu.toString());

		List<Operation> target = CompilationUnit.convertToTarget(cu.generateCode());
		StringBuilder sb = new StringBuilder();
		System.out.println("Assembly:\n");
		for(Operation t: target){
			System.out.println(t.toString());
			sb.append(t.toString()+"\n");
		}

		System.out.println("Program output:\n");
		RyanVM.runProgram(sb.toString(),System.in,System.out);

		System.out.println("\nComplete");
	}

	public void testMemberRefChain() throws IOException, ParserException {

		//Test function
		String program =
				"class A{" +
					"public B b; "+
					"A(){"+
						"b = new B(); "+
					"}"+
				"}"+

				"class B{" +
					"public int i; "+
					"B(){"+
						"i = 5; "+
					"}"+
				"}"+

				"void main(){" +
					"A a = new A(); "+
					"cout << a.b.i ; "+
					"a.b.i = 6;"+
					"cout << a.b.i ; "+
				"}";

		CompilationUnit cu = KXICompiler.compileSyntax(program);
		System.out.println("COMPLETED: " + cu.toString());

		List<Operation> target = CompilationUnit.convertToTarget(cu.generateCode());
		StringBuilder sb = new StringBuilder();
		System.out.println("Assembly:\n");
		for(Operation t: target){
			System.out.println(t.toString());
			sb.append(t.toString()+"\n");
		}
		System.out.println("Program output:\n");
		RyanVM.runProgram(sb.toString(),System.in,System.out);

		System.out.println("\nComplete");
	}


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
			"}"+

		"void main(){" +
			"A a = new A(); "+
			"a.b[3].i = 6; "+
			"cout << a.b[3].i; "+
		"}";

		CompilationUnit cu = KXICompiler.compileSyntax(program);
		System.out.println("COMPLETED: " + cu.toString());

		List<Operation> target = CompilationUnit.convertToTarget(cu.generateCode());
		StringBuilder sb = new StringBuilder();
		System.out.println("Assembly:\n");
		for(Operation t: target){
			System.out.println(t.toString());
			sb.append(t.toString()+"\n");
		}
		System.out.println("Program output:\n");
		RyanVM.runProgram(sb.toString(),System.in,System.out);

		System.out.println("\nComplete");
	}

	public void testMethodCall() throws IOException, ParserException {

		//Test function
		String program =
				"class A{" +

					"A(){"+
						"print(); "+
					"}"+

					"public char print(){" +
						"cout << 'a'; "+
						"return 'a'; "+
					"}"+
				"}"+


				"void main(){" +
					"A a = new A(); "+
				"}";

		CompilationUnit cu = KXICompiler.compileSyntax(program);
		System.out.println("COMPLETED: " + cu.toString());

		List<Operation> target = CompilationUnit.convertToTarget(cu.generateCode());
		StringBuilder sb = new StringBuilder();
		System.out.println("Assembly:\n");
		for(Operation t: target){
			System.out.println(t.toString());
			sb.append(t.toString()+"\n");
		}
		System.out.println("Program output:\n");
		RyanVM.runProgram(sb.toString(),System.in,System.out);

		System.out.println("\nComplete");
	}

	public void testMethodRecursive() throws IOException, ParserException {

		//Test function
		String program =
				"class A{ \n" +

					"public int sum = 0; \n"+

					"A(){ \n"+
						"sum(10); \n"+
					"} \n"+

					"public void sum(int i){ \n" +
						"sum = sum + i; \n"+
						"if(i > 0){ \n" +
							"sum(i - 1); \n"+
						"}\n" +
					"} \n"+
				"} \n"+


				"void main(){" +
					"A a = new A(); "+
					"cout << a.sum; "+
				"}";

		CompilationUnit cu = KXICompiler.compileSyntax(program);
		System.out.println("COMPLETED: " + cu.toString());

		List<Operation> target = CompilationUnit.convertToTarget(cu.generateCode());
		StringBuilder sb = new StringBuilder();
		System.out.println("Assembly:\n");
		for(Operation t: target){
			System.out.println(t.toString());
			sb.append(t.toString()+"\n");
		}
		System.out.println("Program output:\n");
		RyanVM.runProgram(sb.toString(),System.in,System.out);

		System.out.println("\nComplete");
	}

	public void testMethodRecursive2() throws IOException, ParserException {

		//Test function
		String program =
				"class A{ \n" +

					"public int sum = 0; \n"+

					"A(){ \n"+
						"sum = sum(10); \n"+
					"} \n"+

					"public int sum(int i){ \n" +
						"if(i > 0){ \n" +
							"return i + sum(i - 1); \n"+
						"}else{\n"+
							"return 0;\n"+
						"}\n"+
					"} \n"+
				"} \n"+


				"void main(){" +
					"A a = new A(); "+
					"cout << a.sum; "+
				"}";

		CompilationUnit cu = KXICompiler.compileSyntax(program);
		System.out.println("COMPLETED: " + cu.toString());

		List<Operation> target = CompilationUnit.convertToTarget(cu.generateCode());
		StringBuilder sb = new StringBuilder();
		System.out.println("Assembly:\n");
		for(Operation t: target){
			System.out.println(t.toString());
			sb.append(t.toString()+"\n");
		}
		System.out.println("Program output:\n");
		RyanVM.runProgram(sb.toString(),System.in,System.out);

		System.out.println("\nComplete");
	}


	public void testLogic() throws IOException, ParserException {

		//Test function
		String program =

				"void main(){\n" +
					"int i = 5; \n"+
					"bool b; " +
					"b = i == 5 || i == 4; "+
					"cout << b; "+
				"}";

		CompilationUnit cu = KXICompiler.compileSyntax(program);
		System.out.println("COMPLETED: " + cu.toString());

		List<Operation> target = CompilationUnit.convertToTarget(cu.generateCode());
		StringBuilder sb = new StringBuilder();
		System.out.println("Assembly:\n");
		for(Operation t: target){
			System.out.println(t.toString());
			sb.append(t.toString()+"\n");
		}
		System.out.println("Program output:\n");
		RyanVM.runProgram(sb.toString(),System.in,System.out);

		System.out.println("\nComplete");
	}
}
