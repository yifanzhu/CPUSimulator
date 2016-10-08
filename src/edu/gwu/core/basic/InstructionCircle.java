package edu.gwu.core.basic;

public class InstructionCircle {

	
	public static final int TYPE_REG2REG = 0x1; //means load register to register
	public static final int TYPE_MEM2REG = 0x2; //means load register to memory
	public static final int TYPE_REG2MEM = 0x3; //means load memory to register
	public static final int TYPE_DECODE = 0x4;  //means analysis of opcode
	public static final int TYPE_CALCULATE_EA_TO_MAR = 0x5; //means calculate EA and assign the value to MAR
	public static final int TYPE_ALU_EXECUTE = 0x6; //means execute the operations
	public static final int TYPE_PC_PLUS = 0x7; //means PC++	
	public static final int TYPE_CALCULATE_MBR_FOR_EXCUTE = 0x8;
	public static final int TYPE_LOAD_OP1_AND_OP2 = 0x9; //means assign values to operand1 and operand2

	public static final int TYPE_MinusOne= 0x15;	
	public static final int TYPE_JUMP_WITHCONDITION = 0x10;		

	public static final int TYPE_DEV2REG = 0x11;	
	public static final int TYPE_REG2DEV = 0x12;	
	public static final int TYPE_CHK_DEV = 0x13;
	public static final int TYPE_LOAD_OP1 = 0x14;
	
	
	
	
	public static final int TYPE_CALCULATE_EA_TO_MAR_NO_IX = 0x16; //means calculate EA and assign the value to MAR
	
	/** used for CNVRT */
	public static final int TYPE_CONVERT = 0x17;
	
	public int type;
	public String[] args;

	
	public InstructionCircle(int type,String...args){
		this.type = type;
		this.args = args.clone();
	}
}
