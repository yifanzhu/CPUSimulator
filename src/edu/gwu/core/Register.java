package edu.gwu.core;

import edu.gwu.common.Util;

/**
 * 
 * @author yangmang
 *
 */
public class Register {

	protected String name;
	protected int data[];
	protected int size;
	
	
	
	
	public void setBitAt(int bit,int index){
		data[index] = bit;
	}
	
	public Register(String name,int size){
		this.name = name;
		this.size = size;
		this.data = new int[size];
	}
	
	public int[] read(){
		return data;
	}
	
	/**
	 * write input information into Register
	 * <br>if in.length<size: copy all bits from $in$ and set the left to 0
	 * <br>if in.length>size: copy only the $size$ bits of $in$ and ignore the others
	 * @param in
	 */
	public void write(int in[]){
		//TODO :exception
		for(int i=0;i<size;i++){
			if(i<in.length)
				data[size - i - 1] = in[in.length-1-i];
			else
				data[size-i-1] = 0;
		}
	}
	
	public int intValue(){
		return Util.getIntValueFromBits(data);
	}
	
	public float floatValue(){
		return Util.getFloatValueFrom18bit(data);
	}
	
	public void setValueByInt(int value){
		int val = value;
		for(int i=0;i<size;i++){
			data[size-i-1] = val%2;
			val = val/2;
		}
	}
	
	public void addValueByInt(int value){
		this.setValueByInt(this.intValue()+value);
	}
	
	public int[] subValue(int index,int size){
		int[] sub = new int[size];
		for(int i=0;i<size;i++)
			sub[i] = data[index+i];
		return sub;
	}
	public String getBinaryString(){
		StringBuffer buf = new StringBuffer();
		for(int i=0;i<size;i++)
			buf.append(data[i]);
		return buf.toString();
	}
	public int getSize(){
		return size;
	}

	public void setValueByFloat(float floatValue) {
		int[] fData = Util.get18bitFromFloatValue(floatValue);
		for(int i=0;i<18;i++)
			data[i] = fData[i];
	}
	
	public void setValueByFixed(float floatValue) {
		int[] fData = Util.get18bitFromFixedValue(floatValue);
		for(int i=0;i<18;i++)
			data[i] = fData[i];
	}
}
