package edu.gwu.core.basic;

import java.util.HashMap;
import java.util.Map;

/**
 * All the instructions will be defined in this class, including:
 * <br> name,code and every step(circle)
 * @author yangmang
 *
 */
public class InstructionSet {

	private static InstructionSet _instance;
	
	public static InstructionSet getInstance(){
		if(_instance==null)
			_instance = new InstructionSet();
		return _instance;
	}
	
	/**
	 * map for finding code by name
	 */
	private Map<String,Integer> name2code;
	
	/**
	 * map for finding InstructionCircles by code
	 */
	private Map<Integer,InstructionCircles> code2instrs;
	
	/**
	 * 
	 */
	public InstructionSet(){
		
		name2code = new HashMap<String,Integer>();
		code2instrs = new HashMap<Integer,InstructionCircles>();
		
		setLDR();
		
		setSTR();
		
		setLDA();
		
		setAMR();
		setSMR();
		
		setAIR();
		setSIR();
		
		
		
		setLDX();
		setSTX();
		
		setJZ();
		setJNE();
		setJCC();
		setJMP();
		setJSR();
		
		setRFS();
		setSOB();
		setJGE();
		
		setMLT();
		setDVD();
		setTRR();
		setAND();
		setORR();
		setNOT();
		
		setSRC();
		setRRC();
		
		setFADD();
		setFSUB();
		setVADD();
		setVSUB();
		setCNVRT();
		setLDFR();
		setSTFR();
		
		setIN();
		setOUT();
		setCHK();
		
		setTRAP();
	}
	
	private void putCirclesIntoMaps(String name,InstructionCircles circles){
		int code = InstructionSetInfo.getInstance().getOpcodeByName(name);
		name2code.put(name, code);
		code2instrs.put(code, circles);
	}
	
	/**
	 *  LDR: Load Register From Memory
	 *  01
	 */
	private void setLDR(){
		InstructionCircles circles = new InstructionCircles();
		//circles.addCircle(InstructionCircle.TYPE_REG2REG,"MAR","ADDR");
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CALCULATE_EA_TO_MAR));
		circles.addCircle(InstructionCircle.TYPE_MEM2REG,"MBR","MAR");
		circles.addCircle(InstructionCircle.TYPE_REG2REG,"R","MBR");
		circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("LDR",circles);
	}
	
	/**
	 * Store Register To Memory
	 * 02
	 */
	private void setSTR(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CALCULATE_EA_TO_MAR));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"MBR","R"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2MEM,"MAR","MBR"));
		circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("STR",circles);
		
	}
	
	/**
	 * Load Register with Address
	 * 3
	 */
	private void setLDA(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CALCULATE_EA_TO_MAR));
		circles.addCircle(InstructionCircle.TYPE_REG2REG,"R","MAR");
		circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("LDA",circles);
	}
	
	/**
	 * Add Memory To Register
	 * 4
	 */
	private void setAMR(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CALCULATE_EA_TO_MAR));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_MEM2REG,"MBR","MAR"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_LOAD_OP1_AND_OP2,"R","MBR"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_ALU_EXECUTE));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"R","RES"));
		circles.addCommonEndingCircles();

		putCirclesIntoMaps("AMR",circles);
	}
	
	/**
	 * Subtract Memory From Register
	 * 5
	 */
	private void setSMR(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CALCULATE_EA_TO_MAR));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_MEM2REG,"MBR","MAR"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_LOAD_OP1_AND_OP2,"R","MBR"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_ALU_EXECUTE));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"R","RES"));
		circles.addCommonEndingCircles();

		putCirclesIntoMaps("SMR",circles);
	}
	
	/**
	 * Add  Immediate to Register
	 * 6
	 */
	private void setAIR(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_LOAD_OP1_AND_OP2,"R","ADDR"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_ALU_EXECUTE));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"R","RES"));
		circles.addCommonEndingCircles();

		putCirclesIntoMaps("AIR",circles);
	}
	
	/**
	 * Subtract  Immediate  from Register
	 * 7
	 */
	private void setSIR(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_LOAD_OP1_AND_OP2,"R","ADDR"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_ALU_EXECUTE));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"R","RES"));
		circles.addCommonEndingCircles();

		putCirclesIntoMaps("SIR",circles);
	}
	
	/**
	 * Load index register from memory
	 * 41
	 */
	private void setLDX(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CALCULATE_EA_TO_MAR_NO_IX));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_MEM2REG,"MBR","MAR"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"IX","MBR"));
		circles.addCommonEndingCircles();

		putCirclesIntoMaps("LDX",circles);
	}
	
	
	/**
	 * Store Index Register to Memory
	 * 42
	 */
	private void setSTX(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CALCULATE_EA_TO_MAR_NO_IX));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"MBR","IX"));	
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2MEM,"MAR","MBR"));	
		circles.addCommonEndingCircles();

		putCirclesIntoMaps("STX",circles);
	}
	
	/**
	 * Jump If Zero
	 * 010
	 */
	private void setJZ(){
		InstructionCircles circles = new InstructionCircles();		
	    circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CALCULATE_EA_TO_MAR));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_JUMP_WITHCONDITION,"R"));			

		putCirclesIntoMaps("JZ",circles);
	}	

	/**
	 * Jump If Not Equal
	 * 011
	 */
	private void setJNE(){
		InstructionCircles circles = new InstructionCircles();		
	    circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CALCULATE_EA_TO_MAR));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_JUMP_WITHCONDITION,"R"));			

		putCirclesIntoMaps("JNE",circles);
	}	

	/**
	 * Jump If Condition Code
	 * 012
	 */
	private void setJCC(){
		InstructionCircles circles = new InstructionCircles();		
	    circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CALCULATE_EA_TO_MAR));		
	    circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_JUMP_WITHCONDITION,"CC"));			

		putCirclesIntoMaps("JCC",circles);
	}

	/**
	 * Unconditional Jump To Address
	 * 013
	 */
	private void setJMP(){
		InstructionCircles circles = new InstructionCircles();		
	    circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CALCULATE_EA_TO_MAR));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_JUMP_WITHCONDITION,"R"));			

		putCirclesIntoMaps("JMP",circles);
	}
	
	/**
	 * Jump and Save Return Address
	 * 014
	 */
	private void setJSR(){
		InstructionCircles circles = new InstructionCircles();		
	    circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CALCULATE_EA_TO_MAR));
		circles.addCommonEndingCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"R3","PC"));
	    circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_JUMP_WITHCONDITION,"R"));			

		putCirclesIntoMaps("JSR",circles);
	}
	
	/**
	 * Return From Subroutine w/ return code as Immed portion (optional) stored in the instructions address field.
	 * 015
	 */
	private void setRFS(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"R0","Immed"));	
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"PC","R3"));				

		putCirclesIntoMaps("RFS",circles);
	}	
	
	/**
	 * Subtract One and Branch
	 * 016
	 */
	private void setSOB(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(InstructionCircle.TYPE_MinusOne,"R");
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CALCULATE_EA_TO_MAR));		
	    circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_JUMP_WITHCONDITION,"R","MAR"));			

		putCirclesIntoMaps("SOB",circles);
	}	
	
	/**
	 * Jump Greater Than or Equal To
	 * 017
	 */
	private void setJGE(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CALCULATE_EA_TO_MAR));		
	    circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_JUMP_WITHCONDITION,"R","MAR"));			

		putCirclesIntoMaps("JGE",circles);
	}
	
	/**
	 * Logical And of Register and Register
	 * 023
	 */
	private void setAND(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_LOAD_OP1_AND_OP2,"RX","RY"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_ALU_EXECUTE));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"RX","RES"));
		circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("AND",circles);
	}	

	/**
	 * Logical Or of Register and Register
	 * 024
	 */
	private void setORR(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_LOAD_OP1_AND_OP2,"RX","RY"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_ALU_EXECUTE));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"RX","RES"));
		circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("ORR",circles);
	}

	/**
	 * Logical Not of Register To Register
	 * 025
	 */
	private void setNOT(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_LOAD_OP1_AND_OP2,"RX","RY"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_ALU_EXECUTE));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"RX","RES"));
		circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("NOT",circles);
	}
	
	
	/**
	 * Input Character To Register from Device
	 * 061
	 */
	private void setIN(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_DEV2REG,"R","DEVID"));
		circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("IN",circles);
	}
	
	/**
	 * Output Character to Device from Register
	 * 062
	 */
	private void setOUT(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2DEV,"R","DEVID"));
		circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("OUT",circles);
	}	

	/**
	 * Check Device Status to Register
	 * 063
	 */
	private void setCHK(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CHK_DEV,"R","DEVID"));
		circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("CHK",circles);
	}

	/**
	 * Multiply Register by Register
	 * 020
	 */
	private void setMLT(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_LOAD_OP1_AND_OP2,"RX","RY"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_ALU_EXECUTE));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"RX","RES"));
		circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("MLT",circles);
	}
	
	/**
	 * Divide Register by Register
	 * 021
	 */
	private void setDVD(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_LOAD_OP1_AND_OP2,"RX","RY"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_ALU_EXECUTE));
		circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("DVD",circles);
	}
	
	
	/**
	 * Test the Equality of Register and Register
	 * 022
	 */
	private void setTRR(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_LOAD_OP1_AND_OP2,"RX","RY"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_ALU_EXECUTE));
		circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("TRR",circles);
	}
	

	/**
	 * Shift Register by Count
	 * 031
	 */
	private void setSRC(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_LOAD_OP1,"R"));		
		circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("SRC",circles);
	}	
	
	/**
	 * Rotate Register by Count
	 * 032
	 */
	private void setRRC(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_LOAD_OP1,"R"));
		circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("RRC",circles);
	}
	
	//TODO***************************I'm here. Continue your work from here!********************
	private void setTRAP(){
		InstructionCircles circles = new InstructionCircles();
		//circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_LOAD_OP1_AND_OP2,"RX","RY"));
		//circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_ALU_EXECUTE));
		//circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"RX","RES"));
		//circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"RX+1","RES"));
		//circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("TRAP",circles);
	}
	
	/**
	 * Floating Add Memory To Register
	 * 033
	 */
	private void setFADD(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CALCULATE_EA_TO_MAR));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_MEM2REG,"MBR","MAR"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_LOAD_OP1_AND_OP2,"FR","MBR"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_ALU_EXECUTE));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"FR","RES"));
		circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("FADD",circles);
	}
	
	/**
	 * Floating Subtract Memory From Register
	 * 034
	 */
	private void setFSUB(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CALCULATE_EA_TO_MAR));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_MEM2REG,"MBR","MAR"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_LOAD_OP1_AND_OP2,"FR","MBR"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_ALU_EXECUTE));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"FR","RES"));
		circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("FSUB",circles);
	}	

	/**
	 * Vector Add
	 * 035
	 */
	private void setVADD(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CALCULATE_EA_TO_MAR));
		//circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_MEM2REG,"MBR","MAR"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_LOAD_OP1_AND_OP2,"FR","MAR"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_ALU_EXECUTE));
		//circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"FR","RES"));
		circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("VADD",circles);
	}

	/**
	 * Vector Subtract
	 * 036
	 */
	private void setVSUB(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CALCULATE_EA_TO_MAR));
		//circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_MEM2REG,"MBR","MAR"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_LOAD_OP1_AND_OP2,"FR","MAR"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_ALU_EXECUTE));
		//circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"FR","RES"));
		circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("VSUB",circles);
	}

	/**
	 * Convert to Fixed/FloatingPoint
	 * 037
	 */
	private void setCNVRT(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CALCULATE_EA_TO_MAR));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_MEM2REG,"MBR","MAR"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_LOAD_OP1_AND_OP2,"R","MBR"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CONVERT));
		//circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"RX","RES"));
		//circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"RX+1","RES"));
		circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("CNVRT",circles);
	}
	
	/**
	 * Load Floating Register From Memory
	 * 050
	 */
	private void setLDFR(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CALCULATE_EA_TO_MAR));
		circles.addCircle(InstructionCircle.TYPE_MEM2REG,"MBR","MAR");
		circles.addCircle(InstructionCircle.TYPE_REG2REG,"FR","MBR");
		circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("LDFR",circles);
	}
	
	/**
	 * Store Floating Register To Memory
	 * 051
	 */
	private void setSTFR(){
		InstructionCircles circles = new InstructionCircles();
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_CALCULATE_EA_TO_MAR));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"MBR","FR"));
		circles.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2MEM,"MAR","MBR"));
		circles.addCommonEndingCircles();
		
		putCirclesIntoMaps("STFR",circles);
	}

	
	

	
//	public InstructionCircles getCircles2(String name){
//		return code2instrs.get(name2code.get(name));
//	}
//	public InstructionCircles getCircles2(int code){
//		return code2instrs.get(code);
//	}
	public static InstructionCircles getCircles(String name){
		return InstructionSet.getInstance().code2instrs.get(
				InstructionSet.getInstance().name2code.get(name));
	}
	public static InstructionCircles getCircles(int code){
		return InstructionSet.getInstance().code2instrs.get(code);
	}
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static int getCodeOfInstruction(String name){
		return InstructionSet.getInstance().name2code.get(name);
	}
}
