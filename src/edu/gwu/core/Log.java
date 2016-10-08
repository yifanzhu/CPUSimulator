package edu.gwu.core;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import edu.gwu.common.MVCMsg;
import edu.gwu.core.basic.ConsoleMessage;

/**
 * 
 * @author yangmang
 * 
 */
public class Log extends Observable{

	
	
	private static Log _instance;
	public static Log getInstance(){
		if(_instance==null)
			_instance = new Log();
		return _instance;
	}
	
	/**
	 * all message will be stored here
	 * <br>
	 */
	private Queue<ConsoleMessage> consoleMessage;
	
	private boolean circleFinishedFlg = false;
	
	public boolean isCircleFinishedFlg() {
		return circleFinishedFlg;
	}

	public Log(){
		consoleMessage = new LinkedList<ConsoleMessage>();
	}
	
	/**
	 * add a ConsoleMessage into the list, when observers is notified, they will pull those messages out
	 * @param type: the type of the message
	 * @param info: content
	 */
	public void console(int type,String info){
		// do something
		consoleMessage.add(new ConsoleMessage(type,info));
		
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * notify observers
	 */
	public void changeState(){
		circleFinishedFlg = false;
		this.setChanged();
		this.notifyObservers();
	}
	
	public void circleFinished(){
		this.setChanged();

		this.notifyObservers(MVCMsg.MSG_CIRCLE_FINISH);
	}
	
	
	
	/**
	 * get Message queue.
	 * @return
	 */
	public Queue<ConsoleMessage> getConsoleMessage() {
		return consoleMessage;
	}
}
