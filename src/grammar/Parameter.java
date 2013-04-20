package grammar;

import parser.KXIParser;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 1/29/13 5:08 PM
 */
public class Parameter {
	final Type t;
	final String idLexeme;
	final KXIParser.ParameterContext context;
	public Parameter(KXIParser.ParameterContext context){
		this.context=context;
		idLexeme = context.ID().getText();
		boolean isArray = (context.LSQUARE()!=null);
		t = new Type(isArray,context.type());
	}

	public Parameter(Type t, String idLexeme){
		this.t=t;
		this.idLexeme=idLexeme;
		this.context=null;
	}

	public Type getType(){
		return t;
	}

	public String getId(){
		return  idLexeme;
	}
}
