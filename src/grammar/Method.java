package grammar;

import com.sun.corba.se.impl.protocol.giopmsgheaders.ReplyMessage_1_0;
import compiler.Constants;
import compiler.Op;
import compiler.Operation;
import grammar.error.SemanticException;
import org.antlr.v4.runtime.ParserRuleContext;
import parser.KXIParser;

import javax.management.openmbean.OpenMBeanOperationInfo;
import java.sql.Savepoint;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 1/29/13 6:01 PM
 */
public class Method implements Scope{
	final boolean isPublic;
	final Type t;
	public final String lexeme;
	final List<Parameter> parameters = new ArrayList<Parameter>();
	public final MethodBody body;
	final ClassDeclaration parent;
	final ParserRuleContext context;

	public Method(ClassDeclaration parent, KXIParser.Class_member_declarationContext context)  {
		this.context=context;
		this.parent=parent;
		isPublic = context.MODIFIER().getText().equals("public");
		t = new Type(false,context.type());
		lexeme = context.ID().getText();

		if(context.field_declaration().parameter_list()!=null){
			KXIParser.Parameter_listContext plc = context.field_declaration().parameter_list();
			for(KXIParser.ParameterContext p : plc.parameter()){
				Parameter param = new Parameter(p);
				parameters.add(param);
			}
		}
		body = new MethodBody(this, context.field_declaration().method_body());
	}

	//Constructor for main method
	public Method(ClassDeclaration parent, KXIParser.Method_bodyContext context){
		this.context=context;
		isPublic=false;
		t = new Type(false, "void", true);
		lexeme = "main";
		this.parent = parent;
		body = new MethodBody(this,context);
	}

	/**
	 * Generates code for the function call/execution
	 * The return address is located in R0 on entry
	 *
	 * The parameters for the function are located on the stack at SP+i for the i'th argument after the stack frame is built
	 * When the function exits the parameters are no longer on the stack
	 *
	 * The value from the function is always returned in R0, the caller then will add this to the stack
	 *
	 * @return
	 */
	public List<Operation> generateCode(){
		List<Operation> assembly = new ArrayList<Operation>();

		//Generate main method code
		if(getLabel().equals("Main_main")){

			//Set FP
			Operation setFP = new Operation(Op.MOV,"FP","SP");
			assembly.add(setFP);
			Operation setHeap = new Operation(Op.ADI,"SP",3*Constants.STACK_ELEMENT_SIZE);
			setHeap.comment += "add 'this' offset to simplify local vars for main()";
			assembly.add(setHeap);
			assembly.addAll(body.generateCode());
			assembly.get(0).label = body.getLabel();

			//Add a face operation at end with label "END"
			Operation end = new Operation(Op.TRP,"0");
			end.label=this.getLabel()+"_END";
			assembly.add(end);
			return assembly;
		}

		//First save return address from R28
		//First save return address from R28
		Operation saveRet1 = new Operation(Op.MOV,"R0","FP");
		assembly.add(saveRet1);
		Operation addOffset = new Operation(Op.ADI,"R0",1*Constants.STACK_ELEMENT_SIZE);
		assembly.add(addOffset);
		Operation saveRet2 = new Operation(Op.STR,"R0","R28");
		saveRet2.comment = " save return address into FP+1";
		assembly.add(saveRet2);


		/**
		 * Do method body
		 */
		assembly.addAll(body.generateCode());

		//Pop value off stack as return value
		Operation popRet = new Operation(Op.POP,"R0");
		popRet.label = this.getLabel()+"_END";
		popRet.comment = "save return value into FP +0";
		assembly.add(popRet);

		Operation saveRet = new Operation(Op.STR,"FP","R0");
		assembly.add(saveRet);

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
		ret.comment = " Return "+getLabel();
		assembly.add(ret);
		assembly.get(0).label = this.getLabel();

		assembly.get(0).comment = "Method "+getLabel()+" start";
		assembly.get(0).label = getLabel();

		return assembly;
	}

	public String getLabel(){
		return (parent==null ? "null" : parent.getLabel()+"_"+this.lexeme);
	}

	public ClassDeclaration getParent() {
		return parent;
	}


	public boolean exists(String var) {
		return existsLocal(var) || parent.exists(var);
	}

	public boolean isPublic(String var) {
		return isPublic;
	}

	private boolean existsLocal(String var){
		for(Parameter p: parameters){
			if(p.idLexeme.equals(var)){
				return true;
			}
		}
		return false;
	}

	public Type getReturnType(){
		return t;
	}

	public Type getType(String var) {
		if(existsLocal(var)){
			for(Parameter p: parameters){
				if(p.idLexeme.equals(var)){
					return p.getType();
				}
			}
		}
		else if(parent.exists(var)){
			return parent.getType(var);
		}
		return null;
	}

	public List<SemanticException> validateSemantics(List<Type> types) {

		Set<String> ids = CompilationUnit.getReserved();

		List<SemanticException> errors = new ArrayList<SemanticException>();
		for(Parameter p: parameters){

			//Check if we have already declared a variable
			if(ids.contains(p.idLexeme)){
				throw new SemanticException(p.context, "Identifier already used: "+p.idLexeme);
			}else{
				ids.add(p.idLexeme);
			}
			//Check types exist
			Type lhs = p.getType();
			if(!types.contains(lhs)){
				errors.add(new SemanticException(p.context,"Undefined type: "+lhs.lexeme));
			}
		}
		errors.addAll(body.validateSemantics(types));
		return errors;
	}

	public ClassDeclaration getClassDeclaration() {
		return parent;
	}

	public CompilationUnit getCompilationUnit() {
		return parent.getCompilationUnit();
	}

	public List<Parameter> getParameters() {
		return new ArrayList<Parameter>(parameters);
	}

	public String getName(){
		return lexeme;
	}
}
