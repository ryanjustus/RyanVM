package parser;

import grammar.error.SyntaxException;
import grammar.expression.ExpressionBuilder;
import grammar.expression.IExpression;
import grammar.statement.ExprStatement;
import grammar.statement.Statement;
import grammar.statement.StatementBuilder;
import org.antlr.v4.runtime.*;
import grammar.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.misc.Nullable;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 1/28/13 5:54 PM
 */
public class KXICompiler {


	public static IExpression compileExpression(String expression){
		//lexer splits input into tokens

		ANTLRInputStream stream = new ANTLRInputStream(expression);

		KXILexer lexer = new KXILexer(stream);
		TokenStream tokens = new CommonTokenStream(lexer);
		//parser generates abstract syntax tree
		KXIParser parser = new KXIParser(tokens);

		KXIParser.StatementContext context = parser.statement();
		System.out.println("Building expression: "+context.getText());

		Statement s = StatementBuilder.build(null,context);
		if(s instanceof ExprStatement){
			return ((ExprStatement) s).getExpression();
		}
		return null;
	}

	public static Statement compileStatement(String statement){
		KXIParser parser = buildParser(statement);
		KXIParser.StatementContext context =  parser.statement();
		Statement s = StatementBuilder.build(null,context);
		return s;
	}

	public static KXIParser buildParser(String input){

		ANTLRInputStream stream = new ANTLRInputStream(input);
		KXILexer lexer = new KXILexer(stream);
		lexer.addErrorListener(new ANTLRErrorListener() {
			@Override
			public void syntaxError(Recognizer<?, ?> recognizer, @Nullable Object o, int i, int i2, String s, @Nullable RecognitionException e) {

				if(e!=null){
					System.err.println(new SyntaxException(e,"SyntaxError"));
				}else{
					System.err.println(new SyntaxException(i,i2,s));
				}
			}

			@Override
			public void reportAmbiguity(@NotNull Parser parser, DFA dfa, int i, int i2, @NotNull BitSet bitSet, @NotNull ATNConfigSet atnConfigs) {
				//To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public void reportAttemptingFullContext(@NotNull Parser parser, @NotNull DFA dfa, int i, int i2, @NotNull ATNConfigSet atnConfigs) {
				//To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public void reportContextSensitivity(@NotNull Parser parser, @NotNull DFA dfa, int i, int i2, @NotNull ATNConfigSet atnConfigs) {
				//To change body of implemented methods use File | Settings | File Templates.
			}
		});
		TokenStream tokens = new CommonTokenStream(lexer);
		//parser generates abstract syntax tree
		KXIParser parser = new KXIParser(tokens);

		//System.err.println("parser constructed");
		return parser;
	}

	public static CompilationUnit compileSyntax(String input)  {



		final List<SyntaxException> syntaxErrors = new ArrayList<SyntaxException>();

		ANTLRInputStream stream = new ANTLRInputStream(input);
		KXILexer lexer = new KXILexer(stream);
		lexer.addErrorListener(new ANTLRErrorListener() {
			@Override
			public void syntaxError(Recognizer<?, ?> recognizer, @Nullable Object o, int i, int i2, String s, @Nullable RecognitionException e) {
				syntaxErrors.add(new SyntaxException(i,i2,s));
			}

			@Override
			public void reportAmbiguity(@NotNull Parser parser, DFA dfa, int i, int i2, @NotNull BitSet bitSet, @NotNull ATNConfigSet atnConfigs) {
				//To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public void reportAttemptingFullContext(@NotNull Parser parser, @NotNull DFA dfa, int i, int i2, @NotNull ATNConfigSet atnConfigs) {
				//To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public void reportContextSensitivity(@NotNull Parser parser, @NotNull DFA dfa, int i, int i2, @NotNull ATNConfigSet atnConfigs) {
				//To change body of implemented methods use File | Settings | File Templates.
			}
		});
		TokenStream tokens = new CommonTokenStream(lexer);
		//parser generates abstract syntax tree
		KXIParser parser = new KXIParser(tokens);

		parser.addErrorListener(new ANTLRErrorListener() {
			public void syntaxError(Recognizer<?, ?> recognizer, @Nullable Object o, int i, int i2, String s, @Nullable RecognitionException e) {

				if(e!=null){
			        syntaxErrors.add(new SyntaxException(e,"SyntaxError"));
				 }else{
					syntaxErrors.add(new SyntaxException(i,i2,s));
				 }
			}

			public void reportAmbiguity(@NotNull Parser parser, DFA dfa, int i, int i2, @NotNull BitSet bitSet, @NotNull ATNConfigSet atnConfigs) {

			}

			public void reportAttemptingFullContext(@NotNull Parser parser, @NotNull DFA dfa, int i, int i2, @NotNull ATNConfigSet atnConfigs) {

			}

			public void reportContextSensitivity(@NotNull Parser parser, @NotNull DFA dfa, int i, int i2, @NotNull ATNConfigSet atnConfigs) {

			}


		});
			KXIParser.Compilation_unitContext context=null;
		try{
			context =  parser.compilation_unit();
			//System.err.println("Context built");
		}catch(Exception e){

			System.err.println("PARSE EXCEPTION CAUGHT: "+e.getMessage());
			e.printStackTrace(System.err);

		}
		return new CompilationUnit(syntaxErrors,context);
	}
}
