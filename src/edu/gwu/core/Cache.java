package edu.gwu.core;

import edu.gwu.common.Util;
import edu.gwu.core.basic.Word;
import edu.gwu.exception.IllegalMemoryAddressException;

public class Cache implements Memory{
	public static final int R_NOT_UESED = -2;
	public static final int R_JUST_NEW = -1;
	public static final int R_IN_USE_SOME_TIME_AGO = 0;
	public static final int R_IN_USE_JUST_NOW = 100;
	
	
	/**
	 * cachelines for this cache
	 * <br> in each cacheline, we store information including:1.valid flag, 2.tag, 3.block data
	 */
	protected CacheLine[] lines;
	
	
	/**
	 * nextReplaced points to the index of CacheLine to be replaced;
	 * each cache miss and replace, nextReplaced will be increased by one
	 * this ensure FIFO
	 */
	protected int nextReplaced;
	
	/**
	 * CacheRecord is used to store information about number of hit and miss
	 * 
	 */
	protected CacheRecord record;
	
	/**
	 * singleton
	 */
	private static Cache _instance;
	
	public static Cache getInstance(){
		if(_instance==null)
			_instance = new Cache();
		return _instance;
	}

	/**
	 * construction
	 */
	public Cache(){
		record = new CacheRecord();
		lines = new CacheLine[Setting.CACHELINE_SIZE];
		for(int i=0;i<Setting.CACHELINE_SIZE;i++)
			lines[i] = new CacheLine();
	}
	
	/**
	 * visited by CPU
	 * 
	 * @param index: physical index in memory
	 * @return
	 * @throws IllegalMemoryAddressException 
	 */
	public Word read(int index) throws IllegalMemoryAddressException{
		
		//calculate the Tag-value and offset
		int tag = index / Setting.CACHELINE_CAPACITY;
		int offset = index % Setting.CACHELINE_CAPACITY;
		
		//check each valid cacheline
		Word word = hitCache(tag,offset);
		
		//if cache-miss, load from memory
		if(word == null){
			word = loadCacheFromMemory(tag,offset);
			
		}else{
			
		}
		
		//return target data
		return word;
	}
	
	/**
	 * this method will be called when cache miss
	 * load several words from memory
	 * @param tag
	 * @param offset
	 * @return
	 * @throws IllegalMemoryAddressException
	 */
	private Word loadCacheFromMemory(int tag,int offset) throws IllegalMemoryAddressException {
		//get the target cacheline
		CacheLine line = lines[nextReplaced];
		
		//tag the replaced line in CachRecord
		record.tagCacheLineAsReplaced(nextReplaced,offset);
		
		/*
		 * forward nextReplaced
		 */
		nextReplaced++;
		if(nextReplaced>=Setting.CACHELINE_SIZE)
			nextReplaced = 0;
		
		/*
		 * read block from memory
		 * Setting.CacheLine_Capacity words exactly
		 * start at tag*CACHELINE_CAPACITY
		 */
		int start = tag * Setting.CACHELINE_CAPACITY;
		for(int i=0;i<Setting.CACHELINE_CAPACITY;i++)
			line.data[i].setValue(MainMemory.getInstance().read(start+i));
		line.tag = tag;
		line.valid = true;
		
		//return target data
		return line.data[offset];
	}

	/**
	 * check every valid cacheline via tag to find if data is cached
	 * return null where cache miss
	 * @param tag
	 * @param offset
	 * @return if hit then Word; else then null
	 */
	private Word hitCache(int tag, int offset) {
		for(int i=0;i<lines.length;i++){
			CacheLine line = lines[i];
			if(line.valid&&line.tag==tag){
				record.tagCacheWordAsJustHit(i,offset);
				return line.data[offset];
			}
		}
		return null;
	}

	/**
	 * write-through
	 * 
	 * write allocate
	 * 
	 * @param index
	 * @param word
	 * @throws IllegalMemoryAddressException 
	 */
	public void write(int index,Word word) throws IllegalMemoryAddressException{
		//calculate the Tag-value and offset
		int tag = index / Setting.CACHELINE_CAPACITY;
		int offset = index % Setting.CACHELINE_CAPACITY;
		
		//check each valid cacheline
		Word oldWord = hitCache(tag,offset);
		
		//if cache-miss, load from memory
		if(oldWord == null){
			oldWord = loadCacheFromMemory(tag,offset);
		}
		
		//change Word value in cache
		oldWord.setValue(word);
		//write into memory (write-through)
		MainMemory.getInstance().write(index, word);
		
	}
	
	/**
	 * 
	 * @param index
	 * @param data
	 * @throws IllegalMemoryAddressException
	 */
	public void write(int index,int[] data) throws IllegalMemoryAddressException{
		Word word = new Word();
		word.setValue(data);
		write(index,word);
	}
	
	public int[][] getCacheRecord(){
		return record.records;
	}
	
	public Word getWordByLineAndOffset(int line,int offset){
		return lines[line].data[offset];
	}
	
	/**
	 * reset all information in Cache
	 * <br> nextReplaced will be set to 0
	 */
	public void clear(){
		record.clear();
		for(CacheLine line:lines)
			line.valid = false;
		nextReplaced = 0;
	}
	
	/**
	 * get current hit rate information in String format
	 * @return
	 */
	public String getRateInfo(){
		String ret = "HitRate:";
		int sum = record.hit+record.miss;
		if(sum==0)
			ret+="N/A";
		else
			ret+=Util.formatPercentage(((double)record.hit)/sum);
		
		ret+=" (hit:"+record.hit+",miss:"+record.miss+")";
		
		return ret;
	}
	
	/**
	 * specific structure of inner cacheline
	 * @author yangmang
	 *
	 */
	class CacheLine{
		protected int tag;
		
		protected boolean valid;
		
		protected Word[] data;
		
		public CacheLine(){
			tag = -1;
			valid = false;
			data = new Word[Setting.CACHELINE_CAPACITY];
			for(int i=0;i<Setting.CACHELINE_CAPACITY;i++)
				data[i] = new Word();
		}
	}
	
	/**
	 * a class for recording cache information
	 * @author yangmang
	 *
	 */
	class CacheRecord{
		
		private int records[][];
		private int hit;
		private int miss;
		
		public CacheRecord(){
			records = new int[Setting.CACHELINE_SIZE][Setting.CACHELINE_SIZE];
			for(int i=0;i<Setting.CACHELINE_SIZE;i++)
				records[i] = new int[Setting.CACHELINE_SIZE];
			clear();
		}
		
		private void clear(){
			for(int i=0;i<Setting.CACHELINE_SIZE;i++)
				for(int j=0;j<Setting.CACHELINE_CAPACITY;j++)
					records[i][j] = R_NOT_UESED;
			hit = 0;
			miss = 0;
		}
		
		private void lowEveryWordRecord(){
			for(int i=0;i<Setting.CACHELINE_SIZE;i++)
				for(int j=0;j<Setting.CACHELINE_CAPACITY;j++)
					if(records[i][j]>R_IN_USE_SOME_TIME_AGO)
						records[i][j]--;
		}
		
		
		/**
		 * 
		 * @param line
		 */
		private void tagCacheLineAsReplaced(int line, int offset){
			lowEveryWordRecord();
			for(int i=0;i<Setting.CACHELINE_SIZE;i++)
				records[line][i] = R_JUST_NEW;
			records[line][offset] = R_IN_USE_JUST_NOW;
			miss ++ ;
		}
		
		private void tagCacheWordAsJustHit(int line, int offset){
			lowEveryWordRecord();
			records[line][offset] = R_IN_USE_JUST_NOW;
			hit ++;
		}
	
	}
}
