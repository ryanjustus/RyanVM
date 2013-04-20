/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ryanvm.assembler;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ryanvm.Memory;

/**
 *
 * @author ryan
 */
public class Directive implements Operation{
 

    private Pattern intP = Pattern.compile("0x([a-fA-F0-9]{2})|(-?[\\d]{1,3})");
    private byte[] data;
    private int line;
    public Directive(String dir, String data, int line) throws ParserException{
        this.line=line;
        if(dir.equals(".BYT")){
            //ascii encoded data
            if(data.startsWith("'") && data.endsWith("'") && data.length()>0){
                data=data.substring(1, data.length()-1);
                try{
                    this.data = data.getBytes("ASCII");
                }catch(UnsupportedEncodingException e){
                    throw new ParserException(e.getMessage(), line);
                }
            }
            //numeric data
            else{
                Matcher m = intP.matcher(data);
                if(m.matches()){
                    this.data = new byte[1];
                    //Hex format
                    if(data.startsWith("0x")){                        
                        Integer val = Integer.parseInt(m.group(1),16);
                        this.data[0] = Memory.intToByteArray(val)[3];
                    }
                    //Decimal format
                    else{
                        Integer val = Integer.parseInt(m.group(2));
                        this.data[0] = Memory.intToByteArray(val)[3];
                    }
                }else{
                    throw new ParserException("Illegal Argument `"+ data+"`", line);
                }
            }
        }else if(dir.equals(".INT")){
            this.data= Memory.intToByteArray(Integer.parseInt(data));
        }
    }
    
    @Override
    public byte[] getBytes() throws ParserException {
        return data;
    }    
    
    @Override
    public int getSourceLine(){
        return line;
    }
    @Override
    public String toString(){
        return Memory.hexDump(data);
    }
}