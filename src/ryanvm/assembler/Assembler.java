/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ryanvm.assembler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ryanvm.Memory;

/**
 *
 * @author ryan
 */
public class Assembler {  
    
    private final Lexer l;
    public Assembler(Lexer l){
        this.l=l;
    }
    
    public List<Operation> assemble() throws ParserException{
        Map<String,Integer> lookups = firstPass(l);
        l.reset();
        List<Operation> ops = secondPass(l,lookups);  
        return ops;
    }
    
    private Map<String,Integer> firstPass(Lexer l) throws ParserException{
        int lineNum=-1;
        int offset=0;
        int dirOffset=0;
        HashMap<String,Integer> labels = new HashMap<String,Integer>();
        //All the the directives for defining static memory will be added after the code segment
        //We are keeping them separate here
        HashMap<String,Integer[]> dir = new HashMap<String,Integer[]>();
        while(l.hasNext()){
            lineNum++;
            String line = l.next(); //One instruction (line) 
            //System.out.println("LINE: "+line);
            String[] lineParts = line.split(";"); //discard the comment
            if(lineParts.length>0){
                line=lineParts[0].trim();
            }else{
                line="";
            }
            if(line.trim().isEmpty())
                continue;
            //System.out.println(line);
            Pattern p = Pattern.compile("[\\s]+");
            Matcher m = p.matcher(line);
            m.find();
            int spaceIdx = m.start();
         // System.out.println("SPACE IDX:" +spaceIdx);
            String[] parts = new String[2];
            parts[0] = line.substring(0,spaceIdx).trim();
            parts[1] = line.substring(spaceIdx,line.length()).trim();
            if(!isInstr(parts[0]) && !isDirective(parts[0])){
          //      System.out.println("Adding label: "+parts[0]);
          //      System.out.println("VALUE: "+parts[1]);
                if(isDirective(parts[1])){
     //               System.out.println(line);
          //          System.out.println("space idx: "+spaceIdx);
          //          System.out.println("parts: " +parts[0]+"--"+parts[1]);
                  //  System.out.println("dir label "+parts[0]+":"+dirOffset);
                    Integer[] prev = dir.put(parts[0], new Integer[]{dirOffset,lineNum});
                    if(prev!=null){
                        throw new ParserException("Duplicate label '"+parts[0]+"'", lineNum);
                    }   
                    String[] directive = new String[2];
                    directive[0]=parts[1].substring(0, 5).trim();
                    directive[1]=parts[1].substring(5,parts[1].length()).trim();
                    int dOffset = getSize(directive);
                   // System.out.println("inc dirOffset by "+dOffset);
                    dirOffset+=dOffset;
                }else{
                    Integer prev = labels.put(parts[0], offset);
              //      System.out.println("jmp label");
                    if(prev!=null){
                        throw new ParserException("Duplicate label '"+parts[0]+"'", lineNum);
                    }                   
                    offset+=6;
                //    System.out.println("inc offset by 6 ("+offset+") ::" +line);
                }
            }else{
                
                if(isInstr(parts[0])){
                    offset+=6;
               //     System.out.println("inc offset by 6 ("+offset+") ::" +line);
                }else{
                    int dOffset = getSize(parts);
                    dirOffset+=dOffset;
                //    System.out.println(line);
                //    System.out.println("space idx: "+spaceIdx);
                //    System.out.println("parts: " +parts[0]+"--"+parts[1]);
               //     System.out.println("inc dirOffset by "+dOffset);
                }                
            }      
            
        }
       // System.out.println("OFFSET AT END OF INSTRUCTIONS: "+offset);
        for(Entry<String,Integer[]> dirEntry: dir.entrySet()){
            Integer prev = labels.put(dirEntry.getKey(), dirEntry.getValue()[0]+offset);
          //  System.out.println("Setting label: "+ dirEntry.getKey()+"->"+dirEntry.getValue()[0]+offset);
            if(prev!=null){
                throw new ParserException("Duplicate label "+dirEntry.getKey(), dirEntry.getValue()[0]);
            } 
        }
        return labels;
    }
    
    private List<Operation> secondPass(Lexer l, Map<String,Integer> lookupTable) throws ParserException{
        for(Entry<String,Integer> e: lookupTable.entrySet()){
            //System.out.println(e.getKey()+"=>"+(e.getValue()/6));
        }
        List<Instruction> instrs = new ArrayList<Instruction>();
        List<Directive> dirs = new ArrayList<Directive>();
        int lineNum=-1;
        while(l.hasNext()){
            
            String line = l.next(); //One instruction (line)
            lineNum++;
            try{
                if(line.trim().isEmpty())
                    continue;
                
                //System.out.println("LINE: "+line);
                String[] lineParts = line.split(";"); //discard the comment
                if(lineParts.length>0){
                    line=lineParts[0].trim();
                }else{
                    line="";
                }
                if(line.trim().isEmpty())
                    continue;            

                String[] parts = line.split("[\\s]+");
                if(!isInstr(parts[0]) && !isDirective(parts[0])){  
                   // System.out.println("discarding label "+parts[0]);
                    parts = Arrays.copyOfRange(parts, 1, parts.length);
                }
                if(isInstr(parts[0])){
                    String name = parts[0];
                    String[] args = Arrays.copyOfRange(parts, 1, parts.length);
                    Op op =Op.valueOf(name);            
                    Instruction i = new Instruction(op, args, lineNum, lookupTable); 
                    instrs.add(i);
                }else if(isDirective(parts[0])){
                    String name = parts[0];
                    String arg = parts[1];
                    if(line.contains("'")){
                        Pattern p = Pattern.compile("'[^']+'");
                        Matcher m = p.matcher(line);
                        if(m.find()){
                            arg=m.group();
                        }else{
                            System.err.println("ERROR LINE: "+lineNum+"::"+line);
                        }
                    }
                    Directive d = new Directive(name,arg, lineNum);
                    dirs.add(d);
                }
                lineNum++;
            }catch(Exception e){
                System.err.println("ERROR LINE: "+lineNum+"::"+line);
                e.printStackTrace(System.err);
                return new ArrayList<Operation>();
            }
        }
        ArrayList<Operation> ops = new ArrayList<Operation>();
        ops.addAll(instrs);
        ops.addAll(dirs);
        
        /*
        for(Entry<String,Integer> e: lookupTable.entrySet()){
            System.out.println(e.getKey()+"=>"+(e.getValue()/6));
        }
        */
        int offset=0;
        int count=0;
        /*
        for(Operation op: ops){
            System.out.print(count+":"+offset+"::"+op+"::"+Memory.hexDump(op.getBytes()));
            count++;            
            offset+=op.getBytes().length;
        }
        System.out.println("***************************************");
        */
        return ops;
    }
    
    private boolean isInstr(String input){
        
        for(Op i : Op.values()){
            if(i.name().equals(input)) return true;
        }
        //System.out.println(input+" not INSTRUCTION");
        return false;   
    }
    
    private boolean isDirective(String input){        
        return (input.startsWith(".INT") || input.startsWith(".BYT") || input.startsWith(".ALN"));
    }
    
    private int getSize(String[] operation){
        
        if(operation[0].startsWith(".INT")){
            return 4;
        }else if(operation[0].startsWith(".BYT")){
            if(operation[1].startsWith("'") && operation[1].endsWith("'")){
        //        System.out.println("DETERMINING STRING LENGTH |"+operation[1]+"|");
       //         System.out.println(operation[1].length()-2);
                return operation[1].length()-2;
            }
            else{
       //         System.out.println("Returning size 1 for "+operation[1]);
                return 1;
            }
            
        }else{
            System.err.println("SHOULDN'T BE HERE "+operation[0]);
            return Instruction.SIZE;
        }
       // return 0;
    }
}
