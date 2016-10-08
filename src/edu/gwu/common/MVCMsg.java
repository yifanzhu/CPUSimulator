package edu.gwu.common;

/**
 * used for MVC to identify different messages
 * @author yangmang
 *
 */
public class MVCMsg {

	public static final int MSG_POWERON = 1;
	
	public static final int MSG_POWEROFF = 2;
	
	
	public static final int MSG_CIRCLE_FINISH = 3;
	
	
	public static final int MSG_USER_PROGRAM_CONFIRM = 4;
	
	
	
	public static final int MSG_CPU_DEBUG_WAITING = 5;
	
	public static final int MSG_CPU_DEBUG_CONTINUE = 6;
	
	/**
	 * waiting for keyboard
	 */
	public static final int MSG_KEYBOARD_WAITING = 7;
	
	/**
	 * keyboard pressed event
	 */
	public static final int MSG_KEYBOARD_PRESSED = 8;
	
	/**
	 * pipeline flush or state change message
	 */
	public static final int MSG_CPU_PIPELINE = 9;
}
