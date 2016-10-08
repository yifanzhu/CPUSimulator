package edu.gwu.core.basic;

public class InstructionInfo {

	private int binaryType;
	private int instrType;
	private String name;
	private int opcode;
	
	public InstructionInfo(String name,int opcode,int binaryType,int instrType){
		this.binaryType = binaryType;
		this.instrType = instrType;
		this.name = name;
		this.opcode = opcode;
	}

	public int getBinaryType() {
		return binaryType;
	}

	public int getInstrType() {
		return instrType;
	}

	public String getName() {
		return name;
	}

	public int getOpcode() {
		return opcode;
	}
	
	
}
