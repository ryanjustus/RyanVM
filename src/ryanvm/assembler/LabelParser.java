/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ryanvm.assembler;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ryanvm.Memory;

/**
 *
 * @author ryan
 */
public class LabelParser implements OperandParser{

     private final Pattern labelP = Pattern.compile("[A-Za-z_]+[0-9]*");
        @Override
        public byte[] parse(byte opcode, String[] operands, int lineNum, Map<String, Integer> lookupTable) throws ParserException {
            if(operands.length!=1){
                throw new ParserException("Incorrect operand count", lineNum);
            }
            Integer val;
           // System.out.println("JMP OP0:"+operands[0]);
            Matcher m1 = labelP.matcher(operands[0]);
            if(m1.matches()){
                val = lookupTable.get(m1.group(0));
                if(val==null){
                    throw new ParserException("operand 1 invalid, undefined label "+operands[0], lineNum);
                }else{
                    val=val/6;
                }
            }else{
                throw new ParserException("operand 1 invalid, expected label got "+operands[0], lineNum);
            }  
            
            //pad it with zero
            byte[] vals = new byte[6];            
            byte[] bop2 =Memory.intToByteArray(val);
            vals[0]=opcode;
            //pad it with zero
            vals[1]=0x0;            
            System.arraycopy(bop2, 0, vals, 2, bop2.length);            
            return vals;
        }
    
}
