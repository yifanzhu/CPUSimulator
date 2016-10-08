package edu.gwu.exception;

public class IllegalDeviceStatusException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9207025078705926170L;

	
	public IllegalDeviceStatusException(String msg){
		super("IllegalFileDeviceException: "+msg);
	}
}
