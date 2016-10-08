package edu.gwu.core;

import edu.gwu.core.basic.Word;
import edu.gwu.exception.IllegalMemoryAddressException;

/**
 * interface for Memory
 * two methods are needed:
 * read and write
 * @author yangmang
 *
 */
public interface Memory {

	public abstract Word read(int index) throws IllegalMemoryAddressException;
	
	public abstract void write(int index,Word word) throws IllegalMemoryAddressException;
}
