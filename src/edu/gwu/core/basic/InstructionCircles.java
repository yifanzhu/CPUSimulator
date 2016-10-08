package edu.gwu.core.basic;

import java.util.ArrayList;
import java.util.List;

public class InstructionCircles {
	private List<InstructionCircle> circles;
	private static InstructionCircles _commonBeginning;
	
	public InstructionCircles(){
		circles = new ArrayList<InstructionCircle>();		
	}
	
	
	public void addCircle(InstructionCircle circle){
		circles.add(circle);
	}
	
	public void addCircle(int type,String...args){
		circles.add(new InstructionCircle(type,args));
	}
	
	public void addCommonEndingCircles(){
		circles.add(new InstructionCircle(InstructionCircle.TYPE_PC_PLUS));
	}
	
	public List<InstructionCircle> getCircles(){
		return this.circles;
	}
	
	public static InstructionCircles getCommonBeginning(){
		if(_commonBeginning==null){
			_commonBeginning = new InstructionCircles();
			//insert common circles at beginning
			_commonBeginning.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"MAR","PC"));
			_commonBeginning.addCircle(new InstructionCircle(InstructionCircle.TYPE_MEM2REG,"MBR","MAR"));
			_commonBeginning.addCircle(new InstructionCircle(InstructionCircle.TYPE_REG2REG,"IR","MBR"));
			_commonBeginning.addCircle(new InstructionCircle(InstructionCircle.TYPE_DECODE));
			
		}
		return _commonBeginning;
	}
	
}
