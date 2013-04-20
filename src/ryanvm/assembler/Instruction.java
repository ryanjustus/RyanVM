/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ryanvm.assembler;

import java.util.Map;
import ryanvm.Memory;

/**
 *
 * @author ryan
 */
public class Instruction implements Operation{
    
    private final Op op;
    private String arg1;
    private String arg2;
    private final byte[] instruction;
    private int lineNum;
    public static final int SIZE=6;
    
    public Instruction(Op op, String[] operands, int lineNum,Map<String,Integer> lookup) throws ParserException{
        this.op=op;
        this.lineNum=lineNum;
        instruction = op.getParser().parse(op.opcode,operands, lineNum, lookup);
        arg1=operands[0];
        //If there is only 1 argument put it in the second position and zero out the first
        if(operands.length>1)
            arg2=operands[1];
        else{
            arg2=operands[0];
            arg1="0";
        }
    }
    
    
    Byte getOpCode(){
        return op.opcode;
    }
    
    
    @Override
    public byte[] getBytes() throws ParserException{        
        return instruction;
    }
    
    @Override
    public int getSourceLine(){
        return lineNum;
    }
    
    @Override
    public String toString(){
        return op.name()+" "+((arg1==null)?"":arg1)+((arg2==null)?"":" "+arg2);
    }
}