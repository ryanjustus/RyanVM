package grammar;

import parser.KXIParser;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 1/29/13 5:13 PM
 */
public class Type {

	public boolean isArray;
	public final String lexeme;
	public final boolean primitive;

	public Type(boolean isArray, KXIParser.TypeContext context){
		this.isArray=isArray;
		this.primitive = (context.PRIMITIVE_TYPE()!=null);
		this.lexeme = context.getText();
	}

	public Type(boolean isArray, String lexeme, boolean primitive){
		this.isArray=isArray;
		this.lexeme=lexeme;
		this.primitive=primitive;
	}

	public boolean equals(Object o){
		if(o instanceof Type){
			Type t = (Type)o;
			return (t.lexeme.equals(lexeme) && isArray==t.isArray);
		}
		else{
			return false;
		}
	}

	public String toString(){
		return lexeme + (isArray ?"[]":"");
	}

	//Returns the size in bytes that a single allocation requires
	public int getSize(CompilationUnit cu){
		if(this.primitive){
			return 4; //primitives are all four bytes
		}
		else{
			ClassDeclaration clazz = cu.getClass(this);
			return clazz.fields.size();
		}
	}

	public enum Primitive{
		BOOL(new Type(false, "bool", true)),
		INT(new Type(false, "int", true)),
		CHAR(new Type(false, "char", true)),
		NULL(new Type(false,"null",true)),
		VOID(new Type(false,"void",true));

		final Type t;
		private Primitive(Type t){
			this.t=t;
		}

		public Type type(){
			return t;
		}
	}
}
