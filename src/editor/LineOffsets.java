/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author ryan
 */
public class LineOffsets {
	private final List<LineOffset> lines;

	public LineOffsets(String program){
		lines= new ArrayList<LineOffset>();
		String nl = "\n";
		int offset =0;
		int num=0;

		for(String line: program.split("\n")){
			num++;
			int start = offset;
			addOffset(new LineOffset(num,start, line));
			offset+=(line.replaceAll("\\t", " ").length() + 1);
		}
		//System.out.println(num + " lines in program");
	}


	final void addOffset(LineOffset line){
		lines.add(line);
	}

	public int getStartOffset(int line, int column){
		if(line>0)
			line--;
		while(line>=lines.size()){
			line--;
		}
		LineOffset offset = lines.get(line);
		int lineOffset = offset.start;

		//System.out.println("line "+line+": "+offset.content.replaceAll("\\t","^"));
		//System.out.println("length: "+offset.content.length());
		//System.out.println("column: "+column);
		//Count the number of tabs
		int spacesPerTab=7;
		for(int i=0;
		    i<column;
		    i++
				){

			if(i < offset.content.length() && offset.content.charAt(i)=='\t'){
				//column-=spacesPerTab;
			}
		}
		//System.out.println("adjustedColumn: "+column);
		return (lineOffset + column - 1);
	}


	public int getLineNumber(int offset){
		for(LineOffset line:lines){
			if(line.start>=offset){
				return line.line;
			}
		}
		return -1;
	}

	public int getLineCount(){
		return lines.size();
	}

	static class LineOffset{
		private final int line, start;
		private final String content;
		public LineOffset(int line, int start, String content){
			this.line=line;
			this.start=start;
			this.content=content;
		}
		public int getLine(){
			return line;
		}
		public int getStart(){
			return start;
		}
		public String getContent(){
			return content;
		}
	}
}
