package grammar.expression;

import compiler.Constants;
import compiler.Op;
import compiler.Operation;
import grammar.ClassDeclaration;
import grammar.MethodBody;
import grammar.Scope;
import grammar.Type;
import grammar.error.SemanticException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import parser.KXIParser;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @TODO identifier semantic check
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/24/13 3:50 PM
 */
public class Identifier extends ExpressionSkeleton{

	public final String lexeme;

	public final Scope caller;
	public boolean lhs = false;

	public Identifier(ParserRuleContext context,Scope caller, Scope parent, TerminalNode id){
		super(context,parent);
		if(caller==null){
			this.caller=parent;
		}else{
			this.caller=caller;
		}
		lexeme = id.getText();
	}


	public Type getType() {
		if(caller==null){
			return null;
		}
		return caller.getType(lexeme);
	}


	public List<SemanticException> validateSemantics(List<Type> types) {

		List<SemanticException> errors = new ArrayList<SemanticException>();
		if(!caller.exists(lexeme)){
			errors.add(new SemanticException(context,"Undefined identifier "+lexeme));
		}else{

		}

		return errors;
	}

	public void shunt(Stack ops, Queue args) {
		args.add(this);
	}

	public List<Operation> buildCode(){

		List<Operation> assembly = new ArrayList<Operation>();

		if(parent instanceof MethodBody){
			assembly.addAll(referenceMethodVariable());
		}
		else if(parent instanceof ClassDeclaration){
			assembly.addAll(referenceClassMember());
		}else{
			throw new IllegalStateException("Expected parent to be ClassDeclaration or MethodBody: "+parent);
		}
		assembly.get(0).comment+= " PUSH "+toString();
		return assembly;
	}

	private List<Operation> referenceClassMember(){
		List<Operation> assembly = new ArrayList<Operation>();
		//In this case we will be initializing before anything else in the constructor happens
		//We should have a 'this' on the stack in the appropriate spot

		//Not a local variable, reference 'this' on the stack, pushes onto top of stack
		This t = new This(context,parent);
		assembly.addAll(t.buildCode());

		//Now we need to check what the offset in the class heap is
		ClassDeclaration cd = this.getClassDeclaration();
		int offset= cd.getOffset(this);
		Operation popThis = new Operation(Op.POP,"R0");
		popThis.comment="referencing this."+lexeme;
		assembly.add(popThis);
		Operation ref4 = new Operation(Op.ADI,"R0", offset*Constants.HEAP_ELEMENT_SIZE );
		ref4.comment = "add offset in heap record";
		assembly.add(ref4);

		if(!lhs){
			Operation ref5  = new Operation(Op.LDR,"R0","R0");
			assembly.add(ref5);
		}

		Operation push = new Operation(Op.PUSH,"R0");
		assembly.add(push);
		push.comment = lexeme+" now on top of stack";
		return assembly;
	}

	private List<Operation> referenceMethodVariable(){
		List<Operation> assembly = new ArrayList<Operation>();
		int offset = ((MethodBody)parent).getOffset(this);

		if(offset<0){
			assembly.addAll(referenceClassMember());
		}else{
			//Push the address onto the stack
			Operation ref = new Operation(Op.MOV,"R0","FP");
			ref.comment +=" Reference "+lexeme+" from FP+"+offset;
			assembly.add(ref);
			Operation ref2 = new Operation(Op.ADI,"R0",offset*Constants.STACK_ELEMENT_SIZE);
			assembly.add(ref2);

			if(!lhs){
				Operation deref = new Operation(Op.LDR,"R0","R0");
				assembly.add(deref);
			}

			Operation ref3 = new Operation(Op.PUSH,"R0");
			assembly.add(ref3);
		}
		return assembly;
	}

	public String toString(){
		return lexeme;
	}

	public void setLHS(boolean lhs) {
		this.lhs = lhs;
	}

}
