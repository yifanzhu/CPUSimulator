package edu.gwu.core.basic;

import java.util.HashMap;
import java.util.Map;

public class InstructionSetInfo {

	/**
	 * OPCODE(6),R(2),IX(2),I(1),ADD(7)
	 */
	public static final int BINARY_STYLE_R_IX_I_ADD = 1;
	/**
	 * OPCODE(6),Rx(2),Ry(2),(8)
	 */
	public static final int BINARY_STYLE_RX_RY = 2;
	/**
	 * OPCODE(6),(2),R(2),A/L(1),L/R(1),(1),Count(5)
	 */
	public static final int BINARY_STYLE_R_AL_LR_COUNT = 3;
	/**
	 * OPCODE(6),(2),R(2),(3),DevId(5)
	 */
	public static final int BINARY_STYLE_R_DEVID = 4;
	/**
	 * OPCODE(6),(2),FR(2),(3),DevId(5) used for floating and vector opetations (PART4)
	 */
	public static final int BINARY_STYLE_FR_IX_I_ADD = 5;
	
	/**
	 * OPCODE r,x,address[,i]
	 */
	public static final int INSTR_FORMAT_R_X_ADD = 1;
	/**
	 * OPCODE x,address[,i]
	 */
	public static final int INSTR_FORMAT_X_ADD = 2;
	/**
	 * OPCODE cc,x,address[,i]
	 */
	public static final int INSTR_FORMAT_CC_X_ADD = 3;
	/**
	 * OPCODE immed
	 */
	public static final int INSTR_FORMAT_IMMED = 4;
	/**
	 * OPCODE r,immed
	 */
	public static final int INSTR_FORMAT_R_IMMED = 5;
	/**
	 * OPCODE rx,ry
	 */
	public static final int INSTR_FORMAT_RX_RY = 6;
	/**
	 * OPCODE rx
	 */
	public static final int INSTR_FORMAT_RX = 7;
	/**
	 * OPCODE r,devid
	 */
	public static final int INSTR_FORMAT_R_DEVID = 8;
	/**
	 * OPCODE r,count,L/R,A/L
	 */
	public static final int INSTR_FORMAT_R_COUNT_LR_AL = 9;
	
	
	private static InstructionSetInfo _instance;
	
	private Map<String,InstructionInfo> infos;
	private Map<Integer,String>codeToName;
	
	public static InstructionSetInfo getInstance(){
		if(_instance==null)
			_instance = new InstructionSetInfo();
		return _instance;
	}
	
	public InstructionSetInfo(){
		infos = new HashMap<String,InstructionInfo>();
		codeToName = new HashMap<Integer,String>();
		
		
		
		addInstruction("LDR", 1, BINARY_STYLE_R_IX_I_ADD, INSTR_FORMAT_R_X_ADD);
		addInstruction("STR", 2, BINARY_STYLE_R_IX_I_ADD, INSTR_FORMAT_R_X_ADD);
		addInstruction("LDA", 3, BINARY_STYLE_R_IX_I_ADD, INSTR_FORMAT_R_X_ADD);
		
		addInstruction("LDX", 41, BINARY_STYLE_R_IX_I_ADD, INSTR_FORMAT_X_ADD);
		addInstruction("STX", 42, BINARY_STYLE_R_IX_I_ADD, INSTR_FORMAT_X_ADD);
		
		addInstruction("JZ", 10, BINARY_STYLE_R_IX_I_ADD, INSTR_FORMAT_R_X_ADD);
		addInstruction("JNE", 11, BINARY_STYLE_R_IX_I_ADD, INSTR_FORMAT_R_X_ADD);
		
		addInstruction("JCC", 12, BINARY_STYLE_R_IX_I_ADD, INSTR_FORMAT_CC_X_ADD);
		
		addInstruction("JMP", 13, BINARY_STYLE_R_IX_I_ADD, INSTR_FORMAT_X_ADD);
		addInstruction("JSR", 14, BINARY_STYLE_R_IX_I_ADD, INSTR_FORMAT_X_ADD);
		
		addInstruction("RFS", 15, BINARY_STYLE_R_IX_I_ADD, INSTR_FORMAT_IMMED);
		
		addInstruction("SOB", 16, BINARY_STYLE_R_IX_I_ADD, INSTR_FORMAT_R_X_ADD);
		addInstruction("JGE", 17, BINARY_STYLE_R_IX_I_ADD, INSTR_FORMAT_R_X_ADD);
		addInstruction("AMR", 4, BINARY_STYLE_R_IX_I_ADD, INSTR_FORMAT_R_X_ADD);
		addInstruction("SMR", 5, BINARY_STYLE_R_IX_I_ADD, INSTR_FORMAT_R_X_ADD);
		
		addInstruction("AIR", 6, BINARY_STYLE_R_IX_I_ADD, INSTR_FORMAT_R_IMMED);
		addInstruction("SIR", 7, BINARY_STYLE_R_IX_I_ADD, INSTR_FORMAT_R_IMMED);
		
		
		
		addInstruction("MLT", 20, BINARY_STYLE_RX_RY, INSTR_FORMAT_RX_RY);
		addInstruction("DVD", 21, BINARY_STYLE_RX_RY, INSTR_FORMAT_RX_RY);
		addInstruction("TRR", 22, BINARY_STYLE_RX_RY, INSTR_FORMAT_RX_RY);
		addInstruction("AND", 23, BINARY_STYLE_RX_RY, INSTR_FORMAT_RX_RY);
		addInstruction("ORR", 24, BINARY_STYLE_RX_RY, INSTR_FORMAT_RX_RY);
		addInstruction("NOT", 25, BINARY_STYLE_RX_RY, INSTR_FORMAT_RX);
		
		addInstruction("SRC", 31, BINARY_STYLE_R_AL_LR_COUNT, INSTR_FORMAT_R_COUNT_LR_AL);
		addInstruction("RRC", 32, BINARY_STYLE_R_AL_LR_COUNT, INSTR_FORMAT_R_COUNT_LR_AL);
		
		addInstruction("IN", 61, BINARY_STYLE_R_DEVID, INSTR_FORMAT_R_DEVID);
		addInstruction("OUT", 62, BINARY_STYLE_R_DEVID, INSTR_FORMAT_R_DEVID);
		addInstruction("CHK", 63, BINARY_STYLE_R_DEVID, INSTR_FORMAT_R_DEVID);
		
		//part4
		addInstruction("FADD", 33, BINARY_STYLE_FR_IX_I_ADD, INSTR_FORMAT_R_X_ADD);
		addInstruction("FSUB", 34, BINARY_STYLE_FR_IX_I_ADD, INSTR_FORMAT_R_X_ADD);
		addInstruction("VADD", 35, BINARY_STYLE_FR_IX_I_ADD, INSTR_FORMAT_R_X_ADD);
		addInstruction("VSUB", 36, BINARY_STYLE_FR_IX_I_ADD, INSTR_FORMAT_R_X_ADD);
		addInstruction("CNVRT", 37, BINARY_STYLE_R_IX_I_ADD, INSTR_FORMAT_R_X_ADD);
		addInstruction("LDFR", 50, BINARY_STYLE_FR_IX_I_ADD, INSTR_FORMAT_R_X_ADD);
		addInstruction("STFR", 51, BINARY_STYLE_FR_IX_I_ADD, INSTR_FORMAT_R_X_ADD);
		
		
	}
	
	private void addInstruction(String name,int opcode,int binaryType,int formatType){
		infos.put(name, new InstructionInfo(name,opcode,binaryType,formatType));
		codeToName.put(opcode, name);
	}
	
	/**
	 * get the opcode(int) of instruction by name
	 * @param name
	 * @return
	 */
	public int getOpcodeByName(String name){
		if(infos.containsKey(name))
			return infos.get(name).getOpcode();
		return -1;
	}
	
	/**
	 * get InstructionInfo via name
	 * @param name
	 * @return
	 */
	public InstructionInfo getInstrInfoByName(String name){
		return infos.get(name);
	}
	
	public InstructionInfo getInstrInfoByCode(int code){
		return infos.get(codeToName.get(code));
	}
}
