package edu.gwu.core.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.gwu.common.MVCMsg;
import edu.gwu.core.Setting;
import edu.gwu.core.basic.Word;
import edu.gwu.exception.IllegalDeviceStatusException;


/**
 * this class is 
 * @author yangmang
 *
 */
public class CodeEditorDevice extends IODevice{

	
	private Thread waitingThread = null;
	
	private Queue<Word> data = null;
	
	
	public CodeEditorDevice(){
		this.devid = IODevice.DEVID_EDITOR;
	}
	
	@Override
	public void write(Word value) throws IllegalDeviceStatusException {
		throw new IllegalDeviceStatusException("Cannot write into Device("+devid+"), it is readonly");
	}

	@Override
	public Word read() throws IllegalDeviceStatusException {
		
		if(data == null){
			this.waitingThread = Thread.currentThread();
			synchronized (waitingThread) {
				try {
					this.setChanged();
					this.notifyObservers(MVCMsg.MSG_USER_PROGRAM_CONFIRM);
					waitingThread.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if(data.size()==0){
			data = null;
			return new Word(0);
		}
			
		
		return data.poll();
	}

	public void fillDeviceWithCodes(List<Word> codes){
		
		if(this.waitingThread==null)
			return;
		
		data = new LinkedList<Word>();
		
		for(Word word:codes){
			data.add(word);
		}
		//data.add(new Word(0));
		
		synchronized (waitingThread) {
			this.waitingThread.notify();
			this.waitingThread = null;
		}
		
	}
	
	@Override
	public void reset() throws IllegalDeviceStatusException {
		if(this.waitingThread!=null){
			synchronized (waitingThread) {
				this.waitingThread.notify();
				this.waitingThread = null;
			}
		}
		
		this.setChanged();
		this.notifyObservers(MVCMsg.MSG_POWEROFF);
	}

	
	public void backup(String in){
		File file = new File(Setting.BACKUP_FILE);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			writer.write(in);
			
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void backup(List<String> in){
		StringBuffer buf = new StringBuffer();
		for(int i=0;i<in.size();i++){
			if(i>0)
				buf.append("\n");
			buf.append(in.get(i));
		}
		backup(buf.toString());
	}
	
	
	/**
	 * read backup file and get the code lines
	 * @return
	 */
	public List<String> readBackup(){
		List<String> codes = new ArrayList<String>();
		
		File file = new File(Setting.BACKUP_FILE);
		if(file==null || !file.exists())
			return codes;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while(line!=null){
				
				codes.add(line);
				
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return codes;
	}
}
