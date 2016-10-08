package edu.gwu.frame;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import edu.gwu.common.Util;
import edu.gwu.core.basic.Word;
import edu.gwu.core.io.IODevice;
import edu.gwu.core.io.IODeviceController;

public class PrinterPanel extends JPanel implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6614008801609108682L;
	JTextArea text = new JTextArea();
	private IODevice printer;
	
	
	public PrinterPanel() {
		TitledBorder nameTitle = new TitledBorder("Printer (DEVID:1)");
		printer = IODeviceController.getInstance().getPrinter();
		this.setBorder(nameTitle);

		this.setLayout(new GridLayout());

		text = new JTextArea();
		text.setBounds(650, 205, 211, 155);
		text.setBackground(Color.black);
		text.setForeground(Color.white);
		text.setEditable(false);
		text.setLineWrap(true);
		//
		printer.addObserver(this);
		
		// this.add(text);
		JScrollPane js = new JScrollPane(text);
		js.setBackground(Color.black);
		this.add(js);
		
	}

	private void append(int value) {
		text.append(String.valueOf((char)(value)));
		text.setSelectionStart(text.getText().length());
	}


	public void clearText() {
		text.setText(Util.STR_BLANK);
	}

	@Override
	public void update(Observable o, Object msg) {
		//System.out.println("UPDATE:"+msg);
		
		Word word = (Word)msg;
		
		append(word.intValue());
	}
	
	
}
