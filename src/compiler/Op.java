package compiler;

import grammar.expression.IExpression;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 3/14/13 2:47 PM
 */
public enum Op implements Precedence{

	//Language ops
	INDEX("[",1),
	LPAREN("(",1),
	REF(".",1),
	CALL("(",2),
	MUL("*",3),
	DIV("/",3),
	ADD("+",4),
	SUB("-",4),

	GTE(">=",5),
	GT(">",5),
	LTE("<=",5),
	LT("<",5),
	NE("!=",6),
	EQ("==",6),

	OR("||",7),
	AND("&&",7),

	ASSIGN("=",9),


	//PseudoOps
	PUSH("PUSH",-1),
	POP("POP",-1),
	EXIT("EXIT",-1),

	//Primitive functions
	CIN("cin",-1),
	COUT("cout",-1),
	ITOA("itoa",-1),
	ATOI("atoi",-1),

	//Assembly ops
	LDI("ldi",-1),//Load immediate value
	MOV("mov", -1),//Copy value from one register to another
	ADI("adi",-1),//Add immediate value to value in register
	CMP("cmp",-1),
	LDR("ldr",-1),//Loads the the value of the address located in the register
	LDB("ldb",-1),
	JMP("jmp",-1),//Jump to address
	JMR("jmr",-1),//Jump to address stored in the register
	BRZ("brz",-1),//Branch to label if value in register is zero
	BNZ("bnz",-1),
	BLT("blt",-1),
	BGT("bgt",-1),
	STR("str",-1),//Stores the value in the second register to the address in the first register
	STB("stb",-1),
	LDA("lda",-1),
	TRP("trp",-1),

	//Directives
	INT(".INT",-1),
	BYT(".BYT",-1);



	private final int precedence;
	private final String symbol;

	private Op(String symbol, int precedence){
		this.precedence=precedence;
		this.symbol=symbol;
	}

	public boolean lessOrEqual(Precedence other){
		return precedence - other.getPrecedence() >= 0;
	}

	public int getPrecedence(){
		return precedence;
	}

	public static Op fromSymbol(String symbol){
		for(Op op: Op.values()){
			if(op.symbol.equals(symbol)){
				return op;
			}
		}
		return null;
	}
}
