package edu.gwu.core.basic;

import edu.gwu.common.Util;
import edu.gwu.core.Setting;

/**
 * Word for classical CISC computer
 * <br>default as 18-bit word
 * @author yangmang
 *
 */
public class Word {

	/**
	 * for convenience, we use int as bit. The value in data should be 0 or 1;
	 */
	private int[] data;
	
	/**
	 * construction
	 */
	public Word(){
		data = new int[Setting.WORD_SIZE];
	}
	
	public Word(Word w){
		data = w.getData().clone();
	}
	
	public Word(int[] data){
		this.data = new int[Setting.WORD_SIZE];
		this.setValue(data);
	}
	
	public Word(int value){
		data = new int[Setting.WORD_SIZE];
		this.setIntValue(value);
	}
	
	public Word(float value) {
		data = new int[Setting.WORD_SIZE];
		this.setValue(value);
	}

	public int intValue(){
		return Util.getIntValueFromBits(data);
	}
	
	public float floatValue(){
		return Util.getFloatValueFrom18bit(data);
	}
	
	public void setIntValue(int value){
		for(int i=0;i<Setting.WORD_SIZE;i++){
			data[Setting.WORD_SIZE-1-i] = value%2;
			value = value/2;
		}
	}
	
	public int[] getData(){
		return data;
	}
	
	/**
	 * return a copy of Word
	 */
	public Word clone(){
		return new Word(this);
	}
	
	/**
	 * copy the bit values in word into current data
	 * @param word
	 */
	public void setValue(Word word){
		int n[] = word.getData();
		for(int i=0;i<Setting.WORD_SIZE;i++){
			data[i] = n[i];
		}
	}
	
	public void setValue(float value){
		this.setValue(Util.get18bitFromFloatValue(value));
	}
	
	/**
	 * if data.length > 18
	 * 		write the last 18 bits of data;
	 * if data.length < 18
	 * 		use 0 to fill the empty position in the front
	 * @param data
	 */
	public void setValue(int[]data){
		for(int i=0;i<Setting.WORD_SIZE;i++){
			this.data[Setting.WORD_SIZE-i-1] = (i<data.length)?(data[data.length-1-i]):0;
		}
	}
}
