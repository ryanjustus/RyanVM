/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ryanvm;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextAreaDefaultInputMap;
import ryanvm.assembler.Assembler;
import ryanvm.assembler.Instruction;
import ryanvm.assembler.Lexer;
import ryanvm.assembler.Op;
import ryanvm.assembler.Operation;
import ryanvm.assembler.ParserException;

/**
 *
 * @author ryan
 */
public class RyanVM {

    final Memory m;
    final int MEM_SIZE=65536;
    final int INT_SIZE=4;
    final int CONTEXT_MAX =4;
    
    int pc=0; 
    private boolean isRunning;
    
    final int[] regs = new int[32];
    
    //I use a dedicated register for I/O
    private final int IOREG=15;

	private int heapStart = 0;
    
    //This is just to help me remember the registers for SP/FP
    private final int SP=31;
    private final int FP=30;
	private final int HP=29;
    
    
    int currentThreadIdx;
    
    //Thread state size sets aside an int for each register and an int for the pc
    int threadStateSize = regs.length*INT_SIZE+INT_SIZE;
    int [] threadIds = {MEM_SIZE - threadStateSize,MEM_SIZE -(1000+threadStateSize),MEM_SIZE - (2000+threadStateSize),MEM_SIZE - (3000+threadStateSize)};
    //keeps track of the instructions we have run in the current context
    //if this gets to CONTEXT_LEN we reset it and switch contexts
    int contextCnt=0;    
    
    //thread states for whether or not we are running the threads
    boolean[] threadState = {false,false,false,false};
    //addr -> threadIdx
    Map<Integer,Integer> locks;  
    
    private List<Operation> ops;
    
    public RyanVM(){
        m=new Memory(MEM_SIZE);
        locks = new HashMap<Integer,Integer>();        
        //initialize all the registers to zero
        for(int i=0;i<regs.length;i++){
            regs[i]=0;
        }
    }

    
    public int load(List<Operation> ops) throws ParserException{
        int address=0;
        this.ops=ops;
        for(Operation op: ops){
            byte[] bb = op.getBytes();
            for(byte b: bb){
                m.setByte(address, b);
                address++;
            }
        }
	    regs[HP] = address; //SET HP as heap start
	    heapStart = address;
	    return address;
    }
    
    private byte[] fetchNext(){
        return m.getInstr(pc*Instruction.SIZE);
    }
    
    private int getFreeThreadIdx(){
        for(int i=1;i<this.threadState.length;i++){
            if(!threadState[i]){
                return i;
            }
        }
        throw new IllegalStateException("No free threads");
    }
    
    private void saveThreadState(int threadIdx){
        //Save the pc
        int offset = threadIds[threadIdx];
        m.setInt(offset, pc);
        //Save registers
        for(int i=0;i<regs.length;i++){
            offset+=INT_SIZE;
            m.setInt(offset, regs[i]);
        }        
    }
    
    private void restoreThreadState(int threadIdx){
        //restore pc
        int offset = threadIds[threadIdx];;
        pc=m.getInt(offset);
        //restore register
        for(int i=0;i<regs.length;i++){
            offset+=INT_SIZE;
            regs[i] = m.getInt(offset);
        }        
    }
    
    private boolean otherThreadsRunning(){
        for(int i=0;i<threadState.length;i++){
            //if another thread is running return true;
            if(i!=currentThreadIdx
               && threadState[i]){
                return true;
            }
        }
        return false;
    }
    
    
    private void triggerContextSwitch(){
        contextCnt=CONTEXT_MAX;
    }
    
    /**
     * Checks if the another thread has a lock
     * @param threadIdx
     * @param addr
     * @return 
     */
    private boolean isBlocked(int threadIdx, int addr){
        for(Integer lock: locks.keySet()){
            if(lock==addr && locks.get(lock)!=threadIdx)
                return true;
        }
        return false;        
    }
    
    private void lock(int threadIdx, int addr){
        locks.put(addr, threadIdx);        
    }
    
    private void unlock(int threadIdx, int addr){
        if(locks.containsKey(addr) && locks.get(addr)==threadIdx){
            locks.remove(addr);
        }        
    }
    
    public String dumpMemory(){
        return m.hexDump();
    }
    
    public String dumpMemory(int offset, int length){
        return m.hexDump(offset,length);
    }
    
    public String dumpMemoryAsChars(){
        return m.charDump();
    }
    
    public String dumpMemoryAsChars(int offset, int length){
        return m.charDump(offset,length);
    }
    
    
    
    public void runProgram(InputStream in, PrintStream out) throws IOException{

       threadState[0]=true;
       isRunning=true;
       
       currentThreadIdx=0;
       
       //When we start the program set reg[0] to the current thread id
       //This gives our program a way to know where its stack is located
       regs[31]=threadIds[0];

	    /*
	    for(int i=0;i<regs[HP];i+=6){
		    System.out.print("LINE: "+i/6+" OFFSET "+i+" :: ");
		    if(i/6<ops.size()){
			    System.out.print(ops.get(i/6)+" :: ");
		    }
		    System.out.print(this.dumpMemory(i, 6));
	    }
	    System.out.print("**************");
        */

	   //printStatic();
       BufferedReader r = new BufferedReader(new InputStreamReader(in));
       while(isRunning){
	       /*
           Thread.yield();
           //perform round-robin context switches for multi-thread support
           if(contextCnt>=CONTEXT_MAX){ 
               //System.out.println("Context switching");
               contextCnt=0;
               int tmpThreadIdx = currentThreadIdx+1;
               //search for another running thread
               while(!this.threadState[tmpThreadIdx % threadIds.length]){
                   tmpThreadIdx++;                   
               }          
               //If we aren't on the same thread do a context switch
               tmpThreadIdx = tmpThreadIdx%threadIds.length;
               if(tmpThreadIdx!=currentThreadIdx){
                   //System.out.println("CTX Switch "+currentThreadIdx+"->"+tmpThreadIdx);
                   saveThreadState(currentThreadIdx);
                   restoreThreadState(tmpThreadIdx);
                   currentThreadIdx=tmpThreadIdx;
               }               
           }else{
               contextCnt++;
           }
           */
           
           //Fetch the instruction
           byte[] instr = fetchNext(); 
           
           //Decode the op
           byte op = instr[0];
          
           //Fetch the register id's
           byte bop1 = instr[1];
           byte bop2 = instr[5];
           
           //Fetch the integer
           int  iop = Memory.byteArrayToInt(Arrays.copyOfRange(instr, 2, 6)); 
	       //System.out.println("****************");
	       //System.out.println("PC: "+pc+"** "+ops.get(pc)+" INSTR:"+Arrays.toString(instr)+"::"+ iop);
	       if(regs[SP] <= regs[HP]){
		       //printHeap();
		       //printStack();
		       System.err.println("Stack overflow in RyanVM");
		       return;
	       }
    
           //Run the operation
           switch(op){
                case 0x1: //ADD 
                   // System.out.println("add regs["+bop1+"]="+regs[bop1]+"+"+regs[bop2]+" ("+(regs[bop1]+regs[bop2])+")");
                    regs[bop1]=regs[bop1]+regs[bop2];
                    pc++;
                break;

                case 0x2: //SUB 
 //                   System.out.print("sub regs["+bop1+"]="+regs[bop1]+"-"+regs[bop2]+" ("+(regs[bop1]-regs[bop2])+")");
                    regs[bop1]=regs[bop1]-regs[bop2];
                 //   System.out.println(" ("+regs[bop1]+")");
                    pc++;
                break;

                case 0x3: //MUL 
           //         System.out.println("mul ");
                    regs[bop1]*=regs[bop2];
                    pc++;
                break;

                case 0x4: //DIV 
          //          System.out.println("div ");
                    regs[bop1]/=regs[bop2];
                    pc++;
                break;

                case 0x5: // ADI
      //              System.out.println("ADI regs["+bop1+"]<-"+iop);
                    regs[bop1]+=iop;
                    pc++;
                break;
                    
                case 0x6: //AND 
                    regs[bop1]&=regs[bop2];
                    pc++;
                break;
                    
                case 0x7:  //OR 
                    regs[bop1]|=regs[bop2];
                    pc++;
                break;
                    
                case 0x8:  //CMP 
          //          System.out.println("cmp ");
                    if(regs[bop1]>regs[bop2]){
                        regs[bop1]=1;
                    }else if(regs[bop1]==regs[bop2]){
                        regs[bop1]=0;
                    }else if(regs[bop1]<regs[bop2]){
                        regs[bop1]=-1;
                    }
                    pc++;
                break;
                  
                case 0x9:{  //JMP
           //         System.out.println("!!!JMP "+iop+"->"+(iop));
	                regs[28]=(pc+1)*Instruction.SIZE;;
                    pc=(iop);
                }
                break;
                    
                case 0x0a:{ //JMR
                   // System.out.println("JMR to "+bop2+":"+regs[bop1]/6);
                  //  r.readLine();
	                int tmp = (pc+1)*Instruction.SIZE;
                    pc=(regs[bop1]/Instruction.SIZE);
	                regs[bop1]=tmp*Instruction.SIZE; //Save the PC into the specified register
                }
                break;
                    
                case 0x0b: //BNZ 
                    if(regs[bop1]!=0){
                        pc=iop/Instruction.SIZE;
                    }else{
                        pc++;
                    }
                break;
                    
                case 0x0c: //BGT 
         //           System.out.println("bgt ");
         //           System.out.println(regs[bop1]);
                    if(regs[bop1]>0){                        
                        pc=iop/Instruction.SIZE;
        //                System.out.println("BRANCHING TO "+pc);
                    }else{
                        pc++;
                    }
                break;
                    
                case 0x0d: //BLT 
       //             System.out.println("blt ");
                    if(regs[bop1]<0){
                        pc=iop/Instruction.SIZE;
                    }else{
                        pc++;
                    }                    
                break;
                    
                case 0x0e: //BRZ 
      //               System.out.println("brz ");
       //             System.out.println(regs[bop1]);
                    if(regs[bop1]==0){
      //                  System.out.println("BRANCHING TO "+(iop/6));
                        pc=iop/Instruction.SIZE;
                    }else{
        //                System.out.println("NOT BRANCHING, INCREMENTING PC");
                        pc++;
                    } 
               //     input.nextLine();
                break;
                    
                case 0x0f: //MOV 
       //             System.out.println("mov ");
                    regs[bop1]=regs[bop2];
                    pc++;
                break;
                    
                case 0x11: //LDA 
     //              System.out.println("lda ");
                    regs[bop1]=iop;
                    pc++;
                break;
                    
                case 0x12: //JSR 
                    pc++;
              //      System.out.println("***************\njsr ");
                    regs[bop1]=pc*Instruction.SIZE; //Save the PC into the specified register
                 //   System.out.println("SAVING INSTR "+pc/6 +" TO R"+bop1);
                 //   System.out.println(ops.get(pc));
                    pc=iop/Instruction.SIZE;      //Jump to the label  
                //    System.out.println("JUMPING TO INSTR: "+pc);
                //    System.out.println(ops.get(pc));
                break;
                    
                case 0x13: 
      //              System.out.println("str ");
                    m.setInt(iop, regs[bop1]);
                    pc++;
                break;
                
                case 0x14: 
               //     System.out.println("str2 ");
               //     System.out.println("MEM("+regs[bop1]+") <- "+regs[bop2]);
                    m.setInt(regs[bop1], regs[bop2]);
                    pc++;
                break;
                        
                case 0x15: 
                   //System.out.println("ldr ");
                   //System.out.println("LOADING INT at "+iop+":"+m.getInt(iop));
                    //System.out.println(this.dumpMemory(iop-5, iop));
                    //System.out.println();
                    //System.out.println(dumpMemory(iop,iop+10));
	                //System.out.println("retrieved value: "+m.getInt(iop));
                    regs[bop1]=m.getInt(iop);
                    pc++;
                break;
                    
                case 0x16: 
 //                     System.out.println("ldr2 ");
 //                    System.out.println("bop1: "+bop1);
 //                     System.out.println("iop: "+iop);
 //                     System.out.println("reg: "+regs[iop]);
//                      System.out.println("val: "+m.getInt(regs[iop]));
                   // System.out.println("HEX DUMP\n"+m.hexDump(0,175));
                    regs[bop1]=m.getInt(regs[iop]);
                    pc++;
                break;
                   
                case 0x17: 
                  //  System.out.println("stb ");                    
                    Integer val = new Integer(regs[bop1]);
                  //  System.out.println("MEM["+iop+"] <- " +val.byteValue());
                    m.setByte(iop,val.byteValue());
                    pc++;
                break;
                    
                case 0x18: 
 //                   System.out.println("stb2 ");
                    Integer val2 = regs[bop2];
                //    System.out.println(val2);
 //                   System.out.println("MEM["+regs[bop1]+"] <- " +(char)val2.byteValue()+"("+val2.byteValue()+")");
                    m.setByte(regs[bop1],(val2.byteValue()));
                    pc++;
                break;    
                    
                case 0x19: 
  //                  System.out.println("ldb ");
  //                  System.out.println(iop+"->"+m.getByte(iop));
                    regs[bop1]=m.getByte(iop);
                    pc++;
                break;
                    
                case 0x1a: 
                  //  System.out.print("ldb2 ");
                  //  System.out.println((char)(m.getByte(regs[iop])).byteValue());
                    regs[bop1]=m.getByte(regs[iop]);
                    pc++;
                break;
                    
                case 0x21: //21 for the trap
                    int trap = iop;
                    if(trap==0){
                      //  System.out.println("exiting");
                        isRunning=false;
                        
                    }else if(trap==1){
                        //write int in R15 to out
                        //System.out.println("PRINTING INT: `"+regs[IOREG]+"`:"+regs[IOREG]);
                        out.print(regs[IOREG]);
   //                     r.readLine();
                    }else if(trap==2){
                        //When reading from the keyboard if there is no input do a context switch
	                    //System.out.println("HERE!!!!!");
                        //if(r.ready()){
                            regs[IOREG] = Integer.parseInt(r.readLine());
                        /*}else{
                            //we increment the pc at the end of the trap so
                            //we can decrement it here to wait
                            pc--;
                            triggerContextSwitch();                                                        
                        }
                        */
                    }else if(trap==3){
             //          System.out.println("PRINTING CHAR: `"+(char)regs[IOREG]+"`:"+regs[IOREG]);
                        out.print((char)regs[IOREG]);
  //                      r.readLine();
                    }else if(trap==4){
                   //     System.out.println("GET INPUT: ");
                        //regs[IOREG]=input.nextByte(); 
                        //if(r.ready()){
                            regs[IOREG] = r.read();
	                    /*
                        }else{
                            //we increment the pc at the end of the trap so
                            //we can decrement it here to wait
                            pc--;
                            triggerContextSwitch();
                        }
                        */
                      ///  System.out.println("RECEIVED: " +(char)regs[IOREG]+"("+regs[IOREG]+")");
                        
                    }else if(trap==10){
                        int num = regs[IOREG];
                        if(num<'0' || num>'9'){
                            regs[IOREG]=-1; 
                        }else{
                            int iNum = num-'0';
                            regs[IOREG]=iNum;                            
                        }                       
                    }else if(trap==11){
                        int num = regs[IOREG];
                        if(num>=0 && num<=9)
                            regs[IOREG]=num+48;
                        else
                            regs[IOREG]=-1;                        
                    }else if(trap==12){
                        //Print the stack
                        System.out.println("_______________");                        
                        for(int i=regs[31];i<threadIds[currentThreadIdx]-1;i+=4){
                            System.out.println(i+"::"+m.getInt(i));
                        }
                        System.out.println("--------------");
                        
                    }else if(trap==13){
                       printRegs();
                    }
                    pc++;
                break;
                 /*   
                RUN((byte)0x22, new RegisterLabelParser()),
                */
                case 0x22:
                    //retreive thread index, this will throw an exception
                    //if there were no free threads
                    int threadIdx=getFreeThreadIdx();
                    
                    //increment the pc for the current thread
                    pc++;
                    
                    //save the current thread context
                    saveThreadState(currentThreadIdx);
                    
                    //set the thread state to running
                    threadState[threadIdx]=true;
                    
                    //set the currentThreadId to the thread we retrieved
                    currentThreadIdx=threadIdx;
                    
                    //set the pc for the new thread
                    pc=iop/Instruction.SIZE;
                    
                    //reset the context counter
                    contextCnt=0;
                    
                    //return threadId in register
                    regs[bop1]=threadIds[threadIdx];                   
                    
                break;
                    
                 /*
                END((byte)0x23, new NoOperandParser()),
                */
                case 0x23:
                    //we start at 1 because this has no effect on the main thread
                    if(currentThreadIdx!=0){
                        threadState[currentThreadIdx]=false;
                        //triggerContextSwitch();
                    }else{       
                        //we are the main thread.  this instruction does nothing
                        pc++; 
                    }
                    
                break;
                /*
                BLK((byte)0x24, new NoOperandParser()),
                */
                case 0x24:
                    //check we are in the main thread and child threads are running
                    if(currentThreadIdx==0 && otherThreadsRunning()){
                        //trigger a context switch and do nothing else
                        //triggerContextSwitch();
                    }else{    
                        //otherwise increment the pc;
                    //    System.out.println("block complete");
                        pc++;
                    }                    
                break;
                /*
                LCK((byte)0x25, new LabelParser()),
                */
                case 0x25:
                    //iop is address to lock
                    if(isBlocked(currentThreadIdx, iop)){
                        //trigger a context switch and do nothing else
                        //triggerContextSwitch();
                    }else{
                        //we aren't blocked so obtain the lock and increment pc
                        lock(currentThreadIdx, iop);
                        pc++;
                    }                    
                break;
                /*
                ULK((byte)0x26, new LabelParser());
                 */
                case 0x26:
                    unlock(currentThreadIdx,iop); 
                    pc++;
                break;
                
                default: 
                    System.out.append("\n**** PC: "+pc+", "+ops.get(pc).toString()+"\n");
                    throw new IllegalStateException("Thread: "+currentThreadIdx+"Unsupported operation "+op);
                
           }

	       //@TODO enable debug here
	      //printRegs();
	      //printHeap();
	      //printStack();
	      //System.out.println("");

       }
    }

	public void printRegs(){
		//Print the regs
		String regLine="";
		String lblLine="";
		for(int i=0;i<regs.length;i++){
			lblLine+="R"+i+"\t";
			regLine+=regs[i]+"\t";
		}
		System.out.println(lblLine);
		System.out.println(regLine);
	}

	public void printStack(){
		System.out.println("STACK");
		int top = regs[31];
		int bottom = threadIds[0];
		for(int i = top;i<=bottom;i+=4){
			if(i==regs[30]){
				System.out.print("*");
			}else{
				System.out.print(" ");
			}
			System.out.print(i+"-> ("+this.m.getInt(i)+")\t\t"+this.m.hexDump(i,4));
		}

	}

	public void printHeap(){
		System.out.println("HEAP");
		for(int i = heapStart;i<=regs[29];i+=4){
			System.out.print(i+"-> ("+this.m.getInt(i)+")\t "+this.m.hexDump(i,4));
		}
		System.out.println();
	}

	public void printStatic(){
		System.out.println("STATIC");
		for(int i = 0;i<heapStart;i+=4){
			System.out.print(i+"-> ("+this.m.getInt(i)+")\t "+this.m.hexDump(i,4));
		}
		System.out.println();
	}

	public static void runProgram(String assembly, InputStream in, PrintStream out) throws ParserException, IOException {

		//System.out.println(sb.toString());
		Lexer l = new Lexer(assembly);
		Assembler a = new Assembler(l);
		List<ryanvm.assembler.Operation> ops = a.assemble();
		//    for(Operation op: ops){
		//        System.out.println(op);
		//     }


		RyanVM vm = new RyanVM();
		vm.load(ops);
		//      System.out.println(vm.dumpMemory(0, 255));
		//      System.out.println("*********************");
		vm.runProgram(in, out);
	}


    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, ParserException, IOException {
          File f = new File(args[0]);
          StringBuilder sb = new StringBuilder();
          Scanner s = new Scanner(f);
          while(s.hasNextLine()){
                sb.append(s.nextLine()).append("\n");
          }
          //System.out.println(sb.toString());
          Lexer l = new Lexer(sb.toString());
          Assembler a = new Assembler(l);
          List<Operation> ops = a.assemble();
      //    for(Operation op: ops){
      //        System.out.println(op);
     //     }

          RyanVM vm = new RyanVM();
          vm.load(ops);
    //      System.out.println(vm.dumpMemory(0, 255));
    //      System.out.println("*********************");
          vm.runProgram(System.in, System.out);
    }
}
