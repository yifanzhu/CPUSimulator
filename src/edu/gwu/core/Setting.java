package edu.gwu.core;

public class Setting {

	/**
	 * how many bits in a word
	 */
	public static final int WORD_SIZE = 18;
	
	/**
	 * how many Words in the Memory
	 */
	public static final int MEM_WORD_LENGTH = 2048;
	
	
	/**
	 * how many cachelines in Cache
	 */
	public static final int CACHELINE_SIZE = 16;
	
	/**
	 * how many Words stored in each cachelines
	 */
	public static final int CACHELINE_CAPACITY = 8;
	
	
	
	public static final String BACKUP_FILE = "./editor_backup.txt";
}
