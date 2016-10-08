package edu.gwu.common;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Util {

	private static Format PERCENTAGE_FORMAT = new DecimalFormat("0.00%");
	
	public static final String STR_SPACE = " ";
	public static final String STR_BLANK = "";
	
	//Transfer bits to int value
	public static int getIntValueFromBits(int[]bits){
		int val = 0;
		for(int i=0;i<bits.length;i++){
			val = val*2 + bits[i];
		}
		return val;
	}
	//Transfer int value to bits
	public static int[] getBitsFromIntValue(int value,int size){
		int[] data = new int[size];
		for(int i=0;i<size;i++){
			data[size-1-i] = value%2;
			value = value/2;
		}
		return data;
	}
	//Transfer Bits to Binary String
		public static String getBinaryStringFromBits(int[]bits){
			StringBuffer buf = new StringBuffer();
			for(int i=0;i<bits.length;i++){
				buf.append(bits[i]);
				if(i%4==3)
					buf.append(' ');
			}
			return buf.toString();
		}
		
	public static String getBinaryStringFromIntValue(int value, int size){
		return getBinaryStringFromBitsNoBlank(getBitsFromIntValue(value, size));
	}
		
	/**
	 * Transfer Bits to Binary String without blank
	 * @param bits
	 * @return
	 */
	public static String getBinaryStringFromBitsNoBlank(int[]bits){
		StringBuffer buf = new StringBuffer();
		for(int i=0;i<bits.length;i++){
			buf.append(bits[i]);
		}
		return buf.toString();
	}
	
	//Transfer Bits Instruction to Bianry String
	public static String getBinaryStringFormBitsInInstructionFormat(int[]bits){
		if(bits==null)
			return "------  ------------";
		StringBuffer buf = new StringBuffer();
		for(int i=0;i<bits.length;i++){
			buf.append(bits[i]);
			if(i==5)
				buf.append("  ");
		}
		return buf.toString();
	}
	//Get current time
	private static Format timeFormat = new SimpleDateFormat("HH:mm:ss");
	public static String getCurrentTime(){
		return timeFormat.format(new Date());
	}
	//Transfer Binary String to Bits
	public static int[] getBitsFromBinaryString(String str){
		int[] result = new int[str.length()];
		for(int i=0;i<result.length;i++)
			result[i] = Integer.parseInt(String.valueOf(str.charAt(i)));
		return result;
	}
	//Merge list
	public static void mergeList(List<Integer> array,int[] data){
		for(int i=0;i<data.length;i++)
			array.add(data[i]);
	}
	
	
	public static String formatPercentage(double value){
		return PERCENTAGE_FORMAT.format(value);
	}
	
	/**
	 * transfer 18-bit into float value
	 * @param data
	 * @return
	 */
	public static float getFloatValueFrom18bit(int[] data) {
		
		if(Util.getIntValueFromBits(data)==0)
			return 0;
		
		int exp = Util.getIntValueFromBits(Util.getSubBit(data, 1, 7)) - 63;
		
		float m = 1f;
		float base = 1f;
		for(int i=8;i<18;i++){
			base = base / 2;
			if(data[i]==1)
				m+=base;
		}
		
		if(data[0]==1)
			m = -m;
		
		return (float) ( m * Math.pow(2, exp) );
	}
	
	
	public static int[] get18bitFromFloatValue(float value){
		
		//first bit
		int sign = (value<0)?1:0;
		int[] data = new int[18];
		
		if(value==0)
			return data;
		
		int exp = 0;
		
		if(sign==1)
			value = -value;
		
		data[0] = sign;
		
		//7 bit for EXP
		if(value == 0){
			exp = 0;
		}else{
			while((value>=2||value<1)&&exp>=-63&&exp<=64){
				if(value<1){
					value = value*2;
					exp -- ;
				}
				else if(value>=2){
					value = value/2;
					exp ++;
				}
			}
		}
		exp = exp + 63;
		int[] expBits = Util.getBitsFromIntValue(exp, 7);
		for(int i=0;i<7;i++){
			data[i+1] = expBits[i];
		}
		
		//last 10 bit
		float base = 1f;
		for(int i=8;i<18;i++){
			//eliminate the integer part
			value = value - (int)value;
			//double it
			value = value * 2;
			//reset base
			base = base/2;
			//check if this bit is 1
			data[i] = (value>=1)?1:0;
		}
		
		
		return data;
	}
	
	public static int[] get18bitFromFixedValue(float value){
		return new int[18];
	}
	
	public static int[] getSubBit(int[]data,int index,int size){
		int[] sub = new int[size];
		for(int i=0;i<size;i++)
			sub[i] = data[index+i];
		return sub;
	}
	
	public static void main(String args[]){
		for( int count = 0;count<100;count++){
			int exp = (int)Math.pow(10,(int)(Math.random()*5));
			float rVal = (Math.random()>0.5?-1:1)*(float)(Math.random()*exp);
			int[] data = Util.get18bitFromFloatValue(rVal);
			System.out.println(rVal+" "+Util.getBinaryStringFromBits(data)+" "+Util.getFloatValueFrom18bit(data));
		}
		
		System.out.println(Util.getFloatValueFrom18bit(Util.getBitsFromBinaryString("011111110000000000")));
	}
	
	public static float FLOAT_MAX = Util.getFloatValueFrom18bit(Util.getBitsFromBinaryString("111111110000000000"));
	public static float FLOAT_MIN = Util.getFloatValueFrom18bit(Util.getBitsFromBinaryString("011111110000000000"));
}
