package grammar;

import compiler.Op;
import compiler.Operation;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 4/10/13 2:51 PM
 */
public class Constant {
	public enum Type{
		INT,
		BYTE,
		CHAR
	}

	public final Type t;
	public final Object v;
	public Constant(Object value,Type type){
		this.t=type;
		this.v=value;
	}

	public String toString(){
		switch (t){
			case INT:
				return ".INT "+v;
			case BYTE:
				return ".BYT "+v;
			case CHAR:
				return ".BYT "+v;
			default:
				throw new IllegalStateException("CANT BE HERE");

		}
	}

	public Operation getOperation(String label){
		Operation dir;
		switch (t){
			case INT:
				dir = new Operation(Op.INT,v);
				break;
			case BYTE:
				dir = new Operation(Op.INT, v);
				break;
			case CHAR:
				dir = new Operation(Op.BYT,v);
				break;
			default:
				throw new IllegalStateException("CANT BE HERE");

		}
		dir.label=label;
		return dir;
	}

	public boolean equals(Object o){
		if(o instanceof Constant){
		Constant c = (Constant)o;
		return t.equals(c.t) && v.equals(c.v);
		}
		return false;
	}

	public int hashCode(){
		int h = 17;
		h += v.hashCode()*23;
		h += t.hashCode()*23;
		return h;
	}
}
