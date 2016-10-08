package edu.gwu.exception;

public class IllegalTrapCodeException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1165738313748461460L;

	public IllegalTrapCodeException(String code){
		super("Illegal TRAP code:"+code);
	}
}
