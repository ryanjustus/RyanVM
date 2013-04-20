/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ryanvm.assembler;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ryan
 */
class RegisterRegisterParser implements OperandParser{
    private static final Pattern registerP = Pattern.compile("R([0-9]{1,2})");
    
    @Override
    public byte[] parse(byte opcode, String[] operands, int lineNum, Map<String,Integer> labels) throws ParserException {
        if(operands.length!=2){
            throw new ParserException("Incorrect operand count", lineNum);
        }
        byte op1;
        Matcher m1 = registerP.matcher(operands[0]);
        if(m1.matches()){
            op1=Byte.parseByte(m1.group(1));
          //  System.out.println("arg 1 parsed: "+m1.group(1));
        }else{
            throw new ParserException("operand 1 invalid, expected register got "+operands[0], lineNum);
        }

        byte op2;
        Matcher m2 = registerP.matcher(operands[1]);
        if(m2.matches()){
            op2=Byte.parseByte(m2.group(1));
        }else{
            throw new ParserException("operand 2 invalid, expected register got  "+operands[1], lineNum);
        }
        byte[] vals = new byte[6];
        vals[0]=opcode;
        vals[1]=op1;
        vals[2]=0x0;
        vals[3]=0x0;
        vals[4]=0x0;
        vals[5]=op2;        
        return vals;
    }  
    
}
