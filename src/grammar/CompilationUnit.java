package grammar;

import compiler.Constants;
import compiler.Op;
import compiler.Operation;
import grammar.error.SemanticException;
import grammar.error.SyntaxException;
import grammar.expression.FunctionCall;
import org.antlr.v4.runtime.ParserRuleContext;
import parser.KXIParser;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 1/29/13 4:45 PM
 */
public class CompilationUnit implements Scope{

	List<ClassDeclaration> classes;
	public Method main;
	public final List<SyntaxException> parserExceptions;
	public final List<SemanticException> semanticExceptions;


	public final Map<Constant,String> constants;

	public int labelCount = 0;

	public final ParserRuleContext context;
	public CompilationUnit(List<SyntaxException> parserExceptions, KXIParser.Compilation_unitContext context){
		this.context=context;

		this.parserExceptions=parserExceptions;
		this.semanticExceptions = new ArrayList<SemanticException>();

		constants = new HashMap<Constant, String>();
		//Initialize constants with some values


		constants.put(new Constant(0, Constant.Type.INT),"ZERO");
		constants.put(new Constant(1, Constant.Type.INT),"ONE");

		classes = new ArrayList<ClassDeclaration>();

		if(!parserExceptions.isEmpty()){
			System.err.println("Syntax Parsing Failed");
			return;
		}

		for(KXIParser.Class_declarationContext c: context.class_declaration()){
			ClassDeclaration clazz = new ClassDeclaration(this,c);
			classes.add(clazz);
		}

		ClassDeclaration mainClass = new ClassDeclaration(this);
		main = new Method(mainClass,context.method_body());
	}

	public String getNextLabel(){
		return "L"+labelCount++;
	}

	public Scope getParent() {
		return null;
	}

	public boolean exists(String var) {
		return false;
	}

	public boolean isPublic(String var) {
		return false;
	}

	public Type getType(String var) {
		return null;
	}

	public ClassDeclaration getClass(Type t){

		if(t==null){
			return null;
		}
		for(ClassDeclaration c: classes){
			if(c.idLexeme.equals(t.lexeme)){
				return c;
			}
		}

		return null;
	}


	public boolean typeDefined(Type t){
		for(Type.Primitive primitive: Type.Primitive.values()){
			if(primitive.t.lexeme.equals(t.lexeme)){
				return true;
			}
		}

		for(ClassDeclaration cd : classes){
			if(cd.getType().lexeme.equals(t.lexeme)){
				return true;
			}
		}
		return false;
	}


	public List<SemanticException> validateSemantics(){
		return validateSemantics(this.getTypes());
	}

	public List<SemanticException> validateSemantics(List<Type> types) {

		Set<String> ids = getReserved();
		ids.add("main");
		//Check for re-usages of ids
		for(ClassDeclaration c: classes){
			if(ids.contains(c.idLexeme)){
				semanticExceptions.add(new SemanticException(c.context, "Identifier already used: " + c.idLexeme));
			}else{
				ids.add(c.idLexeme);
			}
		}
		//Check semantics of all the children
		for(ClassDeclaration c: classes){
			semanticExceptions.addAll(c.validateSemantics(types));
		}
		semanticExceptions.addAll(main.validateSemantics(types));
		return semanticExceptions;
	}

	public ClassDeclaration getClassDeclaration() {
		return main.getClassDeclaration();
	}

	public CompilationUnit getCompilationUnit() {
		return this;
	}

	public String getName() {
		return "CompilationUnit";
	}

	public String getLabel() {
		return "__CU__";
	}

	public List<Type> getTypes(){
		List<Type> types = new ArrayList<Type>();
		for(ClassDeclaration c: classes){
			types.add(new Type(false,c.idLexeme,false));
		}
		types.addAll(getPrimitives());
		return types;
	}

	public List<Type> getPrimitives(){
		List<Type> primitives = new ArrayList<Type>();
		primitives.add(new Type(false,"int",false));
		primitives.add(new Type(false,"char",false));
		primitives.add(new Type(false,"bool",false));
		primitives.add(new Type(false,"void",false));
		return primitives;
	}


	public List<Operation> generateCode(){

		List<Operation> ops = new ArrayList<Operation>();

		//Add in main method
		ops.addAll(main.generateCode());

		//Add in all the class code
		for(ClassDeclaration cd: classes){
			ops.addAll(cd.generateCode());
		}

		//Add in all the directives
		List<Operation> assembly = new ArrayList<Operation>();
		for(Map.Entry<Constant,String> e: this.constants.entrySet()){
			assembly.add(e.getKey().getOperation(e.getValue()));
		}

		assembly.addAll(ops);

		return assembly;
	}
	public static Set<String> getReserved(){
		Set<String> reserved = new HashSet<String>();
		reserved.add("atoi");
		reserved.add("bool");
		reserved.add("class");
		reserved.add("char");
		reserved.add("cin");
		reserved.add("cout");
		reserved.add("else");
		reserved.add("false");
		reserved.add("if");
		reserved.add("if");
		reserved.add("int");
		reserved.add("itoa");
		reserved.add("new");
		reserved.add("null");
		reserved.add("object");
		reserved.add("public");
		reserved.add("private");
		reserved.add("return");
		reserved.add("string");
		reserved.add("this");
		reserved.add("true");
		reserved.add("void");
		reserved.add("while");
		return reserved;
	}

	public static List<Operation> optimize(List<Operation> input){
		List<Operation> optimized = new ArrayList<Operation>();
		for(int i=0;i<input.size()-1;i++){
			Operation op1 = input.get(i);
			Operation op2 = input.get(i+1);


			if(op1.op == Op.PUSH  &&
			   op2.op == Op.POP  &&
			   op1.arg1.equals(op2.arg1) &&
			   op1.label==null &&
			   op2.label == null)
			{
				//skip
				//System.out.println("Skipping ops: "+op1.toString()+", "+op2.toString());
				//Remove push followed by pop
				i++;
			}else{

				if(op1.op == Op.PUSH  &&
						op2.op == Op.POP  &&
						op1.label==null &&
						op2.label==null)
				{
					//Replace push/pop with mov
					Operation move = new Operation(Op.MOV,op2.arg1,op1.arg1);
				    optimized.add(move);
					//System.out.println("Replacing push/pop with move: "+op1.toString()+", "+op2.toString()+"->"+move.toString());
					i++;
				}else{
					optimized.add(op1);
				}

			}


		}
		optimized.add(input.get(input.size()-1));
		return optimized;
	}

	public static List<Operation> convertToTarget(List<Operation> input){

		List<Operation> target = new ArrayList<Operation>();
		//Convert POP,PUSH
		for(Operation o: input){
			if(o.op==Op.PUSH){
				Operation o1 = new Operation(Op.ADI,"SP",Constants.STACK_ELEMENT_SIZE);
				target.add(o1);
				o1.comment = o.comment;
				o1.label = o.label;
				Operation o2 = new Operation(Op.STR,"SP",o.arg1);
				target.add(o2);

			}else if(o.op==Op.POP){
				Operation o1 = new Operation(Op.LDR,o.arg1,"SP");
				target.add(o1);
				o1.comment = o.comment;
				o1.label = o.label;
				Operation o2 = new Operation(Op.ADI,"SP",-1*Constants.STACK_ELEMENT_SIZE);
				target.add(o2);
			}else{
				target.add(o);
			}
		}

		for(int i=0;i<target.size();i++){
			target.set(i,target.get(i).convertRegisterNames());
		}

		return target;
	}
}
