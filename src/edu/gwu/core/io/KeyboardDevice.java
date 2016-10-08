package edu.gwu.core.io;

import edu.gwu.common.MVCMsg;
import edu.gwu.core.Log;
import edu.gwu.core.basic.ConsoleMessage;
import edu.gwu.core.basic.Word;

public class KeyboardDevice extends IODevice{

	private int keyEvent;
	
	private Thread waitingThread = null;
	
	public KeyboardDevice(){
		this.devid = IODevice.DEVID_KEYBOARD;
	}

	@Override
	public void reset() {
		if(waitingThread!=null){
			synchronized(waitingThread){
				this.keyEvent = 0;
				waitingThread.notify();
				waitingThread = null;
			}
		}
	}

	@Override
	public void write(Word value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Word read() {
		
		/*
		while(!keyEventFlag){
			try {
				System.out.println("NOthing, wait...");
				Thread.sleep(100);
				//Thread.currentThread().wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Something, "+keyEvent);
		*/
		
		//tag current thread then make it wait for INPUT
		this.waitingThread = Thread.currentThread();
		//System.out.println("Start waiting...");
		try {
			synchronized (waitingThread) {
				Log.getInstance().console(ConsoleMessage.TYPE_WAIT, "wait for keyboard");
				this.setChanged();
				this.notifyObservers(MVCMsg.MSG_KEYBOARD_WAITING);
				waitingThread.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//waiting will be terminated by activeEvent which is called when user makes an input
		//input value will be stored in keyEvent
		//System.out.println("Signal coming...");
		return new Word(keyEvent);
	}

	/**
	 * answer when there is an input from iodevice
	 * <br> first check if there is a thread waiting for input
	 * <br> then store the input value
	 * <br> awake that thread
	 * @param event
	 */
	public void activeEvent(int event){
		if(waitingThread!=null){
			synchronized(waitingThread){
				this.keyEvent = event;
				waitingThread.notify();
				waitingThread = null;
				this.setChanged();
				this.notifyObservers(MVCMsg.MSG_KEYBOARD_PRESSED);
			}
		}
	}

}
