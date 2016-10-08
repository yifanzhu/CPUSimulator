package edu.gwu.core.io;

import java.util.Observable;

import edu.gwu.core.basic.Word;
import edu.gwu.exception.IllegalDeviceStatusException;

/**
 * Interface for IO object <br>
 * they should have some methods
 * 
 * @author yangmang
 *
 */
public abstract class IODevice extends Observable {
	// some default device id
	/**
	 * device id of keyboard
	 */
	public static final int DEVID_KEYBOARD = 0;
	/**
	 * device id of printer
	 */
	public static final int DEVID_PRINTER = 1;

	/**
	 * user dev:31(a textarea) as user program source
	 */
	public static final int DEVID_EDITOR = 31;
	
	protected int devid;

	/**
	 * device should have a write method for others to store data into it
	 * 
	 * @param value
	 */
	public abstract void write(Word value) throws IllegalDeviceStatusException;

	/**
	 * device should have a read method for others to read data from it
	 * 
	 * @param value
	 */
	public abstract Word read() throws IllegalDeviceStatusException;

	/**
	 * device should have a reset method to set it into default state
	 */
	public abstract void reset() throws IllegalDeviceStatusException;

	/**
	 * device should have a getDevid method to identify it
	 * 
	 * @return
	 */
	public int getDevid() {
		return devid;
	}
}
