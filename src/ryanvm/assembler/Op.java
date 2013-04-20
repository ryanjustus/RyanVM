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
public enum Op {
    
    //Directives
    
    //Instructions
    //Register Register Arithmethic
    ADD((byte)0x01,new RegisterRegisterParser()),
    SUB((byte)0x02,new RegisterRegisterParser()),
    MUL((byte)0x03,new RegisterRegisterParser()),
    DIV((byte)0x04,new RegisterRegisterParser()),
    ADI((byte)0x05,new OperandParser(){
            private final Pattern registerP = Pattern.compile("R([0-9]{1,2})");
            private final Pattern intP = Pattern.compile("0x([a-fA-F0-9]+)|(-?[\\d]+)");     
            
            @Override
            public byte[] parse(byte opcode, String[] operands, int lineNum, Map<String,Integer> labels) throws ParserException {
                if(operands.length!=2){
                    throw new ParserException("Incorrect operand count", lineNum);
                }
                byte val1;
                Matcher m1 = registerP.matcher(operands[0]);
                if(m1.matches()){
                    val1=Byte.parseByte(m1.group(1));
                }else{
                    throw new ParserException("operand 1 invalid, expected register got "+operands[0], lineNum);
                }

                int val2;
                Matcher m2 = intP.matcher(operands[1]);
                if(m2.matches()){                    
                    //hex format
                    if(operands[1].startsWith("0x")){                        
                        val2 = Integer.parseInt(m2.group(1),16);
                    }
                    //Decimal format
                    else{
                        val2 = Integer.parseInt(m2.group(2));
                    }                    
                }else{
                    throw new ParserException("operand 2 invalid, expected number got "+operands[0], lineNum);
                }
                            //pad it with zero
                byte[] vals = new byte[6];            
                byte[] bop2 =Memory.intToByteArray(val2);
                vals[0]=opcode;
                //pad it with zero
                vals[1]=val1;            
                System.arraycopy(bop2, 0, vals, 2, bop2.length);            
                return vals;
            }
    }),   
    //Logic
    AND((byte)0x06,new RegisterRegisterParser()),
    OR((byte)0x07,new RegisterRegisterParser()),
    
    //Compare
    CMP((byte)0x08,new RegisterRegisterParser()),
    
    //Jump instructions
    JMP((byte)0x09,new LabelParser()),    
    //Jump instructions
    JMR((byte)0x0a,new OperandParser() {
        private final Pattern registerP = Pattern.compile("R([0-9]{1,2})");
        @Override
        public byte[] parse(byte opcode, String[] operands, int lineNum, Map<String, Integer> lookupTable) throws ParserException {
            if(operands.length!=1){
                throw new ParserException("Incorrect operand count, expected 1", lineNum);
            }
            Byte val;
            Matcher m1 = registerP.matcher(operands[0]);
            if(m1.matches()){
                val = Byte.parseByte(m1.group(1));
                if(val==null){
                    throw new ParserException("operand 1 invalid, expected register got "+operands[0], lineNum);
                }
            }else{
                throw new ParserException("operand 1 invalid, expected register got "+operands[0], lineNum);
            }    
            
            byte[] vals = new byte[6];            
            vals[0]=opcode;
            //pad it with zero
            vals[1]=val;  
            vals[2]=0;
            vals[3]=0;
            vals[4]=0;
            vals[5]=0;       
            return vals;
        }
    }),
    
    BNZ((byte)0x0b,new RegisterLabelParser()),
    BGT((byte)0x0c,new RegisterLabelParser()),
    BLT((byte)0x0d,new RegisterLabelParser()),
    BRZ((byte)0x0e,new RegisterLabelParser()),
    
    //Move instructions    
    MOV((byte)0x0f,new RegisterRegisterParser()),
    
    LDA((byte)0x11,new RegisterLabelParser()),
    JSR((byte)0x12, new RegisterLabelParser()),
    //Two variants, label, register
    //Each of these I left a space between 
    STR((byte)0x13,new RegisterLabelOrRegisterParser()),
    LDR((byte)0x15,new RegisterLabelOrRegisterParser()),
    STB((byte)0x17,new RegisterLabelOrRegisterParser()),
    LDB((byte)0x19,new RegisterLabelOrRegisterParser()),
    
    
        //Jump instructions
    TRP((byte)0x21,new OperandParser() {
        private Pattern intP = Pattern.compile("0x([a-fA-F0-9]{2})|([\\d]{1,3})");
        @Override
        public byte[] parse(byte opcode, String[] operands, int lineNum, Map<String, Integer> lookupTable) throws ParserException {
            if(operands.length!=1){
                throw new ParserException("Incorrect operand count", lineNum);
            }
     
            Matcher m1 = intP.matcher(operands[0]);
            int op;
            if(m1.matches()){
                //Hex value
                if(operands[0].startsWith("0x")){                        
                    op = Integer.parseInt(m1.group(1),16);
                }
                //Decimal format
                else{
                    op = Integer.parseInt(m1.group(2));
                    
                }                
            }else{
                throw new ParserException("operand 2 invalid, expected IMM got "+operands[0], lineNum);
            }  
            //pad it with zero
            byte[] vals = new byte[6];
            byte[] bop2 =Memory.intToByteArray(op);
            vals[0]=opcode;
            vals[1]=0x0;

            System.arraycopy(bop2, 0, vals, 2, bop2.length);
            return vals;
        }
    }),
    
    //Multithread instructions
    RUN((byte)0x22, new RegisterLabelParser()),
    END((byte)0x23, new NoOperandParser()),
    BLK((byte)0x24, new NoOperandParser()),
    LCK((byte)0x25, new LabelParser()),
    ULK((byte)0x26, new LabelParser());
    
    public final byte opcode;
    private final OperandParser p;
    
    Op(byte opcode, OperandParser p){
        this.opcode=opcode;
        this.p=p;    
    }
    
    public static Op fromByte(byte b){
        for(Op o: Op.values()){
            if(o.opcode==b){
                return o;
            }
        }
        throw new IllegalArgumentException("No such opcode: "+b);
    }
    
    OperandParser getParser(){
        return p;
    }    
}