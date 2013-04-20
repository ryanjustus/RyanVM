// Generated from C:\Users\ryan\KXI.g4 by ANTLR 4.0
package parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class KXIParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		MODIFIER=1, KEYFUNC=2, PRIMITIVE_TYPE=3, VOID=4, IF=5, ELSE=6, WHILE=7, 
		RETURN=8, CLASS=9, NULL=10, THIS=11, NEW=12, MAIN=13, CIN=14, COUT=15, 
		CHAR=16, COMMENT=17, LOGIC=18, BOOLEAN=19, ID=20, INT=21, WS=22, COMMA=23, 
		SEMICOLON=24, LPAREN=25, RPAREN=26, RSQUARE=27, LSQUARE=28, RBRACKET=29, 
		LBRACKET=30, OR=31, DOT=32, QUERY=33, IN=34, OUT=35, BOOL_EXPR=36, ASSIGN=37, 
		MATH=38;
	public static final String[] tokenNames = {
		"<INVALID>", "MODIFIER", "KEYFUNC", "PRIMITIVE_TYPE", "'void'", "'if'", 
		"'else'", "'while'", "'return'", "'class'", "'null'", "'this'", "'new'", 
		"'main'", "'cin'", "'cout'", "CHAR", "COMMENT", "LOGIC", "BOOLEAN", "ID", 
		"INT", "WS", "','", "';'", "'('", "')'", "']'", "'['", "'}'", "'{'", "'|'", 
		"'.'", "'?'", "'>>'", "'<<'", "BOOL_EXPR", "'='", "MATH"
	};
	public static final int
		RULE_compilation_unit = 0, RULE_class_declaration = 1, RULE_class_member_declaration = 2, 
		RULE_field_declaration = 3, RULE_constructor_declaration = 4, RULE_method_body = 5, 
		RULE_variable_declaration = 6, RULE_parameter_list = 7, RULE_parameter = 8, 
		RULE_statement = 9, RULE_expression = 10, RULE_expressionz = 11, RULE_fn_arr_member = 12, 
		RULE_argument_list = 13, RULE_assignment_expression = 14, RULE_type = 15, 
		RULE_class_name = 16, RULE_new_declaration = 17, RULE_member_refz = 18;
	public static final String[] ruleNames = {
		"compilation_unit", "class_declaration", "class_member_declaration", "field_declaration", 
		"constructor_declaration", "method_body", "variable_declaration", "parameter_list", 
		"parameter", "statement", "expression", "expressionz", "fn_arr_member", 
		"argument_list", "assignment_expression", "type", "class_name", "new_declaration", 
		"member_refz"
	};

	@Override
	public String getGrammarFileName() { return "KXI.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public KXIParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class Compilation_unitContext extends ParserRuleContext {
		public Class_declarationContext class_declaration(int i) {
			return getRuleContext(Class_declarationContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(KXIParser.RPAREN, 0); }
		public List<Class_declarationContext> class_declaration() {
			return getRuleContexts(Class_declarationContext.class);
		}
		public TerminalNode MAIN() { return getToken(KXIParser.MAIN, 0); }
		public TerminalNode VOID() { return getToken(KXIParser.VOID, 0); }
		public TerminalNode EOF() { return getToken(KXIParser.EOF, 0); }
		public Method_bodyContext method_body() {
			return getRuleContext(Method_bodyContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(KXIParser.LPAREN, 0); }
		public Compilation_unitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compilation_unit; }
	}

	public final Compilation_unitContext compilation_unit() throws RecognitionException {
		Compilation_unitContext _localctx = new Compilation_unitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_compilation_unit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CLASS) {
				{
				{
				setState(38); class_declaration();
				}
				}
				setState(43);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(44); match(VOID);
			setState(45); match(MAIN);
			setState(46); match(LPAREN);
			setState(47); match(RPAREN);
			setState(48); method_body();
			setState(49); match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Class_declarationContext extends ParserRuleContext {
		public TerminalNode LBRACKET() { return getToken(KXIParser.LBRACKET, 0); }
		public TerminalNode CLASS() { return getToken(KXIParser.CLASS, 0); }
		public TerminalNode RBRACKET() { return getToken(KXIParser.RBRACKET, 0); }
		public List<Class_member_declarationContext> class_member_declaration() {
			return getRuleContexts(Class_member_declarationContext.class);
		}
		public Class_member_declarationContext class_member_declaration(int i) {
			return getRuleContext(Class_member_declarationContext.class,i);
		}
		public Class_nameContext class_name() {
			return getRuleContext(Class_nameContext.class,0);
		}
		public Class_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_class_declaration; }
	}

	public final Class_declarationContext class_declaration() throws RecognitionException {
		Class_declarationContext _localctx = new Class_declarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_class_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51); match(CLASS);
			setState(52); class_name();
			setState(53); match(LBRACKET);
			setState(57);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MODIFIER || _la==ID) {
				{
				{
				setState(54); class_member_declaration();
				}
				}
				setState(59);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(60); match(RBRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Class_member_declarationContext extends ParserRuleContext {
		public Constructor_declarationContext constructor_declaration() {
			return getRuleContext(Constructor_declarationContext.class,0);
		}
		public Field_declarationContext field_declaration() {
			return getRuleContext(Field_declarationContext.class,0);
		}
		public TerminalNode ID() { return getToken(KXIParser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode MODIFIER() { return getToken(KXIParser.MODIFIER, 0); }
		public Class_member_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_class_member_declaration; }
	}

	public final Class_member_declarationContext class_member_declaration() throws RecognitionException {
		Class_member_declarationContext _localctx = new Class_member_declarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_class_member_declaration);
		try {
			setState(68);
			switch (_input.LA(1)) {
			case MODIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(62); match(MODIFIER);
				setState(63); type();
				setState(64); match(ID);
				setState(65); field_declaration();
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(67); constructor_declaration();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Field_declarationContext extends ParserRuleContext {
		public TerminalNode RPAREN() { return getToken(KXIParser.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(KXIParser.SEMICOLON, 0); }
		public TerminalNode LSQUARE() { return getToken(KXIParser.LSQUARE, 0); }
		public Assignment_expressionContext assignment_expression() {
			return getRuleContext(Assignment_expressionContext.class,0);
		}
		public TerminalNode RSQUARE() { return getToken(KXIParser.RSQUARE, 0); }
		public Method_bodyContext method_body() {
			return getRuleContext(Method_bodyContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(KXIParser.LPAREN, 0); }
		public Parameter_listContext parameter_list() {
			return getRuleContext(Parameter_listContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(KXIParser.ASSIGN, 0); }
		public Field_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_field_declaration; }
	}

	public final Field_declarationContext field_declaration() throws RecognitionException {
		Field_declarationContext _localctx = new Field_declarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_field_declaration);
		int _la;
		try {
			setState(85);
			switch (_input.LA(1)) {
			case SEMICOLON:
			case LSQUARE:
			case ASSIGN:
				enterOuterAlt(_localctx, 1);
				{
				setState(72);
				_la = _input.LA(1);
				if (_la==LSQUARE) {
					{
					setState(70); match(LSQUARE);
					setState(71); match(RSQUARE);
					}
				}

				setState(76);
				_la = _input.LA(1);
				if (_la==ASSIGN) {
					{
					setState(74); match(ASSIGN);
					setState(75); assignment_expression();
					}
				}

				setState(78); match(SEMICOLON);
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 2);
				{
				setState(79); match(LPAREN);
				setState(81);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PRIMITIVE_TYPE) | (1L << VOID) | (1L << ID))) != 0)) {
					{
					setState(80); parameter_list();
					}
				}

				setState(83); match(RPAREN);
				setState(84); method_body();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Constructor_declarationContext extends ParserRuleContext {
		public TerminalNode RPAREN() { return getToken(KXIParser.RPAREN, 0); }
		public Method_bodyContext method_body() {
			return getRuleContext(Method_bodyContext.class,0);
		}
		public Class_nameContext class_name() {
			return getRuleContext(Class_nameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(KXIParser.LPAREN, 0); }
		public Parameter_listContext parameter_list() {
			return getRuleContext(Parameter_listContext.class,0);
		}
		public Constructor_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructor_declaration; }
	}

	public final Constructor_declarationContext constructor_declaration() throws RecognitionException {
		Constructor_declarationContext _localctx = new Constructor_declarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_constructor_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87); class_name();
			setState(88); match(LPAREN);
			setState(90);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PRIMITIVE_TYPE) | (1L << VOID) | (1L << ID))) != 0)) {
				{
				setState(89); parameter_list();
				}
			}

			setState(92); match(RPAREN);
			setState(93); method_body();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Method_bodyContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public TerminalNode LBRACKET() { return getToken(KXIParser.LBRACKET, 0); }
		public List<Variable_declarationContext> variable_declaration() {
			return getRuleContexts(Variable_declarationContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode RBRACKET() { return getToken(KXIParser.RBRACKET, 0); }
		public Variable_declarationContext variable_declaration(int i) {
			return getRuleContext(Variable_declarationContext.class,i);
		}
		public Method_bodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method_body; }
	}

	public final Method_bodyContext method_body() throws RecognitionException {
		Method_bodyContext _localctx = new Method_bodyContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_method_body);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(95); match(LBRACKET);
			setState(99);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					{
					{
					setState(96); variable_declaration();
					}
					} 
				}
				setState(101);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			setState(105);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IF) | (1L << WHILE) | (1L << RETURN) | (1L << NULL) | (1L << THIS) | (1L << CIN) | (1L << COUT) | (1L << CHAR) | (1L << BOOLEAN) | (1L << ID) | (1L << INT) | (1L << LPAREN) | (1L << LBRACKET))) != 0)) {
				{
				{
				setState(102); statement();
				}
				}
				setState(107);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(108); match(RBRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Variable_declarationContext extends ParserRuleContext {
		public TerminalNode SEMICOLON() { return getToken(KXIParser.SEMICOLON, 0); }
		public TerminalNode LSQUARE() { return getToken(KXIParser.LSQUARE, 0); }
		public Assignment_expressionContext assignment_expression() {
			return getRuleContext(Assignment_expressionContext.class,0);
		}
		public TerminalNode RSQUARE() { return getToken(KXIParser.RSQUARE, 0); }
		public TerminalNode ID() { return getToken(KXIParser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(KXIParser.ASSIGN, 0); }
		public Variable_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable_declaration; }
	}

	public final Variable_declarationContext variable_declaration() throws RecognitionException {
		Variable_declarationContext _localctx = new Variable_declarationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_variable_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110); type();
			setState(111); match(ID);
			setState(114);
			_la = _input.LA(1);
			if (_la==LSQUARE) {
				{
				setState(112); match(LSQUARE);
				setState(113); match(RSQUARE);
				}
			}

			setState(118);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(116); match(ASSIGN);
				setState(117); assignment_expression();
				}
			}

			setState(120); match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Parameter_listContext extends ParserRuleContext {
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public TerminalNode COMMA(int i) {
			return getToken(KXIParser.COMMA, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(KXIParser.COMMA); }
		public Parameter_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter_list; }
	}

	public final Parameter_listContext parameter_list() throws RecognitionException {
		Parameter_listContext _localctx = new Parameter_listContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_parameter_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122); parameter();
			setState(127);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(123); match(COMMA);
				setState(124); parameter();
				}
				}
				setState(129);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterContext extends ParserRuleContext {
		public TerminalNode LSQUARE() { return getToken(KXIParser.LSQUARE, 0); }
		public TerminalNode RSQUARE() { return getToken(KXIParser.RSQUARE, 0); }
		public TerminalNode ID() { return getToken(KXIParser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_parameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130); type();
			setState(131); match(ID);
			setState(134);
			_la = _input.LA(1);
			if (_la==LSQUARE) {
				{
				setState(132); match(LSQUARE);
				setState(133); match(RSQUARE);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public TerminalNode LBRACKET() { return getToken(KXIParser.LBRACKET, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(KXIParser.RPAREN, 0); }
		public TerminalNode IN() { return getToken(KXIParser.IN, 0); }
		public TerminalNode WHILE() { return getToken(KXIParser.WHILE, 0); }
		public TerminalNode OUT() { return getToken(KXIParser.OUT, 0); }
		public TerminalNode ELSE() { return getToken(KXIParser.ELSE, 0); }
		public TerminalNode RETURN() { return getToken(KXIParser.RETURN, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public TerminalNode SEMICOLON() { return getToken(KXIParser.SEMICOLON, 0); }
		public TerminalNode RBRACKET() { return getToken(KXIParser.RBRACKET, 0); }
		public TerminalNode CIN() { return getToken(KXIParser.CIN, 0); }
		public TerminalNode COUT() { return getToken(KXIParser.COUT, 0); }
		public TerminalNode LPAREN() { return getToken(KXIParser.LPAREN, 0); }
		public TerminalNode IF() { return getToken(KXIParser.IF, 0); }
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_statement);
		int _la;
		try {
			setState(177);
			switch (_input.LA(1)) {
			case LBRACKET:
				enterOuterAlt(_localctx, 1);
				{
				setState(136); match(LBRACKET);
				setState(140);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IF) | (1L << WHILE) | (1L << RETURN) | (1L << NULL) | (1L << THIS) | (1L << CIN) | (1L << COUT) | (1L << CHAR) | (1L << BOOLEAN) | (1L << ID) | (1L << INT) | (1L << LPAREN) | (1L << LBRACKET))) != 0)) {
					{
					{
					setState(137); statement();
					}
					}
					setState(142);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(143); match(RBRACKET);
				}
				break;
			case NULL:
			case THIS:
			case CHAR:
			case BOOLEAN:
			case ID:
			case INT:
			case LPAREN:
				enterOuterAlt(_localctx, 2);
				{
				setState(144); expression();
				setState(145); match(SEMICOLON);
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 3);
				{
				setState(147); match(IF);
				setState(148); match(LPAREN);
				setState(149); expression();
				setState(150); match(RPAREN);
				setState(151); statement();
				setState(154);
				switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
				case 1:
					{
					setState(152); match(ELSE);
					setState(153); statement();
					}
					break;
				}
				}
				break;
			case WHILE:
				enterOuterAlt(_localctx, 4);
				{
				setState(156); match(WHILE);
				setState(157); match(LPAREN);
				setState(158); expression();
				setState(159); match(RPAREN);
				setState(160); statement();
				}
				break;
			case RETURN:
				enterOuterAlt(_localctx, 5);
				{
				setState(162); match(RETURN);
				setState(164);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NULL) | (1L << THIS) | (1L << CHAR) | (1L << BOOLEAN) | (1L << ID) | (1L << INT) | (1L << LPAREN))) != 0)) {
					{
					setState(163); expression();
					}
				}

				setState(166); match(SEMICOLON);
				}
				break;
			case CIN:
				enterOuterAlt(_localctx, 6);
				{
				setState(167); match(CIN);
				setState(168); match(IN);
				setState(169); expression();
				setState(170); match(SEMICOLON);
				}
				break;
			case COUT:
				enterOuterAlt(_localctx, 7);
				{
				setState(172); match(COUT);
				setState(173); match(OUT);
				setState(174); expression();
				setState(175); match(SEMICOLON);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public TerminalNode CHAR() { return getToken(KXIParser.CHAR, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(KXIParser.RPAREN, 0); }
		public TerminalNode INT() { return getToken(KXIParser.INT, 0); }
		public TerminalNode BOOLEAN() { return getToken(KXIParser.BOOLEAN, 0); }
		public ExpressionzContext expressionz() {
			return getRuleContext(ExpressionzContext.class,0);
		}
		public Fn_arr_memberContext fn_arr_member() {
			return getRuleContext(Fn_arr_memberContext.class,0);
		}
		public TerminalNode ID() { return getToken(KXIParser.ID, 0); }
		public TerminalNode NULL() { return getToken(KXIParser.NULL, 0); }
		public TerminalNode LPAREN() { return getToken(KXIParser.LPAREN, 0); }
		public Member_refzContext member_refz() {
			return getRuleContext(Member_refzContext.class,0);
		}
		public TerminalNode THIS() { return getToken(KXIParser.THIS, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_expression);
		int _la;
		try {
			setState(211);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(179); match(LPAREN);
				setState(180); expression();
				setState(181); match(RPAREN);
				setState(183);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LOGIC) | (1L << BOOL_EXPR) | (1L << ASSIGN) | (1L << MATH))) != 0)) {
					{
					setState(182); expressionz();
					}
				}

				}
				break;
			case BOOLEAN:
				enterOuterAlt(_localctx, 2);
				{
				setState(185); match(BOOLEAN);
				setState(187);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LOGIC) | (1L << BOOL_EXPR) | (1L << ASSIGN) | (1L << MATH))) != 0)) {
					{
					setState(186); expressionz();
					}
				}

				}
				break;
			case NULL:
				enterOuterAlt(_localctx, 3);
				{
				setState(189); match(NULL);
				setState(191);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LOGIC) | (1L << BOOL_EXPR) | (1L << ASSIGN) | (1L << MATH))) != 0)) {
					{
					setState(190); expressionz();
					}
				}

				}
				break;
			case INT:
				enterOuterAlt(_localctx, 4);
				{
				setState(193); match(INT);
				setState(195);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LOGIC) | (1L << BOOL_EXPR) | (1L << ASSIGN) | (1L << MATH))) != 0)) {
					{
					setState(194); expressionz();
					}
				}

				}
				break;
			case CHAR:
				enterOuterAlt(_localctx, 5);
				{
				setState(197); match(CHAR);
				setState(199);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LOGIC) | (1L << BOOL_EXPR) | (1L << ASSIGN) | (1L << MATH))) != 0)) {
					{
					setState(198); expressionz();
					}
				}

				}
				break;
			case THIS:
			case ID:
				enterOuterAlt(_localctx, 6);
				{
				setState(201);
				_la = _input.LA(1);
				if ( !(_la==THIS || _la==ID) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				setState(203);
				_la = _input.LA(1);
				if (_la==LPAREN || _la==LSQUARE) {
					{
					setState(202); fn_arr_member();
					}
				}

				setState(206);
				_la = _input.LA(1);
				if (_la==DOT) {
					{
					setState(205); member_refz();
					}
				}

				setState(209);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LOGIC) | (1L << BOOL_EXPR) | (1L << ASSIGN) | (1L << MATH))) != 0)) {
					{
					setState(208); expressionz();
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionzContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode BOOL_EXPR() { return getToken(KXIParser.BOOL_EXPR, 0); }
		public TerminalNode LOGIC() { return getToken(KXIParser.LOGIC, 0); }
		public Assignment_expressionContext assignment_expression() {
			return getRuleContext(Assignment_expressionContext.class,0);
		}
		public TerminalNode MATH() { return getToken(KXIParser.MATH, 0); }
		public TerminalNode ASSIGN() { return getToken(KXIParser.ASSIGN, 0); }
		public ExpressionzContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionz; }
	}

	public final ExpressionzContext expressionz() throws RecognitionException {
		ExpressionzContext _localctx = new ExpressionzContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_expressionz);
		try {
			setState(221);
			switch (_input.LA(1)) {
			case BOOL_EXPR:
				enterOuterAlt(_localctx, 1);
				{
				setState(213); match(BOOL_EXPR);
				setState(214); expression();
				}
				break;
			case MATH:
				enterOuterAlt(_localctx, 2);
				{
				setState(215); match(MATH);
				setState(216); expression();
				}
				break;
			case LOGIC:
				enterOuterAlt(_localctx, 3);
				{
				setState(217); match(LOGIC);
				setState(218); expression();
				}
				break;
			case ASSIGN:
				enterOuterAlt(_localctx, 4);
				{
				setState(219); match(ASSIGN);
				setState(220); assignment_expression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Fn_arr_memberContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Argument_listContext argument_list() {
			return getRuleContext(Argument_listContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(KXIParser.RPAREN, 0); }
		public TerminalNode LSQUARE() { return getToken(KXIParser.LSQUARE, 0); }
		public TerminalNode RSQUARE() { return getToken(KXIParser.RSQUARE, 0); }
		public TerminalNode LPAREN() { return getToken(KXIParser.LPAREN, 0); }
		public Fn_arr_memberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fn_arr_member; }
	}

	public final Fn_arr_memberContext fn_arr_member() throws RecognitionException {
		Fn_arr_memberContext _localctx = new Fn_arr_memberContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_fn_arr_member);
		int _la;
		try {
			setState(232);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(223); match(LPAREN);
				setState(225);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NULL) | (1L << THIS) | (1L << CHAR) | (1L << BOOLEAN) | (1L << ID) | (1L << INT) | (1L << LPAREN))) != 0)) {
					{
					setState(224); argument_list();
					}
				}

				setState(227); match(RPAREN);
				}
				break;
			case LSQUARE:
				enterOuterAlt(_localctx, 2);
				{
				setState(228); match(LSQUARE);
				setState(229); expression();
				setState(230); match(RSQUARE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Argument_listContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(KXIParser.COMMA, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(KXIParser.COMMA); }
		public Argument_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument_list; }
	}

	public final Argument_listContext argument_list() throws RecognitionException {
		Argument_listContext _localctx = new Argument_listContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_argument_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(234); expression();
			setState(239);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(235); match(COMMA);
				setState(236); expression();
				}
				}
				setState(241);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Assignment_expressionContext extends ParserRuleContext {
		public TerminalNode NEW() { return getToken(KXIParser.NEW, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(KXIParser.RPAREN, 0); }
		public TerminalNode KEYFUNC() { return getToken(KXIParser.KEYFUNC, 0); }
		public New_declarationContext new_declaration() {
			return getRuleContext(New_declarationContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(KXIParser.LPAREN, 0); }
		public Assignment_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment_expression; }
	}

	public final Assignment_expressionContext assignment_expression() throws RecognitionException {
		Assignment_expressionContext _localctx = new Assignment_expressionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_assignment_expression);
		try {
			setState(252);
			switch (_input.LA(1)) {
			case NULL:
			case THIS:
			case CHAR:
			case BOOLEAN:
			case ID:
			case INT:
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(242); expression();
				}
				break;
			case NEW:
				enterOuterAlt(_localctx, 2);
				{
				setState(243); match(NEW);
				setState(244); type();
				setState(245); new_declaration();
				}
				break;
			case KEYFUNC:
				enterOuterAlt(_localctx, 3);
				{
				setState(247); match(KEYFUNC);
				setState(248); match(LPAREN);
				setState(249); expression();
				setState(250); match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode VOID() { return getToken(KXIParser.VOID, 0); }
		public Class_nameContext class_name() {
			return getRuleContext(Class_nameContext.class,0);
		}
		public TerminalNode PRIMITIVE_TYPE() { return getToken(KXIParser.PRIMITIVE_TYPE, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_type);
		try {
			setState(257);
			switch (_input.LA(1)) {
			case PRIMITIVE_TYPE:
				enterOuterAlt(_localctx, 1);
				{
				setState(254); match(PRIMITIVE_TYPE);
				}
				break;
			case VOID:
				enterOuterAlt(_localctx, 2);
				{
				setState(255); match(VOID);
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 3);
				{
				setState(256); class_name();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Class_nameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(KXIParser.ID, 0); }
		public Class_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_class_name; }
	}

	public final Class_nameContext class_name() throws RecognitionException {
		Class_nameContext _localctx = new Class_nameContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_class_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(259); match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class New_declarationContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Argument_listContext argument_list() {
			return getRuleContext(Argument_listContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(KXIParser.RPAREN, 0); }
		public TerminalNode LSQUARE() { return getToken(KXIParser.LSQUARE, 0); }
		public TerminalNode RSQUARE() { return getToken(KXIParser.RSQUARE, 0); }
		public TerminalNode LPAREN() { return getToken(KXIParser.LPAREN, 0); }
		public New_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_new_declaration; }
	}

	public final New_declarationContext new_declaration() throws RecognitionException {
		New_declarationContext _localctx = new New_declarationContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_new_declaration);
		int _la;
		try {
			setState(270);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(261); match(LPAREN);
				setState(263);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NULL) | (1L << THIS) | (1L << CHAR) | (1L << BOOLEAN) | (1L << ID) | (1L << INT) | (1L << LPAREN))) != 0)) {
					{
					setState(262); argument_list();
					}
				}

				setState(265); match(RPAREN);
				}
				break;
			case LSQUARE:
				enterOuterAlt(_localctx, 2);
				{
				setState(266); match(LSQUARE);
				setState(267); expression();
				setState(268); match(RSQUARE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Member_refzContext extends ParserRuleContext {
		public Fn_arr_memberContext fn_arr_member() {
			return getRuleContext(Fn_arr_memberContext.class,0);
		}
		public TerminalNode ID() { return getToken(KXIParser.ID, 0); }
		public TerminalNode DOT() { return getToken(KXIParser.DOT, 0); }
		public Member_refzContext member_refz() {
			return getRuleContext(Member_refzContext.class,0);
		}
		public Member_refzContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_member_refz; }
	}

	public final Member_refzContext member_refz() throws RecognitionException {
		Member_refzContext _localctx = new Member_refzContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_member_refz);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(272); match(DOT);
			setState(273); match(ID);
			setState(275);
			_la = _input.LA(1);
			if (_la==LPAREN || _la==LSQUARE) {
				{
				setState(274); fn_arr_member();
				}
			}

			setState(278);
			_la = _input.LA(1);
			if (_la==DOT) {
				{
				setState(277); member_refz();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\2\3(\u011b\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4"+
		"\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20"+
		"\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\3\2\7\2*\n\2\f\2\16\2-\13\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\7\3:\n\3\f\3\16\3=\13\3\3\3"+
		"\3\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4G\n\4\3\5\3\5\5\5K\n\5\3\5\3\5\5\5O\n"+
		"\5\3\5\3\5\3\5\5\5T\n\5\3\5\3\5\5\5X\n\5\3\6\3\6\3\6\5\6]\n\6\3\6\3\6"+
		"\3\6\3\7\3\7\7\7d\n\7\f\7\16\7g\13\7\3\7\7\7j\n\7\f\7\16\7m\13\7\3\7\3"+
		"\7\3\b\3\b\3\b\3\b\5\bu\n\b\3\b\3\b\5\by\n\b\3\b\3\b\3\t\3\t\3\t\7\t\u0080"+
		"\n\t\f\t\16\t\u0083\13\t\3\n\3\n\3\n\3\n\5\n\u0089\n\n\3\13\3\13\7\13"+
		"\u008d\n\13\f\13\16\13\u0090\13\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\5\13\u009d\n\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\5\13\u00a7\n\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\5\13\u00b4\n\13\3\f\3\f\3\f\3\f\5\f\u00ba\n\f\3\f\3\f\5\f\u00be"+
		"\n\f\3\f\3\f\5\f\u00c2\n\f\3\f\3\f\5\f\u00c6\n\f\3\f\3\f\5\f\u00ca\n\f"+
		"\3\f\3\f\5\f\u00ce\n\f\3\f\5\f\u00d1\n\f\3\f\5\f\u00d4\n\f\5\f\u00d6\n"+
		"\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00e0\n\r\3\16\3\16\5\16\u00e4"+
		"\n\16\3\16\3\16\3\16\3\16\3\16\5\16\u00eb\n\16\3\17\3\17\3\17\7\17\u00f0"+
		"\n\17\f\17\16\17\u00f3\13\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\5\20\u00ff\n\20\3\21\3\21\3\21\5\21\u0104\n\21\3\22\3\22\3\23"+
		"\3\23\5\23\u010a\n\23\3\23\3\23\3\23\3\23\3\23\5\23\u0111\n\23\3\24\3"+
		"\24\3\24\5\24\u0116\n\24\3\24\5\24\u0119\n\24\3\24\2\25\2\4\6\b\n\f\16"+
		"\20\22\24\26\30\32\34\36 \"$&\2\3\4\r\r\26\26\u0139\2+\3\2\2\2\4\65\3"+
		"\2\2\2\6F\3\2\2\2\bW\3\2\2\2\nY\3\2\2\2\fa\3\2\2\2\16p\3\2\2\2\20|\3\2"+
		"\2\2\22\u0084\3\2\2\2\24\u00b3\3\2\2\2\26\u00d5\3\2\2\2\30\u00df\3\2\2"+
		"\2\32\u00ea\3\2\2\2\34\u00ec\3\2\2\2\36\u00fe\3\2\2\2 \u0103\3\2\2\2\""+
		"\u0105\3\2\2\2$\u0110\3\2\2\2&\u0112\3\2\2\2(*\5\4\3\2)(\3\2\2\2*-\3\2"+
		"\2\2+)\3\2\2\2+,\3\2\2\2,.\3\2\2\2-+\3\2\2\2./\7\6\2\2/\60\7\17\2\2\60"+
		"\61\7\33\2\2\61\62\7\34\2\2\62\63\5\f\7\2\63\64\7\1\2\2\64\3\3\2\2\2\65"+
		"\66\7\13\2\2\66\67\5\"\22\2\67;\7 \2\28:\5\6\4\298\3\2\2\2:=\3\2\2\2;"+
		"9\3\2\2\2;<\3\2\2\2<>\3\2\2\2=;\3\2\2\2>?\7\37\2\2?\5\3\2\2\2@A\7\3\2"+
		"\2AB\5 \21\2BC\7\26\2\2CD\5\b\5\2DG\3\2\2\2EG\5\n\6\2F@\3\2\2\2FE\3\2"+
		"\2\2G\7\3\2\2\2HI\7\36\2\2IK\7\35\2\2JH\3\2\2\2JK\3\2\2\2KN\3\2\2\2LM"+
		"\7\'\2\2MO\5\36\20\2NL\3\2\2\2NO\3\2\2\2OP\3\2\2\2PX\7\32\2\2QS\7\33\2"+
		"\2RT\5\20\t\2SR\3\2\2\2ST\3\2\2\2TU\3\2\2\2UV\7\34\2\2VX\5\f\7\2WJ\3\2"+
		"\2\2WQ\3\2\2\2X\t\3\2\2\2YZ\5\"\22\2Z\\\7\33\2\2[]\5\20\t\2\\[\3\2\2\2"+
		"\\]\3\2\2\2]^\3\2\2\2^_\7\34\2\2_`\5\f\7\2`\13\3\2\2\2ae\7 \2\2bd\5\16"+
		"\b\2cb\3\2\2\2dg\3\2\2\2ec\3\2\2\2ef\3\2\2\2fk\3\2\2\2ge\3\2\2\2hj\5\24"+
		"\13\2ih\3\2\2\2jm\3\2\2\2ki\3\2\2\2kl\3\2\2\2ln\3\2\2\2mk\3\2\2\2no\7"+
		"\37\2\2o\r\3\2\2\2pq\5 \21\2qt\7\26\2\2rs\7\36\2\2su\7\35\2\2tr\3\2\2"+
		"\2tu\3\2\2\2ux\3\2\2\2vw\7\'\2\2wy\5\36\20\2xv\3\2\2\2xy\3\2\2\2yz\3\2"+
		"\2\2z{\7\32\2\2{\17\3\2\2\2|\u0081\5\22\n\2}~\7\31\2\2~\u0080\5\22\n\2"+
		"\177}\3\2\2\2\u0080\u0083\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082\3\2\2"+
		"\2\u0082\21\3\2\2\2\u0083\u0081\3\2\2\2\u0084\u0085\5 \21\2\u0085\u0088"+
		"\7\26\2\2\u0086\u0087\7\36\2\2\u0087\u0089\7\35\2\2\u0088\u0086\3\2\2"+
		"\2\u0088\u0089\3\2\2\2\u0089\23\3\2\2\2\u008a\u008e\7 \2\2\u008b\u008d"+
		"\5\24\13\2\u008c\u008b\3\2\2\2\u008d\u0090\3\2\2\2\u008e\u008c\3\2\2\2"+
		"\u008e\u008f\3\2\2\2\u008f\u0091\3\2\2\2\u0090\u008e\3\2\2\2\u0091\u00b4"+
		"\7\37\2\2\u0092\u0093\5\26\f\2\u0093\u0094\7\32\2\2\u0094\u00b4\3\2\2"+
		"\2\u0095\u0096\7\7\2\2\u0096\u0097\7\33\2\2\u0097\u0098\5\26\f\2\u0098"+
		"\u0099\7\34\2\2\u0099\u009c\5\24\13\2\u009a\u009b\7\b\2\2\u009b\u009d"+
		"\5\24\13\2\u009c\u009a\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u00b4\3\2\2\2"+
		"\u009e\u009f\7\t\2\2\u009f\u00a0\7\33\2\2\u00a0\u00a1\5\26\f\2\u00a1\u00a2"+
		"\7\34\2\2\u00a2\u00a3\5\24\13\2\u00a3\u00b4\3\2\2\2\u00a4\u00a6\7\n\2"+
		"\2\u00a5\u00a7\5\26\f\2\u00a6\u00a5\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7"+
		"\u00a8\3\2\2\2\u00a8\u00b4\7\32\2\2\u00a9\u00aa\7\20\2\2\u00aa\u00ab\7"+
		"$\2\2\u00ab\u00ac\5\26\f\2\u00ac\u00ad\7\32\2\2\u00ad\u00b4\3\2\2\2\u00ae"+
		"\u00af\7\21\2\2\u00af\u00b0\7%\2\2\u00b0\u00b1\5\26\f\2\u00b1\u00b2\7"+
		"\32\2\2\u00b2\u00b4\3\2\2\2\u00b3\u008a\3\2\2\2\u00b3\u0092\3\2\2\2\u00b3"+
		"\u0095\3\2\2\2\u00b3\u009e\3\2\2\2\u00b3\u00a4\3\2\2\2\u00b3\u00a9\3\2"+
		"\2\2\u00b3\u00ae\3\2\2\2\u00b4\25\3\2\2\2\u00b5\u00b6\7\33\2\2\u00b6\u00b7"+
		"\5\26\f\2\u00b7\u00b9\7\34\2\2\u00b8\u00ba\5\30\r\2\u00b9\u00b8\3\2\2"+
		"\2\u00b9\u00ba\3\2\2\2\u00ba\u00d6\3\2\2\2\u00bb\u00bd\7\25\2\2\u00bc"+
		"\u00be\5\30\r\2\u00bd\u00bc\3\2\2\2\u00bd\u00be\3\2\2\2\u00be\u00d6\3"+
		"\2\2\2\u00bf\u00c1\7\f\2\2\u00c0\u00c2\5\30\r\2\u00c1\u00c0\3\2\2\2\u00c1"+
		"\u00c2\3\2\2\2\u00c2\u00d6\3\2\2\2\u00c3\u00c5\7\27\2\2\u00c4\u00c6\5"+
		"\30\r\2\u00c5\u00c4\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\u00d6\3\2\2\2\u00c7"+
		"\u00c9\7\22\2\2\u00c8\u00ca\5\30\r\2\u00c9\u00c8\3\2\2\2\u00c9\u00ca\3"+
		"\2\2\2\u00ca\u00d6\3\2\2\2\u00cb\u00cd\t\2\2\2\u00cc\u00ce\5\32\16\2\u00cd"+
		"\u00cc\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce\u00d0\3\2\2\2\u00cf\u00d1\5&"+
		"\24\2\u00d0\u00cf\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d3\3\2\2\2\u00d2"+
		"\u00d4\5\30\r\2\u00d3\u00d2\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d6\3"+
		"\2\2\2\u00d5\u00b5\3\2\2\2\u00d5\u00bb\3\2\2\2\u00d5\u00bf\3\2\2\2\u00d5"+
		"\u00c3\3\2\2\2\u00d5\u00c7\3\2\2\2\u00d5\u00cb\3\2\2\2\u00d6\27\3\2\2"+
		"\2\u00d7\u00d8\7&\2\2\u00d8\u00e0\5\26\f\2\u00d9\u00da\7(\2\2\u00da\u00e0"+
		"\5\26\f\2\u00db\u00dc\7\24\2\2\u00dc\u00e0\5\26\f\2\u00dd\u00de\7\'\2"+
		"\2\u00de\u00e0\5\36\20\2\u00df\u00d7\3\2\2\2\u00df\u00d9\3\2\2\2\u00df"+
		"\u00db\3\2\2\2\u00df\u00dd\3\2\2\2\u00e0\31\3\2\2\2\u00e1\u00e3\7\33\2"+
		"\2\u00e2\u00e4\5\34\17\2\u00e3\u00e2\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4"+
		"\u00e5\3\2\2\2\u00e5\u00eb\7\34\2\2\u00e6\u00e7\7\36\2\2\u00e7\u00e8\5"+
		"\26\f\2\u00e8\u00e9\7\35\2\2\u00e9\u00eb\3\2\2\2\u00ea\u00e1\3\2\2\2\u00ea"+
		"\u00e6\3\2\2\2\u00eb\33\3\2\2\2\u00ec\u00f1\5\26\f\2\u00ed\u00ee\7\31"+
		"\2\2\u00ee\u00f0\5\26\f\2\u00ef\u00ed\3\2\2\2\u00f0\u00f3\3\2\2\2\u00f1"+
		"\u00ef\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\35\3\2\2\2\u00f3\u00f1\3\2\2"+
		"\2\u00f4\u00ff\5\26\f\2\u00f5\u00f6\7\16\2\2\u00f6\u00f7\5 \21\2\u00f7"+
		"\u00f8\5$\23\2\u00f8\u00ff\3\2\2\2\u00f9\u00fa\7\4\2\2\u00fa\u00fb\7\33"+
		"\2\2\u00fb\u00fc\5\26\f\2\u00fc\u00fd\7\34\2\2\u00fd\u00ff\3\2\2\2\u00fe"+
		"\u00f4\3\2\2\2\u00fe\u00f5\3\2\2\2\u00fe\u00f9\3\2\2\2\u00ff\37\3\2\2"+
		"\2\u0100\u0104\7\5\2\2\u0101\u0104\7\6\2\2\u0102\u0104\5\"\22\2\u0103"+
		"\u0100\3\2\2\2\u0103\u0101\3\2\2\2\u0103\u0102\3\2\2\2\u0104!\3\2\2\2"+
		"\u0105\u0106\7\26\2\2\u0106#\3\2\2\2\u0107\u0109\7\33\2\2\u0108\u010a"+
		"\5\34\17\2\u0109\u0108\3\2\2\2\u0109\u010a\3\2\2\2\u010a\u010b\3\2\2\2"+
		"\u010b\u0111\7\34\2\2\u010c\u010d\7\36\2\2\u010d\u010e\5\26\f\2\u010e"+
		"\u010f\7\35\2\2\u010f\u0111\3\2\2\2\u0110\u0107\3\2\2\2\u0110\u010c\3"+
		"\2\2\2\u0111%\3\2\2\2\u0112\u0113\7\"\2\2\u0113\u0115\7\26\2\2\u0114\u0116"+
		"\5\32\16\2\u0115\u0114\3\2\2\2\u0115\u0116\3\2\2\2\u0116\u0118\3\2\2\2"+
		"\u0117\u0119\5&\24\2\u0118\u0117\3\2\2\2\u0118\u0119\3\2\2\2\u0119\'\3"+
		"\2\2\2\'+;FJNSW\\ektx\u0081\u0088\u008e\u009c\u00a6\u00b3\u00b9\u00bd"+
		"\u00c1\u00c5\u00c9\u00cd\u00d0\u00d3\u00d5\u00df\u00e3\u00ea\u00f1\u00fe"+
		"\u0103\u0109\u0110\u0115\u0118";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}