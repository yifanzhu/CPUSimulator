package edu.gwu.exception;

public class IllegalMemoryAddressException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8962456956683549924L;

	public IllegalMemoryAddressException(int index){
		super("Illegal Memory Address:"+index);
	}
}
