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
public class RegisterLabelOrRegisterParser implements OperandParser{
    
    private static final Pattern registerP = Pattern.compile("R([0-9]{1,2})");
    private static final Pattern labelP = Pattern.compile("[A-Za-z]+[0-9]*");
    @Override
    public byte[] parse(byte opcode, String[] operands, int lineNum, Map<String, Integer> lookupTable) throws ParserException {
        if(operands.length!=2){
            throw new ParserException("Incorrect operand count", lineNum);
        }
        byte[] vals = new byte[6];
        byte op1;        
        vals[0]=opcode;
        Matcher m1 = registerP.matcher(operands[0]);
        if(m1.matches()){            
            op1=Byte.parseByte(m1.group(1));            
        }else{
            throw new ParserException("operand 1 invalid, expected register got "+operands[0], lineNum);
        }      
        
        vals[1]=op1;

        
        m1 = registerP.matcher(operands[1]);
        if(m1.matches()){ 
       ///     System.out.println("*********op "+vals[0]+"->"+(vals[0]+1));
            vals[0]++;
            vals[2]=0;
            vals[3]=0;
            vals[4]=0;
            vals[5]=Byte.parseByte(m1.group(1));
        }else{      
           // System.out.println("'"+operands[1]+"'");
            Matcher m2 = labelP.matcher(operands[1]);
            if(m2.matches()){
                Integer val = lookupTable.get(m2.group(0));
       //         System.out.println("PARSING LABEL "+operands[1] + "->"+val);
                if(val==null){
                    throw new ParserException("operand 2 invalid, undefined label "+operands[1], lineNum);
                }
                byte[] bop2 = Memory.intToByteArray(val);
                System.arraycopy(bop2, 0, vals, 2, bop2.length);
            }
        }
        
        return vals;
    }
    
}
