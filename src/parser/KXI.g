grammar KXI;

options {
    language=Java;
    output=AST;

}

@lexer::header {package parser;}
@parser::header {package parser;}

@parser::members {
  @Override
  public void reportError(RecognitionException e) {
    throw new RuntimeException("I quit!\n" + e.getMessage()); 
  }
}




/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/
 //EntryPoint
compilation_unit
	:	class_declaration*
		VOID MAIN LPAREN RPAREN method_body
	; 
//Declarations
class_declaration
	:CLASS class_name LBRACKET
	class_member_declaration*
	RBRACKET
	;
class_member_declaration
	: MODIFIER type ID field_declaration
	| constructor_declaration
	;
field_declaration
	:(LSQUARE RSQUARE)? (ASSIGN assignment_expression)? 
	| LPAREN parameter_list? RPAREN method_body
	;
constructor_declaration
	:	
		class_name LPAREN parameter_list* RPAREN method_body
	;
method_body
	: LBRACKET 
		variable_declaration*
		statement*
	  RBRACKET		
	;
variable_declaration
	:	type ID (LSQUARE RSQUARE)? 
		(ASSIGN assignment_expression)? SEMICOLON
	;
parameter_list
	:	parameter (COMMA parameter)*;
	
parameter
	:	type ID (LSQUARE RSQUARE)?; 
	
//Statements
statement
	:	RBRACKET statement* LBRACKET
	|	expression SEMICOLON
	|	IF LPAREN expression RPAREN (ELSE statement)?
	|	WHILE LPAREN expression RPAREN statement
	|	RETURN expression? SEMICOLON
	|	CIN IN expression SEMICOLON
	| 	COUT OUT expression SEMICOLON
	;	

//Expressions	
expression
	:	LPAREN expression RPAREN expressionz?
	|	BOOLEAN expressionz?
	|	NULL	expressionz?
	|	INT expressionz?
	|	CHAR	expressionz?
	|	ID fn_arr_member? member_refz? expressionz?
	;

expressionz
	:	(BOOL_EXPR|LOGIC|MATH) expression
        |       ASSIGN assignment_expression;	

	
fn_arr_member
	:	LPAREN argument_list? RPAREN
	|	LSQUARE expression RSQUARE;	
	

argument_list
	:	expression (COMMA expression)*;
	
assignment_expression
	:	expression
	|	THIS
	|	NEW type new_declaration
	|	KEYFUNC LPAREN expression RPAREN;
	
	
type	:	PRIMITIVE_TYPE 
	|	VOID
	| 	class_name;
	
class_name
	:	ID;	
	
new_declaration
	:	LPAREN argument_list? RPAREN
	|	LSQUARE	expression RSQUARE;
	
member_refz
	:	DOT ID fn_arr_member? member_refz?;
	




/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/


MODIFIER:  ('public'|'private');

KEYFUNC	:  ('atoi'|'itoa');

PRIMITIVE_TYPE	:  ('bool'|'char'|'int'|'object'|'string');

VOID	: 'void';

IF	: 'if';
ELSE	: 'else';
WHILE	: 'while';
RETURN	: 'return';	
CLASS	: 'class';		
NULL	: 'null';		
THIS	: 'this';
NEW	: 'new';
MAIN	: 'main';
CIN	: 'cin';
COUT	: 'cout';
CHAR:  '\'' ( ESC_SEQ | ~('\''|'\\') ) '\''
    ;
fragment
HEX_DIGIT : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
ESC_SEQ
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UNICODE_ESC
    |   OCTAL_ESC
    ;

fragment
OCTAL_ESC
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UNICODE_ESC
    :   '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ; 
    
COMMENT
    :   '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    |   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;       
    
		
LOGIC	: ('&&'|'||');		
		
BOOLEAN : ('true'|'false');
ID  :	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
    ;

INT 
	: ('+'|'-')?('0'..'9')+;
	

WS       :           (' '|'\t'|'\f'|'\n'|'\r')+{ skip(); };
COMMA	: 	',';
SEMICOLON
	:	';';
LPAREN 	: 	'(' ;
RPAREN 	:  	')' ;
RSQUARE	:	']';
LSQUARE	:	'[';
RBRACKET:	'}';
LBRACKET:	'{';
OR 	:  	'|';
DOT 	:	'\.';
QUERY   :	'?';	
IN	: '<<';
OUT	: '>>';
BOOL_EXPR
	: ('>'|'<'|'=='|'>='|'<='|'!=');
ASSIGN	: '=';

MATH	: ('+'|'-'|'*'|'/');

