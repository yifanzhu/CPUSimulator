package edu.gwu.exception;

public class IllegalOperationCodeException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8456923390302002981L;

	public IllegalOperationCodeException(String operation){
		super("Illegal Operation Code:"+operation);
	}
}
