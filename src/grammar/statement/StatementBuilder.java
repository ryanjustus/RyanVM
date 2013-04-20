package grammar.statement;

import grammar.MethodBody;
import parser.KXIParser;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 2/24/13 3:15 PM
 */
public class StatementBuilder {
	public static Statement build(MethodBody parent, KXIParser.StatementContext context){
		Statement stmt;
		if(context.LBRACKET()!=null){
			stmt = new BlockStatement(parent,context);
		}
		else if(context.IF()!=null){
			stmt = new IfStatement(parent,context);
		}else if(context.WHILE()!=null){
			stmt = new WhileStatement(parent,context);
		}else if(context.RETURN()!=null){
			stmt = new ReturnStatement(parent,context);
		}else if(context.COUT()!=null){
			stmt = new COUTStatement(parent,context);
		}else if(context.CIN()!=null){
			stmt = new CINStatement(parent,context);
		}else{
			stmt = new ExprStatement(parent,context);
		}
		return stmt;
	}
}
