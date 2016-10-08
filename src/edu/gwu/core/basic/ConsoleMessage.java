package edu.gwu.core.basic;

import edu.gwu.common.Util;

public class ConsoleMessage {

	public static final int TYPE_ERROR = 0;
	public static final int TYPE_CIRCLE = 1;
	public static final int TYPE_LOG = 2;
	public static final int TYPE_SUCCESS = 3;
	public static final int TYPE_INSTRUCTION = 4;
	public static final int TYPE_WAIT = 5;
	
	public static final String[] PREFIX= {"[ERROR]","[CIRCLE]","[INFO]","[SUCCESS]","[INSTR]","[WAIT]"};
	
	/**
	 * 
	 */
	public int type;
	public String message;
	public String time;
	
	public ConsoleMessage(int type,String message){
		this.type = type;
		this.message = message;
		this.time = Util.getCurrentTime();
	}
	
	public String toString(){
		return PREFIX[type]+time+Util.STR_SPACE+message;
	}
}
