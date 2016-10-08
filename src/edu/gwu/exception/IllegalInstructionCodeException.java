package edu.gwu.exception;

public class IllegalInstructionCodeException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -809581845799714269L;

	public IllegalInstructionCodeException(String code){
		super("Illegal Instrucation Code: "+code);
	}
}
