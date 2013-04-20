package grammar;

import com.sun.org.apache.bcel.internal.generic.RET;
import compiler.Constants;
import compiler.Op;
import compiler.Operation;
import grammar.error.SemanticException;
import org.antlr.v4.runtime.ParserRuleContext;
import parser.KXIParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 1/29/13 6:03 PM
 */
public class Constructor implements Scope{
	final String nameLexeme;
	private final List<Parameter> parameters;
	final MethodBody body;
	final ClassDeclaration parent;
	final ParserRuleContext context;
	public Constructor(ClassDeclaration parent, KXIParser.Constructor_declarationContext context) {
		this.context=context;
		this.parent=parent;
		nameLexeme = context.class_name().getText();
		parameters  = new ArrayList<Parameter>();
		body = new MethodBody(this,context.method_body());

		KXIParser.Parameter_listContext plc = context.parameter_list();

		if(plc!=null){
			for(KXIParser.ParameterContext p : plc.parameter()){
				Parameter param = new Parameter(p);
				parameters.add(param);
			}
		}
	}

	public boolean signatureMatches(List<Type> parameters){
		if(parameters.size()!=this.parameters.size()){
			return false;
		}
		for(int i=0;i<this.parameters.size();i++){
			Parameter local = this.parameters.get(i);
			if(!local.getType().equals(parameters.get(i))){
				return false;
			}
		}
		return true;
	}

	public Scope getParent() {
		return parent;
	}

	public boolean exists(String var) {
		return existsLocal(var) || parent.exists(var);
	}

	public boolean isPublic(String var) {
		return true;
	}

	private boolean existsLocal(String var){
		if(var.equals("this")){
			return true;
		}
		for(Parameter p: parameters){
			if(p.getId().equals(var)){
				return true;
			}
		}
		return false;
	}

	public Type getType(String var) {
		if(var.equals("this")){
			return getClassDeclaration().getType();
		}

		if(existsLocal(var)){
			for(Parameter p: parameters){
				if(p.getId().equals(var)){
					return p.getType();
				}
			}
		}else if(parent.exists(var)){
			return parent.getType(var);
		}
		return null;
	}

	public List<SemanticException> validateSemantics(List<Type> types) {

		ArrayList<SemanticException> errors = new ArrayList<SemanticException>();

		if(!nameLexeme.equals(parent.idLexeme)){
			errors.add(new SemanticException(context,"Constructor name must match class name: "+nameLexeme));
		}

		Set<String> ids = CompilationUnit.getReserved();
		for(Parameter param : parameters){
			//Check if the lexeme is used in the context
			if(ids.contains(param.idLexeme)){
				errors.add(new SemanticException(param.context,"Identifier already used: "+param.idLexeme));
			}else{
				ids.add(param.idLexeme);
			}

			if(!types.contains(param.getType())){
				errors.add(new SemanticException(param.context,"Undefined type: "+param.getType().lexeme));
			}
		}
		errors.addAll(body.validateSemantics(types));
		return errors;
	}

	public List<Operation> generateCode(){
		List<Operation> assembly = new ArrayList<Operation>();
		//First save return address from R28
		Operation saveRet1 = new Operation(Op.MOV,"R0","FP");
		assembly.add(saveRet1);
		Operation addOffset = new Operation(Op.ADI,"R0",1*Constants.STACK_ELEMENT_SIZE);
		assembly.add(addOffset);
		Operation saveRet2 = new Operation(Op.STR,"R0","R28");
		saveRet2.comment = " save return address into FP+1";
		assembly.add(saveRet2);

		//Add in static initializations
		assembly.addAll(parent.buildFieldInitCode());

		/**
		 * Do method body
		 */
		assembly.addAll(body.generateCode());
		//if the return type is void lets push a void onto the stack

		//Get the return address and store it in R0
		Operation getRetAddr = new Operation(Op.MOV,"R0","FP");
		assembly.add(getRetAddr);

		Operation getRetAddr2 = new Operation(Op.ADI,"R0",1* Constants.STACK_ELEMENT_SIZE);
		assembly.add(getRetAddr2);
		Operation getRetAddr3 = new Operation(Op.LDR,"R0","R0"); //R0 now contains return address
		getRetAddr3.comment = "Loaded return address into R0";
		assembly.add(getRetAddr3);

		//Undo the stack header
		//Move the frame pointer back
		Operation resetFP = new Operation(Op.MOV,"R1","FP");
		assembly.add(resetFP);
		Operation resetFP1 = new Operation(Op.ADI,"R1",2*Constants.STACK_ELEMENT_SIZE); //Location of PFP now in R1
		assembly.add(resetFP1);

		//Reset the stack pointer to the frame pointer+ 1 for the return val
		Operation resetSP = new Operation(Op.MOV,"SP","FP");
		assembly.add(resetSP);

		Operation resetFP2 = new Operation(Op.LDR,"R1","R1");
		assembly.add(resetFP2);
		Operation resetFP3 = new Operation(Op.MOV,"FP","R1"); //FP is now set properly
		resetFP3.comment = "FP now set to PFP";
		assembly.add(resetFP3);

		//JMP back to return call location
		Operation ret = new Operation(Op.JMR,"R0");
		ret.comment = " Return";
		assembly.add(ret);
		assembly.get(0).label = this.getLabel();

		assembly.get(0).comment = "Constructor "+getLabel()+" start";
		assembly.get(0).label = getLabel();

		return assembly;
	}

	public ClassDeclaration getClassDeclaration() {
		return parent;
	}

	public CompilationUnit getCompilationUnit() {
		return parent.getCompilationUnit();
	}

	public String getName() {
		return parent.getName()+"()";
	}

	public String getLabel() {
		return parent.getLabel()+"_"+this.nameLexeme;
	}

	public List<Parameter> getParameters() {
		return new ArrayList<Parameter>(parameters);
	}
}
