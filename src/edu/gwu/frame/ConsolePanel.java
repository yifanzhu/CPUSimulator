package edu.gwu.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import edu.gwu.core.Log;
import edu.gwu.core.basic.ConsoleMessage;

public class ConsolePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3789059821467636448L;

	private Log log = Log.getInstance();

	private Queue<ConsoleMessage> msgs;
	private int maxMsg = 100;
	
	private boolean changed = false;
	
	private Font smaller = getFont().deriveFont(10f);
	
	private JCheckBox logCheck,errorCheck,circleCheck,instrCheck,waitCheck;
	
	/**
	 * use JTextPane instead of JTextArea 
	 * <br>because JTextPane can set different lines into different colors
	 * <br> normal logs will be in White foreground (Black background)
	 * <br> error logs will be in Red foreground (Black background)
	 */
	//private JTextPane text;
	
	private JTextArea text;
	
	private SimpleAttributeSet[] attrs;
	
	
	Document doc;
	
	/**
	 * construction
	 */
	public ConsolePanel(){
		
		TitledBorder nameTitle =new TitledBorder("Console"); 
		
		this.setBorder(nameTitle);
		
		this.setLayout(new BorderLayout());
		
		text = new JTextArea();
		text.setBounds(0, 0, 400, 200);
		text.setBackground(Color.black);
		text.setForeground(Color.white);
		
	//	text.setLineWrap(true);
		
		text.setEditable(false);
		doc = text.getDocument();
		
		//this.add(text);
		JScrollPane js = new JScrollPane(text);
		js.setBackground(Color.black);
		this.add(js,BorderLayout.CENTER);
		
		JPanel checks = new JPanel();
		logCheck = new JCheckBox("LOG");
		logCheck.setSelected(true);
		errorCheck = new JCheckBox("ERROR");
		errorCheck.setSelected(true);
		circleCheck = new JCheckBox("CIRCLE");
		circleCheck.setSelected(false);
		instrCheck = new JCheckBox("INSTR");
		instrCheck.setSelected(true);
		waitCheck = new JCheckBox("WAIT");
		waitCheck.setSelected(true);
		logCheck.setFont(smaller);
		errorCheck.setFont(smaller);
		circleCheck.setFont(smaller);
		instrCheck.setFont(smaller);
		waitCheck.setFont(smaller);
		
		
		checks.add(logCheck);
		checks.add(errorCheck);
		checks.add(circleCheck);
		checks.add(instrCheck);
		checks.add(waitCheck);
		this.add(checks,BorderLayout.SOUTH);
		
		attrs = new SimpleAttributeSet[10];
		
		attrs[ConsoleMessage.TYPE_LOG] = new SimpleAttributeSet();
		
		attrs[ConsoleMessage.TYPE_ERROR] = new SimpleAttributeSet();
		StyleConstants.setForeground(attrs[ConsoleMessage.TYPE_ERROR], Color.red);
		StyleConstants.setBackground(attrs[ConsoleMessage.TYPE_ERROR], Color.blue);
		
		attrs[ConsoleMessage.TYPE_CIRCLE] = new SimpleAttributeSet();
		StyleConstants.setForeground(attrs[ConsoleMessage.TYPE_CIRCLE], Color.yellow);
		
		attrs[ConsoleMessage.TYPE_INSTRUCTION] = new SimpleAttributeSet();
		StyleConstants.setForeground(attrs[ConsoleMessage.TYPE_INSTRUCTION], Color.orange);
		
		attrs[ConsoleMessage.TYPE_WAIT] = new SimpleAttributeSet();
		StyleConstants.setForeground(attrs[ConsoleMessage.TYPE_WAIT],new Color(125,218,121));

		
		
		
		
		msgs = new LinkedList<ConsoleMessage>();
		
		ActionListener checkListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				showAll();
			}
			
		};
		
		logCheck.addActionListener(checkListener);
		errorCheck.addActionListener(checkListener);
		circleCheck.addActionListener(checkListener);
		instrCheck.addActionListener(checkListener);
		waitCheck.addActionListener(checkListener);
		
		new Thread(){
			public void run(){
				while(true){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(changed)
						showLeft();
				}
			}
		}.start();
	}
	
	
	public void update(){
		Queue<ConsoleMessage> logMsgs = log.getConsoleMessage();
		while(!logMsgs.isEmpty()){
			ConsoleMessage msg = logMsgs.poll();
			showMessage(msg);
			//text.append(msg.toString());
			//text.append("\n");
			
			msgs.add(msg);
		}
		
		text.setSelectionStart(text.getText().length());
		text.setSelectionEnd(text.getSelectionStart());
		
		//changed = true;
	}
	
	private void showLeft(){
			Queue<ConsoleMessage> logMsgs = log.getConsoleMessage();
			while(!logMsgs.isEmpty()){
				showMessage(logMsgs.peek());
				msgs.add(logMsgs.poll());
			}
			while(msgs.size()>maxMsg)
				msgs.poll();
			changed = false;
	}
	
	private void showAll(){
		
		Queue<ConsoleMessage> logMsgs = log.getConsoleMessage();
		while(!logMsgs.isEmpty()){
			msgs.add(logMsgs.poll());
		}
		while(msgs.size()>maxMsg)
			msgs.poll();
		
		try {
			Document doc = text.getDocument();
			doc.remove(0, doc.getLength());
			
			
			
			for(ConsoleMessage msg:msgs)
				showMessage(msg);
			
			
			changed = false;
			
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	
	private void showMessage(ConsoleMessage msg){
		
		if(msg.type==ConsoleMessage.TYPE_CIRCLE && !circleCheck.isSelected())
			return;
		if(msg.type==ConsoleMessage.TYPE_ERROR && !errorCheck.isSelected())
			return;
		if(msg.type==ConsoleMessage.TYPE_LOG && !logCheck.isSelected())
			return;
		if(msg.type==ConsoleMessage.TYPE_INSTRUCTION && !instrCheck.isSelected())
			return;
		if(msg.type==ConsoleMessage.TYPE_WAIT && !waitCheck.isSelected())
			return;
		
		text.append(msg.toString());
		text.append("\n");
		//doc.insertString(doc.getLength(),msg.toString()+"\n",attrs[msg.type]);
		//text.setSelectionStart(doc.getLength());
	}
	
}
