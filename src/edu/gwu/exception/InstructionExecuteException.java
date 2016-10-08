package edu.gwu.exception;

public class InstructionExecuteException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1614316641031354260L;

	public InstructionExecuteException(String name){
		super("Instruction Execute Exception: "+name);
	}
}
