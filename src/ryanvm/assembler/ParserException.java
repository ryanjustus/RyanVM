/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ryanvm.assembler;

/**
 *
 * @author ryan
 */
public class ParserException extends Exception {
    final int line;
    final String message;
    public ParserException(String message, int line) {
        super(message);
        this.line=line;
        this.message=message;
    }
    
    @Override
    public String getMessage(){
        return "LINE "+line + "::"+super.getMessage();
    }
}
