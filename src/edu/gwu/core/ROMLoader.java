package edu.gwu.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import edu.gwu.core.basic.Word;
import edu.gwu.exception.IllegalMemoryAddressException;



public class ROMLoader extends Observable{
	public static final int BOOT_FIRST_LINE = 10;
	
	
	
	private static ROMLoader _instance;
	public static ROMLoader getInstance(){
		if(_instance==null)
			_instance = new ROMLoader();
		return _instance;
	}
	
	private List<String> bootCodes;
	
	private List<Word> bootBinaries;
	

	public ROMLoader(){
		bootCodes = new ArrayList<String>();
		bootBinaries = new ArrayList<Word>();
		
		this.resetDefaultBootProgram();
	}
	
	
	
	private void compileBootProgram(){
		bootBinaries.clear();
		for(String line:bootCodes){
			bootBinaries.add(new Word(ProgramCompiler.decodeInstruction(line)));
		}
	}
	
	public List<String> getBootCodes(){
		return this.bootCodes;
	}
	
	/**
	 * default boot program
	 */
	public void resetDefaultBootProgram(){
		
		bootCodes.clear();
		bootCodes.add("LDR 0,0,8");
		bootCodes.add("AIR 0,55");
		bootCodes.add("STR 0,0,9");
		bootCodes.add("LDX 1,9");
		bootCodes.add("IN 1,31");
		bootCodes.add("STR 1,1,0");
		bootCodes.add("STX 1,9");
		bootCodes.add("LDR 0,0,9");
		bootCodes.add("AIR 0,1");
		bootCodes.add("STR 0,0,9");
		bootCodes.add("LDX 1,9");
		bootCodes.add("JNE 1,0,14");
		bootCodes.add("JMP 0,55");		
		
	}
	
	
	/**
	 * loat boot program into memory
	 * <tr> start at BOOT_FIRST_LINE
	 * @throws IllegalMemoryAddressException
	 */
	public void loadBootIntoMemory() throws IllegalMemoryAddressException{
		
		this.compileBootProgram();
		
		MainMemory memory = MainMemory.getInstance();
		
		int start = CPU.CPU_BOOT_START_LINE;
		for(Word word:bootBinaries){
			memory.write(start, word);
			start++;
		}
	}
	
	
	
	private List<String> program1;
	public List<String> getProgram1(){
		if(program1==null)
			initProgram1();
		return program1;
	}
	private void initProgram1() {
		program1 = new ArrayList<String>();
		program1.add("LDR 0,0,8");
		program1.add("AIR 0,127");
		program1.add("AIR 0,127");
		program1.add("STR 0,0,40");
		program1.add("LDX 1,40");
		program1.add("LDR 3,0,8");
		program1.add("AIR 3,5");
		program1.add("STR 3,0,41");
		program1.add("LDR 2,0,8");
		program1.add("IN 0,0");
		program1.add("OUT 0,1");
		program1.add("LDR 1,0,8");
		program1.add("AIR 1,10");
		program1.add("TRR 0,1");
		program1.add("JCC 3,0,77");
		program1.add("SIR 0,48");
		program1.add("STR 0,0,50");
		program1.add("LDR 0,0,8");
		program1.add("AIR 0,10");
		program1.add("MLT 2,0");
		program1.add("AMR 2,0,50");
		program1.add("JMP 0,64");
		program1.add("STR 2,1,0");
		program1.add("STX 1,50");
		program1.add("LDR 0,0,50");
		program1.add("AIR 0,1");
		program1.add("STR 0,0,50");
		program1.add("LDX 1,50");
		program1.add("SOB 3,0,63");
		program1.add("LDR 0,0,40");
		program1.add("STR 0,0,50");
		program1.add("AMR 0,0,41");
		program1.add("STR 0,0,51");
		program1.add("LDR 3,0,41");
		program1.add("SIR 3,1");
		program1.add("STR 2,0,52");
		program1.add("LDR 0,0,50,1");
		program1.add("SMR 0,0,52");
		program1.add("STR 0,0,51,1");
		program1.add("LDR 0,0,8");
		program1.add("AIR 0,1");
		program1.add("AMR 0,0,50");
		program1.add("STR 0,0,50");
		program1.add("LDR 0,0,8");
		program1.add("AIR 0,1");
		program1.add("AMR 0,0,51");
		program1.add("STR 0,0,51");
		program1.add("SOB 3,0,91");
		program1.add("STX 1,50");
		program1.add("LDR 1,0,50");
		program1.add("LDR 0,0,50,1");
		program1.add("STR 0,0,42");
		program1.add("LDR 3,0,41");
		program1.add("SIR 3,2");
		program1.add("LDR 2,0,8");
		program1.add("AIR 2,1");
		program1.add("AMR 2,0,50");
		program1.add("STR 2,0,50");
		program1.add("LDR 0,0,42");
		program1.add("SMR 0,0,50,1");
		program1.add("JCC 1,0,119");
		program1.add("LDR 1,0,50");
		program1.add("LDR 0,0,50,1");
		program1.add("STR 0,0,42");
		program1.add("SOB 3,0,109");
		program1.add("SMR 1,0,41");
		program1.add("STR 1,0,50");
		program1.add("LDR 2,0,50,1");
		program1.add("LDR 0,0,8");
		program1.add("AIR 0,78");
		program1.add("OUT 0,1");
		program1.add("AIR 0,23");
		program1.add("OUT 0,1");
		program1.add("SIR 0,4");
		program1.add("OUT 0,1");
		program1.add("AIR 0,17");
		program1.add("OUT 0,1");
		program1.add("SIR 0,13");
		program1.add("OUT 0,1");
		program1.add("AIR 0,14");
		program1.add("OUT 0,1");
		program1.add("AIR 0,1");
		program1.add("OUT 0,1");
		program1.add("SIR 0,58");
		program1.add("OUT 0,1");
		program1.add("LDR 0,0,8");
		program1.add("AIR 0,100");
		program1.add("STR 0,0,50");
		program1.add("LDX 2,50");
		program1.add("LDR 0,0,40");
		program1.add("AMR 0,0,41");
		program1.add("AMR 0,0,41");
		program1.add("STR 0,0,43");
		program1.add("STR 0,0,44");
		program1.add("LDR 1,0,8");
		program1.add("AIR 1,10");
		program1.add("DVD 2,1");
		program1.add("STR 3,0,43,1");
		program1.add("LDR 0,0,8");
		program1.add("AIR 0,1");
		program1.add("AMR 0,0,43");
		program1.add("STR 0,0,43");
		program1.add("JNE 2,2,51");
		program1.add("SMR 0,0,44");
		program1.add("LDR 2,0,43");
		program1.add("SIR 2,1");
		program1.add("STR 2,0,43");
		program1.add("LDR 2,0,43,1");
		program1.add("AIR 2,48");
		program1.add("OUT 2,1");
		program1.add("SOB 0,2,59");
	}
	
	
	private List<String> program2;
	public List<String> getProgram2(){
		if(program2==null)
			initProgram2();
		return program2;
	}
	private void initProgram2() {
		program2 = new ArrayList<String>();
		program2.add("LDR 0,0,8");
		program2.add("AIR 0,127");
		program2.add("AIR 0,127");
		program2.add("STR 0,0,40");
		program2.add("LDX 1,40");
		program2.add("IN 0,3");
		program2.add("JZ 0,0,70");
		program2.add("OUT 0,1");
		program2.add("STR 0,1,0");
		program2.add("STX 1,50");
		program2.add("LDR 1,0,50");
		program2.add("AIR 1,1");
		program2.add("STR 1,0,50");
		program2.add("LDX 1,50");
		program2.add("JMP 0,60");
		program2.add("STX 1,41");
		program2.add("LDR 0,0,8");
		program2.add("AIR 0,10");
		program2.add("OUT 0,1");
		program2.add("LDR 3,0,8");
		program2.add("AIR 3,10");
		program2.add("IN 0,0");
		program2.add("OUT 0,1");
		program2.add("STR 0,1,0");
		program2.add("TRR 0,3");
		program2.add("JCC 3,0,87");
		program2.add("STX 1,50");
		program2.add("LDR 1,0,50");
		program2.add("AIR 1,1");
		program2.add("STR 1,0,50");
		program2.add("LDX 1,50");
		program2.add("JMP 0,76");
		program2.add("STX 1,42");
		program2.add("SMR 1,0,41");
		program2.add("STR 1,0,43");
		program2.add("LDR 3,0,8");
		program2.add("LDR 0,0,8");
		program2.add("AIR 0,100");
		program2.add("STR 0,0,50");
		program2.add("LDX 3,50");
		program2.add("LDX 1,40");
		program2.add("LDX 2,41");
		program2.add("LDR 1,0,41");
		program2.add("AIR 3,0");
		program2.add("LDR 0,0,50");
		program2.add("TRR 0,1");
		program2.add("JCC 3,3,78");
		program2.add("LDR 0,1,0");
		program2.add("LDR 1,2,0");
		program2.add("STR 0,0,50");
		program2.add("SIR 0,32");
		program2.add("JZ 0,3,28");
		program2.add("SIR 0,12");
		program2.add("JZ 0,3,28");
		program2.add("SIR 0,2");
		program2.add("JZ 0,3,28");
		program2.add("LDR 0,0,50");
		program2.add("TRR 0,1");
		program2.add("JCC 3,3,15");
		program2.add("JMP 3,55");
		program2.add("STX 1,50");
		program2.add("LDR 0,0,8");
		program2.add("AIR 0,1");
		program2.add("AMR 0,0,50");
		program2.add("STR 0,0,50");
		program2.add("LDX 1,50");
		program2.add("STX 2,50");
		program2.add("LDR 0,0,8");
		program2.add("AIR 0,1");
		program2.add("AMR 0,0,50");
		program2.add("STR 0,0,50");
		program2.add("LDX 2,50");
		program2.add("JMP 0,102");
		program2.add("LDR 0,0,50");
		program2.add("SIR 0,10");
		program2.add("JNE 0,3,33");
		program2.add("AIR 2,1");
		program2.add("JMP 3,34");
		program2.add("AIR 3,1");
		program2.add("STX 2,50");
		program2.add("LDR 0,0,50");
		program2.add("LDR 1,0,42");
		program2.add("TRR 0,1");
		program2.add("JCC 3,3,40");
		program2.add("JMP 3,55");
		program2.add("STR 2,0,50");
		program2.add("AIR 2,49");
		program2.add("OUT 2,1");
		program2.add("LDR 2,0,8");
		program2.add("AIR 2,46");
		program2.add("OUT 2,1");
		program2.add("LDR 2,0,50");
		program2.add("STR 3,0,50");
		program2.add("AIR 3,48");
		program2.add("OUT 3,1");
		program2.add("LDR 3,0,8");
		program2.add("AIR 3,10");
		program2.add("OUT 3,1");
		program2.add("LDR 3,0,50");
		program2.add("JMP 3,55");
		program2.add("LDX 2,41");
		program2.add("STX 1,50");
		program2.add("LDR 1,0,8");
		program2.add("AIR 1,1");
		program2.add("AMR 1,0,50");
		program2.add("STR 1,0,50");
		program2.add("LDX 1,50");
		program2.add("LDR 0,1,0");
		program2.add("STR 0,0,50");
		program2.add("STR 1,0,51");
		program2.add("STX 1,52");
		program2.add("LDR 0,0,52");
		program2.add("LDR 1,0,41");
		program2.add("SIR 1,1");
		program2.add("TRR 0,1");
		program2.add("JCC 3,3,87");
		program2.add("LDR 0,0,50");
		program2.add("LDR 1,0,51");
		program2.add("SIR 0,10");
		program2.add("JNE 0,3,78");
		program2.add("LDR 3,0,8");
		program2.add("AIR 2,1");
		program2.add("JMP 0,96");
		program2.add("SIR 0,22");
		program2.add("JZ 0,3,85");
		program2.add("SIR 0,12");
		program2.add("JZ 0,3,85");
		program2.add("SIR 0,2");
		program2.add("JZ 0,3,85");
		program2.add("JMP 0,96");
		program2.add("AIR 3,1");
		program2.add("JMP 3,56");
	}

	
	private List<String> programPart4;
	public List<String> getProgramPart4(){
		if(programPart4==null)
			initProgramPart4();
		return programPart4;
	}
	private void initProgramPart4() {
		programPart4 = new ArrayList<String>();
		programPart4.add("LDR 0,0,8");
		programPart4.add("AIR 0,100");
		programPart4.add("AIR 0,100");
		programPart4.add("AIR 0,100");
		programPart4.add("STR 0,0,50");
		programPart4.add("LDX 1,50");
		programPart4.add("IN 1,5");
		programPart4.add("JZ 1,0,71");
		programPart4.add("STR 1,1,0");
		programPart4.add("STX 1,51");
		programPart4.add("LDR 0,0,8");
		programPart4.add("AIR 0,1");
		programPart4.add("AMR 0,0,51");
		programPart4.add("STR 0,0,51");
		programPart4.add("LDX 1,51");
		programPart4.add("JMP 0,61");
		programPart4.add("LDX 1,50");
		programPart4.add("LDFR 0,1,0");
		programPart4.add("FADD 0,1,1");
		programPart4.add("STFR 0,1,100");
		programPart4.add("LDX 1,50");
		programPart4.add("LDFR 0,1,0");
		programPart4.add("FSUB 0,1,1");
		programPart4.add("STFR 0,1,101");
		programPart4.add("LDR 0,0,8");
		programPart4.add("AIR 0,1");
		programPart4.add("CNVRT 0,0,50");
		programPart4.add("STFR 0,1,102");
		programPart4.add("LDR 0,0,50");
		programPart4.add("STR 0,1,50");
		programPart4.add("AIR 0,6");
		programPart4.add("STR 0,1,51");
		programPart4.add("AIR 0,6");
		programPart4.add("STR 0,1,52");
		programPart4.add("LDR 1,0,8");
		programPart4.add("AIR 1,6");
		programPart4.add("STR 1,0,51");
		programPart4.add("LDR 2,0,8");
		programPart4.add("AIR 2,1");
		programPart4.add("CNVRT 2,0,51");
		programPart4.add("STFR 0,0,51");
		programPart4.add("VADD 0,1,50");
		programPart4.add("VSUB 0,1,51");
	}
	
}
