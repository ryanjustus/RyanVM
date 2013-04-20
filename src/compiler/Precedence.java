package compiler;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Time: 4/4/13 5:33 PM
 */
public interface Precedence {
	public boolean lessOrEqual(Precedence other);
	public int getPrecedence();
}
