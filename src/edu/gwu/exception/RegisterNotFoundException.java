package edu.gwu.exception;

public class RegisterNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3152703136675513790L;

	public RegisterNotFoundException(String name){
		super("Register Not Found Exception: try to user register:"+name);
	}
}
