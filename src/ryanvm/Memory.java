/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ryanvm;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author ryan
 */
public class Memory {
    
    private final Byte[] memory;
    private static final int INSTR_SIZE=6;
    private static final int DEFAULT_MEM_SIZE=1024;
    private static final int INT_SIZE = Integer.SIZE/8;;
    
    public Memory(){
        memory = new Byte[DEFAULT_MEM_SIZE];
        init();
    }
    
    public Memory(int size){
        memory = new Byte[size];
        init();
    }
    
    private void init(){
        for(int i=0;i<memory.length;i++){
            memory[i]=(byte)0x00;
        }
    }
    
    Byte getByte(int address){
        return memory[address];
    }
    
    public void setByte(int address, Byte value){
        memory[address] = value;
    }
    
    public void setInt(int address, Integer value){
      byte[] bytes = intToByteArray(value);
      for(int i=0;i<bytes.length;i++){
          setByte(address+i,bytes[i]);
      }
    }
    
    public Integer getInt(int address){
        
        byte[] intVal = new byte[INT_SIZE];
        for(int i=0;i<INT_SIZE;i++){
            intVal[i]=getByte(address+i);
            //System.out.println("RETRIEVED BYTE AT "+(address+i)+"->"+getByte(address+i));
        }
        return byteArrayToInt(intVal);
    }
    
    public byte[] getInstr(int address){
        byte[] instr = new byte[INSTR_SIZE];
        for(int i=0;i<INSTR_SIZE;i++){
            instr[i]=getByte(address+i);
        }
        return instr;
    }
    
    public void setInstr(int address, Byte[] instr){
        for(int i=0;i<INSTR_SIZE;i++){
            setByte(address+i, instr[i]);
        }
    }
    
    public String hexDump(){
        return Memory.hexDump(memory);
    }
    
    public String hexDump(int offset, int length){
        return Memory.hexDump(Arrays.copyOfRange(memory, offset, offset+length));
    }
    
    public String charDump(int offset, int length){       
        return Memory.charDump(Arrays.copyOfRange(memory, offset, offset+length));
    }
    
    public String charDump(){
        return Memory.hexDump(memory);
    }
    
    public static byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value
              };
    }

    public static int byteArrayToInt(byte [] b) {
       /* System.out.print("CONVERTING BYTE[] TO INT: [");
        for(int i=0;i<4;i++)
            System.out.print(b[i]+",");
        System.out.println("]->"+((b[0] << 24)
                + ((b[1] & 0xFF) << 16)
                + ((b[2] & 0xFF) << 8)
                + (b[3] & 0xFF)));
        *
        */
        return (b[0] << 24)
                + ((b[1] & 0xFF) << 16)
                + ((b[2] & 0xFF) << 8)
                + (b[3] & 0xFF);
    }
    
    public static String hexDump(Byte[] BB){
        byte[] bb = new byte[BB.length];
        for(int i=0;i<BB.length;i++){           
           bb[i]=BB[i].byteValue();
        }
        return hexDump(bb);
    }
    
    public static String hexDump(byte[] b){
          StringBuilder result = new StringBuilder();
          for (int i=0; i < b.length; i++) {            
            result.append(Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 ))
                  .append(" ");
            if(i%6==5){
                result=result.append("\n");
            }
          }
          if(b.length<6){
              result.append("\n");
          }
          return result.toString();
    }
    
    public static String charDump(Byte[] BB){
        byte[] bb = new byte[BB.length];
        for(int i=0;i<BB.length;i++){           
           bb[i]=BB[i].byteValue();
        }
        return charDump(bb);
    }
        
    public static String charDump(byte[] b){
        StringBuilder result = new StringBuilder();
        for (int i=0; i < b.length; i++) {            
            result.append((char)b[i]);
            if(i%6==5){
                result=result.append("\n");
            }
        }
        if(b.length<6){
              result.append("\n");
        }
        return result.toString();
    }
}