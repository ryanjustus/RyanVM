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
class RegisterLabelParser implements OperandParser{
    private static final Pattern registerP = Pattern.compile("R([0-9]{1,2})");
    private static final Pattern labelP = Pattern.compile("[A-Za-z]+[0-9]*");


    public byte[] parse(byte opcode, String[] operands, int lineNum, Map<String, Integer> lookupTable) throws ParserException {
        if(operands.length!=2){
            throw new ParserException("Incorrect operand count", lineNum);
        }
        byte op1;
        Matcher m1 = registerP.matcher(operands[0].trim());        
        if(m1.matches()){
            op1=Byte.parseByte(m1.group(1));
        }else{
            throw new ParserException("operand 1 invalid, expected register got `"+operands[0]+"`", lineNum);
        }
        
        int op2;
        Matcher m2 = labelP.matcher(operands[1]);
        if(m2.matches()){
            Integer val = lookupTable.get(m2.group(0));
            
            if(val==null){
                throw new ParserException("operand 2 invalid, undefined label "+operands[1], lineNum);
            }
        //    System.out.println("LABEL "+m2.group(0) + "->"+val);
            op2=val;
        }else{
            throw new ParserException("operand 2 invalid, expected label got "+operands[1], lineNum);
        }
        byte[] vals = new byte[6];
        byte[] bop2 =Memory.intToByteArray(op2);
        vals[0]=opcode;
        vals[1]=op1;        
        System.arraycopy(bop2, 0, vals, 2, bop2.length);
        return vals;
    }
    
}