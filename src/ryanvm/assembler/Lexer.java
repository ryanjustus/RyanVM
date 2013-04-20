/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ryanvm.assembler;

import java.io.BufferedReader;
import java.util.Iterator;

/**
 *
 * @author ryan
 */
public class Lexer implements Iterator{

    String[] lines;
    int lineIdx;
    
    public Lexer(String input){
       lineIdx=0;
       lines = input.split("[\r\n|\n]");
    }
    
    @Override
    public boolean hasNext() {
        return (lineIdx<(lines.length));
    }

    @Override
    public String next() {
        return lines[lineIdx++];
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void reset(){
        lineIdx=0;
    }
    
}