package grammar.expression;


import parser.KXICompiler;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 3/14/13 4:09 PM
 */
public class TestShuntYard {

	public static void main(String[] args){
		/*
		{
			//Test assignment
			String s1 = "a = x + 1 / (a + b);";
			IExpression e1 = KXICompiler.compileExpression(s1);
			System.out.println("COMPLETED: " +e1.toString());
			Stack s = new Stack();
			Queue q = new LinkedList();
			e1.shunt(s,q);
			while(!s.isEmpty()){
				Object o = s.pop();
				System.out.println("Putting "+o+" onto queue");
				q.add(o);
			}
			int i = 0;
			while(!q.isEmpty()){
				System.out.println(i++ +":: "+q.poll());
			}
		}
		*/

		/*

		{
			//Test primitive function
			String s1 = "c = itoa(1+a/b);";
			IExpression e1 = KXICompiler.compileExpression(s1);
			System.out.println("COMPLETED: " +e1.toString());
			Stack s = new Stack();
			Queue q = new LinkedList();
			e1.shunt(s,q);
			while(!s.isEmpty()){
				Object o = s.pop();
				System.out.println("Putting "+o+" onto queue");
				q.add(o);
			}
			int i = 0;
			while(!q.isEmpty()){
				System.out.println(i++ +":: "+q.poll());
			}
		}

		*/
		/*
		{
			//Test function
			String s1 = "e = f(a ,c,d);";
			IExpression e1 = KXICompiler.compileExpression(s1);
			System.out.println("COMPLETED: " +e1.toString());
			Stack s = new Stack();
			LinkedList q = new LinkedList();
			e1.shunt(s,q);
			while(!s.isEmpty()){
				Object o = s.pop();
				System.out.println("Putting "+o+" onto queue");
				q.add(o);
			}


			int i = 0;
			while(!q.isEmpty()){
				System.out.println(i++ +":: "+q.poll());
			}
		}
		*/

		{
			//Test new declaration
			String s1 = "a.f();";
			IExpression e1 = KXICompiler.compileExpression(s1);
			System.out.println("COMPLETED: " +e1.toString());
			Stack s = new Stack();
			LinkedList q = new LinkedList();
			e1.shunt(s,q);
			while(!s.isEmpty()){
				Object o = s.pop();
				System.out.println("Putting "+o+" onto queue");
				q.add(o);
			}


			int i = 0;
			while(!q.isEmpty()){
				System.out.println(i++ +":: "+q.poll());
			}
		}

	}
}
