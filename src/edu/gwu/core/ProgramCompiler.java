package edu.gwu.core;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import edu.gwu.common.Util;
import edu.gwu.core.basic.InstructionInfo;
import edu.gwu.core.basic.InstructionSetInfo;

/**
 * compiler 
 * @author yangmang
 *
 */
public class ProgramCompiler {

	/**
	 * decode the language code into binary code
	 * @param ins
	 * @return
	 */
	public static int[] decodeInstruction(String ins){
		try {
			StringTokenizer tokenizer = new StringTokenizer(ins," ,");
			
			//while(tokenizer.hasMoreElements()){
			//	System.out.println(tokenizer.nextToken());
			//}
			
			//get InstructionInfo by Name
			InstructionInfo insInfo = InstructionSetInfo.getInstance().getInstrInfoByName(tokenizer.nextToken());
			
			//return null if no instruction
			if(insInfo == null)
				return null;
			
			
			//OPCODE
			int opcodeValue = insInfo.getOpcode();
			
			//get arguments information from code
			int r=0,ix=0,i=0,add=0,rx=0,ry=0,al=0,lr=0,devid=0,count=0;
			switch (insInfo.getInstrType()) {
			case (InstructionSetInfo.INSTR_FORMAT_CC_X_ADD):
				//cc -> r
				r = Integer.parseInt(tokenizer.nextToken());
				ix = Integer.parseInt(tokenizer.nextToken());
				add = Integer.parseInt(tokenizer.nextToken());
				//check if there is indirect flag after address
				if(tokenizer.hasMoreTokens())
					i = Integer.parseInt(tokenizer.nextToken());
				break;
			case (InstructionSetInfo.INSTR_FORMAT_IMMED):
				//immed -> add
				add = Integer.parseInt(tokenizer.nextToken());
				break;
			case (InstructionSetInfo.INSTR_FORMAT_R_COUNT_LR_AL):
				r = Integer.parseInt(tokenizer.nextToken());
				count = Integer.parseInt(tokenizer.nextToken());
				lr = Integer.parseInt(tokenizer.nextToken());
				al = Integer.parseInt(tokenizer.nextToken());
				break;
			case (InstructionSetInfo.INSTR_FORMAT_R_DEVID):
				r = Integer.parseInt(tokenizer.nextToken());
				devid = Integer.parseInt(tokenizer.nextToken());
				break;
			case (InstructionSetInfo.INSTR_FORMAT_R_IMMED):
				r = Integer.parseInt(tokenizer.nextToken());
				//immed -> add
				add = Integer.parseInt(tokenizer.nextToken());
				break;
			case (InstructionSetInfo.INSTR_FORMAT_R_X_ADD):
				r = Integer.parseInt(tokenizer.nextToken());
				ix = Integer.parseInt(tokenizer.nextToken());
				add = Integer.parseInt(tokenizer.nextToken());
				//check if there is indirect flag after address
				if(tokenizer.hasMoreTokens())
					i = Integer.parseInt(tokenizer.nextToken());
				break;
			case (InstructionSetInfo.INSTR_FORMAT_RX):
				rx = Integer.parseInt(tokenizer.nextToken());
				break;
			case (InstructionSetInfo.INSTR_FORMAT_RX_RY):
				rx = Integer.parseInt(tokenizer.nextToken());
				ry = Integer.parseInt(tokenizer.nextToken());
				break;
			case (InstructionSetInfo.INSTR_FORMAT_X_ADD):
				ix = Integer.parseInt(tokenizer.nextToken());
				add = Integer.parseInt(tokenizer.nextToken());
				//check if there is indirect flag after address
				if(tokenizer.hasMoreTokens())
					i = Integer.parseInt(tokenizer.nextToken());
				break;
			}
			
			//build binary code
			List<Integer> code = new ArrayList<Integer>();
			
			
			
			int[]ops = Util.getBitsFromIntValue(opcodeValue, 6);
			Util.mergeList(code, ops);
			
			switch(insInfo.getBinaryType()){
			case(InstructionSetInfo.BINARY_STYLE_R_AL_LR_COUNT):
				//OPCODE(6),(2),R(2),A/L(1),L/R(1),(1),Count(5)
				Util.mergeList(code, Util.getBitsFromIntValue(0, 2));
				Util.mergeList(code, Util.getBitsFromIntValue(r, 2));
				Util.mergeList(code, Util.getBitsFromIntValue(al, 1));
				Util.mergeList(code, Util.getBitsFromIntValue(lr, 1));
				Util.mergeList(code, Util.getBitsFromIntValue(0, 1));
				Util.mergeList(code, Util.getBitsFromIntValue(count, 5));
				break;
			
			case(InstructionSetInfo.BINARY_STYLE_R_DEVID):
				//OPCODE(6),(2),R(2),(3),DevId(5)
				Util.mergeList(code, Util.getBitsFromIntValue(0, 2));
				Util.mergeList(code, Util.getBitsFromIntValue(r, 2));
				Util.mergeList(code, Util.getBitsFromIntValue(0, 3));
				Util.mergeList(code, Util.getBitsFromIntValue(devid, 5));
				break;
			
			case(InstructionSetInfo.BINARY_STYLE_R_IX_I_ADD):
				//OPCODE(6),R(2),IX(2),I(1),ADD(7)
				Util.mergeList(code, Util.getBitsFromIntValue(r, 2));
				Util.mergeList(code, Util.getBitsFromIntValue(ix, 2));
				Util.mergeList(code, Util.getBitsFromIntValue(i, 1));
				Util.mergeList(code, Util.getBitsFromIntValue(add, 7));
				break;
				
			case(InstructionSetInfo.BINARY_STYLE_FR_IX_I_ADD):
				//OPCODE(6),FR(2),IX(2),I(1),ADD(7)
				Util.mergeList(code, Util.getBitsFromIntValue(r, 2));
				Util.mergeList(code, Util.getBitsFromIntValue(ix, 2));
				Util.mergeList(code, Util.getBitsFromIntValue(i, 1));
				Util.mergeList(code, Util.getBitsFromIntValue(add, 7));
				break;
			
			case(InstructionSetInfo.BINARY_STYLE_RX_RY):
				//OPCODE(6),Rx(2),Ry(2),(8)
				Util.mergeList(code, Util.getBitsFromIntValue(rx, 2));
				Util.mergeList(code, Util.getBitsFromIntValue(ry, 2));
				Util.mergeList(code, Util.getBitsFromIntValue(0, 8));
				break;
			}
			int[]result = new int[18];
			for(int index=0;index<code.size();index++)
				result[index] = code.get(index);
			
			return result;
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}
	
}
