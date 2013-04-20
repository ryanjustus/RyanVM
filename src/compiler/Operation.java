package compiler;

import com.sun.org.apache.xpath.internal.Arg;
import grammar.expression.IExpression;

import javax.imageio.spi.RegisterableService;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 3/14/13 2:52 PM
 */
public class Operation {
	public final  Op op;
	public final Object arg1,arg2;
	public final int numArgs;
	public String label;
	public String comment ="";

	public Operation(Op op, Object arg){
		numArgs=1;
		arg2=null;
		this.arg1=arg;
		this.op = op;
	}

	public Operation(Op op, Object arg1, Object arg2){
		numArgs=2;
		this.arg2=arg2;
		this.arg1=arg1;
		this.op = op;
	}

	public Operation convertRegisterNames(){
		Operation o = this;
	  	//Convert register names
		if(o.arg1.equals("SP")){
			o = new Operation(o.op,"R31",o.arg2);
		}else if(o.arg1.equals("FP")){
			o = new Operation(o.op,"R30",o.arg2);
		}else if(o.arg1.equals("HP")){
			o = new Operation(o.op,"R29",o.arg2);
		}else if(o.arg1.equals("IO")){
			o = new Operation(o.op,"R15",o.arg2);
		}

		if(o.arg2!=null){
			if(o.arg2.equals("SP")){
				o = new Operation(o.op,o.arg1,"R31");
			}else if(o.arg2.equals("FP")){
				o = new Operation(o.op,o.arg1,"R30");
			}else if(o.arg2.equals("HP")){
				o = new Operation(o.op,o.arg1, "R29");
			}else if(o.arg2.equals("IO")){
				o = new Operation(o.op,o.arg1, "R15");
			}
		}

		o.comment = this.comment;
		o.label = this.label;
		return o;
	}

	public String toString(){
		String opStr = op.toString();
		if(op ==Op.INT || op==Op.BYT){
			opStr = "."+opStr;
		}
		comment = comment.replaceAll(";","");
		comment = comment.replaceAll("\\|\\|"," OR ");
		return (label==null ? "\t\t\t":label+"\t") +opStr+" "+arg1+ (arg2==null ? "" : " "+arg2) +";"+ ((!comment.isEmpty()) ? "\t "+comment :"");
	}
}
