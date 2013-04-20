/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ryanvm.assembler;

/**
 *
 * @author ryan
 */
public interface Operation {
    byte[] getBytes() throws ParserException; 
    int getSourceLine();
}
