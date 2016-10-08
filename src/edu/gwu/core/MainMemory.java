package edu.gwu.core;

import edu.gwu.core.basic.Word;
import edu.gwu.exception.IllegalMemoryAddressException;

/**
 * A class to simulate It provides methods: Main memory 
 * @author yangmang
 *
 */
public class MainMemory implements Memory{

	private static MainMemory _instance;
	
	private Word memData[];
	
	public MainMemory(){
		this.memData = new Word[Setting.MEM_WORD_LENGTH];
		for(int i=0;i<Setting.MEM_WORD_LENGTH;i++)
			memData[i] = new Word();
	}
	
	
	public Word read(int index) throws IllegalMemoryAddressException{
		if(index<0||index>Setting.MEM_WORD_LENGTH)
			throw new IllegalMemoryAddressException(index);
		return this.memData[index].clone();
	}
	
	/**
	 * turn int[]data into Word before write
	 * @param index
	 * @param data
	 * @throws IllegalMemoryAddressException
	 */
	public void write(int index,int[] data) throws IllegalMemoryAddressException{
		if(index<0||index>Setting.MEM_WORD_LENGTH)
			throw new IllegalMemoryAddressException(index);
		
		this.memData[index].setValue(data);
	}
	
	/**
	 * 
	 * @param index
	 * @param word
	 * @throws IllegalMemoryAddressException
	 */
	public void write(int index,Word word) throws IllegalMemoryAddressException{
		if(index<0||index>Setting.MEM_WORD_LENGTH)
			throw new IllegalMemoryAddressException(index);
		
		this.memData[index].setValue(word);
		
	}
	
	public static MainMemory getInstance(){
		if(_instance == null){
			initInstance();
		}
		return _instance;
	}
	
	private static void initInstance(){
		_instance = new MainMemory();
	}


	public void clear() {
		for(Word word:memData)
			word.setIntValue(0);
	}


	public Word[] getMemData() {
		return memData;
	}
	
}
