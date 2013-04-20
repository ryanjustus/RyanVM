// Generated from C:\Users\ryan\KXI.g4 by ANTLR 4.0
package parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class KXILexer extends Lexer {
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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"MODIFIER", "KEYFUNC", "PRIMITIVE_TYPE", "'void'", "'if'", "'else'", "'while'", 
		"'return'", "'class'", "'null'", "'this'", "'new'", "'main'", "'cin'", 
		"'cout'", "CHAR", "COMMENT", "LOGIC", "BOOLEAN", "ID", "INT", "WS", "','", 
		"';'", "'('", "')'", "']'", "'['", "'}'", "'{'", "'|'", "'.'", "'?'", 
		"'>>'", "'<<'", "BOOL_EXPR", "'='", "MATH"
	};
	public static final String[] ruleNames = {
		"MODIFIER", "KEYFUNC", "PRIMITIVE_TYPE", "VOID", "IF", "ELSE", "WHILE", 
		"RETURN", "CLASS", "NULL", "THIS", "NEW", "MAIN", "CIN", "COUT", "CHAR", 
		"HEX_DIGIT", "ESC_SEQ", "OCTAL_ESC", "UNICODE_ESC", "COMMENT", "LOGIC", 
		"BOOLEAN", "ID", "INT", "WS", "COMMA", "SEMICOLON", "LPAREN", "RPAREN", 
		"RSQUARE", "LSQUARE", "RBRACKET", "LBRACKET", "OR", "DOT", "QUERY", "IN", 
		"OUT", "BOOL_EXPR", "ASSIGN", "MATH"
	};


	public KXILexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "KXI.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 20: COMMENT_action((RuleContext)_localctx, actionIndex); break;

		case 25: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1: skip();  break;
		}
	}
	private void COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\2\4(\u0148\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t"+
		"\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20"+
		"\t\20\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27"+
		"\t\27\4\30\t\30\4\31\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36"+
		"\t\36\4\37\t\37\4 \t \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4"+
		"(\t(\4)\t)\4*\t*\4+\t+\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\5\2e\n\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3o\n\3\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\5\4\u0088\n\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3"+
		"\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r"+
		"\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20"+
		"\3\21\3\21\3\21\5\21\u00c9\n\21\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3\23"+
		"\5\23\u00d3\n\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u00de"+
		"\n\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\7\26\u00eb"+
		"\n\26\f\26\16\26\u00ee\13\26\3\26\5\26\u00f1\n\26\3\26\3\26\3\26\3\26"+
		"\3\27\3\27\3\27\3\27\5\27\u00fb\n\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\5\30\u0106\n\30\3\31\3\31\7\31\u010a\n\31\f\31\16\31\u010d"+
		"\13\31\3\32\5\32\u0110\n\32\3\32\6\32\u0113\n\32\r\32\16\32\u0114\3\33"+
		"\6\33\u0118\n\33\r\33\16\33\u0119\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3"+
		"\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3\'"+
		"\3(\3(\3(\3)\3)\3)\3)\3)\3)\3)\3)\3)\5)\u0143\n)\3*\3*\3+\3+\2,\3\3\1"+
		"\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n\1\23\13\1\25\f\1\27\r\1\31"+
		"\16\1\33\17\1\35\20\1\37\21\1!\22\1#\2\1%\2\1\'\2\1)\2\1+\23\2-\24\1/"+
		"\25\1\61\26\1\63\27\1\65\30\3\67\31\19\32\1;\33\1=\34\1?\35\1A\36\1C\37"+
		"\1E \1G!\1I\"\1K#\1M$\1O%\1Q&\1S\'\1U(\1\3\2\13\4))^^\5\62;CHch\n$$))"+
		"^^ddhhppttvv\4\f\f\17\17\5C\\aac|\6\62;C\\aac|\5\13\f\16\17\"\"\4>>@@"+
		"\5,-//\61\61\u015a\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13"+
		"\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2"+
		"\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2"+
		"!\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2"+
		"\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2"+
		"A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3"+
		"\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\3d\3\2\2\2\5n\3\2\2"+
		"\2\7\u0087\3\2\2\2\t\u0089\3\2\2\2\13\u008e\3\2\2\2\r\u0091\3\2\2\2\17"+
		"\u0096\3\2\2\2\21\u009c\3\2\2\2\23\u00a3\3\2\2\2\25\u00a9\3\2\2\2\27\u00ae"+
		"\3\2\2\2\31\u00b3\3\2\2\2\33\u00b7\3\2\2\2\35\u00bc\3\2\2\2\37\u00c0\3"+
		"\2\2\2!\u00c5\3\2\2\2#\u00cc\3\2\2\2%\u00d2\3\2\2\2\'\u00dd\3\2\2\2)\u00df"+
		"\3\2\2\2+\u00e6\3\2\2\2-\u00fa\3\2\2\2/\u0105\3\2\2\2\61\u0107\3\2\2\2"+
		"\63\u010f\3\2\2\2\65\u0117\3\2\2\2\67\u011d\3\2\2\29\u011f\3\2\2\2;\u0121"+
		"\3\2\2\2=\u0123\3\2\2\2?\u0125\3\2\2\2A\u0127\3\2\2\2C\u0129\3\2\2\2E"+
		"\u012b\3\2\2\2G\u012d\3\2\2\2I\u012f\3\2\2\2K\u0131\3\2\2\2M\u0133\3\2"+
		"\2\2O\u0136\3\2\2\2Q\u0142\3\2\2\2S\u0144\3\2\2\2U\u0146\3\2\2\2WX\7r"+
		"\2\2XY\7w\2\2YZ\7d\2\2Z[\7n\2\2[\\\7k\2\2\\e\7e\2\2]^\7r\2\2^_\7t\2\2"+
		"_`\7k\2\2`a\7x\2\2ab\7c\2\2bc\7v\2\2ce\7g\2\2dW\3\2\2\2d]\3\2\2\2e\4\3"+
		"\2\2\2fg\7c\2\2gh\7v\2\2hi\7q\2\2io\7k\2\2jk\7k\2\2kl\7v\2\2lm\7q\2\2"+
		"mo\7c\2\2nf\3\2\2\2nj\3\2\2\2o\6\3\2\2\2pq\7d\2\2qr\7q\2\2rs\7q\2\2s\u0088"+
		"\7n\2\2tu\7e\2\2uv\7j\2\2vw\7c\2\2w\u0088\7t\2\2xy\7k\2\2yz\7p\2\2z\u0088"+
		"\7v\2\2{|\7q\2\2|}\7d\2\2}~\7l\2\2~\177\7g\2\2\177\u0080\7e\2\2\u0080"+
		"\u0088\7v\2\2\u0081\u0082\7u\2\2\u0082\u0083\7v\2\2\u0083\u0084\7t\2\2"+
		"\u0084\u0085\7k\2\2\u0085\u0086\7p\2\2\u0086\u0088\7i\2\2\u0087p\3\2\2"+
		"\2\u0087t\3\2\2\2\u0087x\3\2\2\2\u0087{\3\2\2\2\u0087\u0081\3\2\2\2\u0088"+
		"\b\3\2\2\2\u0089\u008a\7x\2\2\u008a\u008b\7q\2\2\u008b\u008c\7k\2\2\u008c"+
		"\u008d\7f\2\2\u008d\n\3\2\2\2\u008e\u008f\7k\2\2\u008f\u0090\7h\2\2\u0090"+
		"\f\3\2\2\2\u0091\u0092\7g\2\2\u0092\u0093\7n\2\2\u0093\u0094\7u\2\2\u0094"+
		"\u0095\7g\2\2\u0095\16\3\2\2\2\u0096\u0097\7y\2\2\u0097\u0098\7j\2\2\u0098"+
		"\u0099\7k\2\2\u0099\u009a\7n\2\2\u009a\u009b\7g\2\2\u009b\20\3\2\2\2\u009c"+
		"\u009d\7t\2\2\u009d\u009e\7g\2\2\u009e\u009f\7v\2\2\u009f\u00a0\7w\2\2"+
		"\u00a0\u00a1\7t\2\2\u00a1\u00a2\7p\2\2\u00a2\22\3\2\2\2\u00a3\u00a4\7"+
		"e\2\2\u00a4\u00a5\7n\2\2\u00a5\u00a6\7c\2\2\u00a6\u00a7\7u\2\2\u00a7\u00a8"+
		"\7u\2\2\u00a8\24\3\2\2\2\u00a9\u00aa\7p\2\2\u00aa\u00ab\7w\2\2\u00ab\u00ac"+
		"\7n\2\2\u00ac\u00ad\7n\2\2\u00ad\26\3\2\2\2\u00ae\u00af\7v\2\2\u00af\u00b0"+
		"\7j\2\2\u00b0\u00b1\7k\2\2\u00b1\u00b2\7u\2\2\u00b2\30\3\2\2\2\u00b3\u00b4"+
		"\7p\2\2\u00b4\u00b5\7g\2\2\u00b5\u00b6\7y\2\2\u00b6\32\3\2\2\2\u00b7\u00b8"+
		"\7o\2\2\u00b8\u00b9\7c\2\2\u00b9\u00ba\7k\2\2\u00ba\u00bb\7p\2\2\u00bb"+
		"\34\3\2\2\2\u00bc\u00bd\7e\2\2\u00bd\u00be\7k\2\2\u00be\u00bf\7p\2\2\u00bf"+
		"\36\3\2\2\2\u00c0\u00c1\7e\2\2\u00c1\u00c2\7q\2\2\u00c2\u00c3\7w\2\2\u00c3"+
		"\u00c4\7v\2\2\u00c4 \3\2\2\2\u00c5\u00c8\7)\2\2\u00c6\u00c9\5%\23\2\u00c7"+
		"\u00c9\n\2\2\2\u00c8\u00c6\3\2\2\2\u00c8\u00c7\3\2\2\2\u00c9\u00ca\3\2"+
		"\2\2\u00ca\u00cb\7)\2\2\u00cb\"\3\2\2\2\u00cc\u00cd\t\3\2\2\u00cd$\3\2"+
		"\2\2\u00ce\u00cf\7^\2\2\u00cf\u00d3\t\4\2\2\u00d0\u00d3\5)\25\2\u00d1"+
		"\u00d3\5\'\24\2\u00d2\u00ce\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d2\u00d1\3"+
		"\2\2\2\u00d3&\3\2\2\2\u00d4\u00d5\7^\2\2\u00d5\u00d6\4\62\65\2\u00d6\u00d7"+
		"\4\629\2\u00d7\u00de\4\629\2\u00d8\u00d9\7^\2\2\u00d9\u00da\4\629\2\u00da"+
		"\u00de\4\629\2\u00db\u00dc\7^\2\2\u00dc\u00de\4\629\2\u00dd\u00d4\3\2"+
		"\2\2\u00dd\u00d8\3\2\2\2\u00dd\u00db\3\2\2\2\u00de(\3\2\2\2\u00df\u00e0"+
		"\7^\2\2\u00e0\u00e1\7w\2\2\u00e1\u00e2\5#\22\2\u00e2\u00e3\5#\22\2\u00e3"+
		"\u00e4\5#\22\2\u00e4\u00e5\5#\22\2\u00e5*\3\2\2\2\u00e6\u00e7\7\61\2\2"+
		"\u00e7\u00e8\7\61\2\2\u00e8\u00ec\3\2\2\2\u00e9\u00eb\n\5\2\2\u00ea\u00e9"+
		"\3\2\2\2\u00eb\u00ee\3\2\2\2\u00ec\u00ea\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed"+
		"\u00f0\3\2\2\2\u00ee\u00ec\3\2\2\2\u00ef\u00f1\7\17\2\2\u00f0\u00ef\3"+
		"\2\2\2\u00f0\u00f1\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f3\7\f\2\2\u00f3"+
		"\u00f4\3\2\2\2\u00f4\u00f5\b\26\2\2\u00f5,\3\2\2\2\u00f6\u00f7\7(\2\2"+
		"\u00f7\u00fb\7(\2\2\u00f8\u00f9\7~\2\2\u00f9\u00fb\7~\2\2\u00fa\u00f6"+
		"\3\2\2\2\u00fa\u00f8\3\2\2\2\u00fb.\3\2\2\2\u00fc\u00fd\7v\2\2\u00fd\u00fe"+
		"\7t\2\2\u00fe\u00ff\7w\2\2\u00ff\u0106\7g\2\2\u0100\u0101\7h\2\2\u0101"+
		"\u0102\7c\2\2\u0102\u0103\7n\2\2\u0103\u0104\7u\2\2\u0104\u0106\7g\2\2"+
		"\u0105\u00fc\3\2\2\2\u0105\u0100\3\2\2\2\u0106\60\3\2\2\2\u0107\u010b"+
		"\t\6\2\2\u0108\u010a\t\7\2\2\u0109\u0108\3\2\2\2\u010a\u010d\3\2\2\2\u010b"+
		"\u0109\3\2\2\2\u010b\u010c\3\2\2\2\u010c\62\3\2\2\2\u010d\u010b\3\2\2"+
		"\2\u010e\u0110\7/\2\2\u010f\u010e\3\2\2\2\u010f\u0110\3\2\2\2\u0110\u0112"+
		"\3\2\2\2\u0111\u0113\4\62;\2\u0112\u0111\3\2\2\2\u0113\u0114\3\2\2\2\u0114"+
		"\u0112\3\2\2\2\u0114\u0115\3\2\2\2\u0115\64\3\2\2\2\u0116\u0118\t\b\2"+
		"\2\u0117\u0116\3\2\2\2\u0118\u0119\3\2\2\2\u0119\u0117\3\2\2\2\u0119\u011a"+
		"\3\2\2\2\u011a\u011b\3\2\2\2\u011b\u011c\b\33\3\2\u011c\66\3\2\2\2\u011d"+
		"\u011e\7.\2\2\u011e8\3\2\2\2\u011f\u0120\7=\2\2\u0120:\3\2\2\2\u0121\u0122"+
		"\7*\2\2\u0122<\3\2\2\2\u0123\u0124\7+\2\2\u0124>\3\2\2\2\u0125\u0126\7"+
		"_\2\2\u0126@\3\2\2\2\u0127\u0128\7]\2\2\u0128B\3\2\2\2\u0129\u012a\7\177"+
		"\2\2\u012aD\3\2\2\2\u012b\u012c\7}\2\2\u012cF\3\2\2\2\u012d\u012e\7~\2"+
		"\2\u012eH\3\2\2\2\u012f\u0130\7\60\2\2\u0130J\3\2\2\2\u0131\u0132\7A\2"+
		"\2\u0132L\3\2\2\2\u0133\u0134\7@\2\2\u0134\u0135\7@\2\2\u0135N\3\2\2\2"+
		"\u0136\u0137\7>\2\2\u0137\u0138\7>\2\2\u0138P\3\2\2\2\u0139\u0143\t\t"+
		"\2\2\u013a\u013b\7?\2\2\u013b\u0143\7?\2\2\u013c\u013d\7@\2\2\u013d\u0143"+
		"\7?\2\2\u013e\u013f\7>\2\2\u013f\u0143\7?\2\2\u0140\u0141\7#\2\2\u0141"+
		"\u0143\7?\2\2\u0142\u0139\3\2\2\2\u0142\u013a\3\2\2\2\u0142\u013c\3\2"+
		"\2\2\u0142\u013e\3\2\2\2\u0142\u0140\3\2\2\2\u0143R\3\2\2\2\u0144\u0145"+
		"\7?\2\2\u0145T\3\2\2\2\u0146\u0147\t\n\2\2\u0147V\3\2\2\2\22\2dn\u0087"+
		"\u00c8\u00d2\u00dd\u00ec\u00f0\u00fa\u0105\u010b\u010f\u0114\u0119\u0142";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}