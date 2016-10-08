package edu.gwu.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.gwu.core.CPU;
import edu.gwu.core.Register;

public class RegisterItemPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 665657414676323304L;

	private String name;
	private JLabel labelName;
	private Register reg;
	private JLabel labelValue;
	
	private int intValue;
	private boolean modifiedFlag = false;
	
	private Color selected = new Color(20,147,122);
	private Color unselected = new Color(28,205,170);
	private Color modified = Color.yellow;
	
	private Color bgColor = unselected;
	
	public String getRegisterName(){
		return this.name;
	}
	
	public RegisterItemPanel(String name){
		//super(name);
		this.name = name;
		this.reg = CPU.getInstance().getRegisters().get(name);
		
		intValue = reg.intValue();
		labelName = new JLabel(name+" ("+reg.intValue()+")");
		this.add(labelName);
		resetToolTipText();
		
		labelValue = new JLabel(reg.getBinaryString());
		//this.add(labelValue);
		labelValue.setVisible(false);
		
		this.setLayout(new GridLayout(1,1,4,1));
		
		this.setSize(150, 30);
		//this.setBackground(Color.CYAN);
		
		
		this.addMouseListener(
				new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						highlight();
					}

					@Override
					public void mouseExited(MouseEvent e) {
						deHighlight();
					}
					
				});
	}
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		
		Dimension size = this.getSize();
		
		g.setColor(bgColor);
		g.fillRoundRect(0, 0, size.width, size.height, 4, 4);
		g.setColor(Color.gray);
		g.drawRoundRect(0, 0, size.width, size.height, 6, 6);
		//super.paintComponent(g);
	}



	private void highlight(){
		//this.setBackground(new Color(20,147,122));
		bgColor = selected;
		//if(modifiedFlag)
		//	bgColor = modified;
		repaint();
	}
	
	private void deHighlight(){
		//this.setBackground(new Color(28,205,170));
		bgColor = unselected;
		if(modifiedFlag)
			bgColor = modified;
		repaint();
	}
	
	public void update(){
		
		labelName.setText(name+" ("+reg.intValue()+")");
		resetToolTipText();
		
		
		super.repaint();
	}



	public void updateState() {

		int value = reg.intValue();
		
			if(intValue!=value){
				modifiedFlag = true;
				intValue = value;
				bgColor = modified;
			}else{
				modifiedFlag = false;
				bgColor = unselected;
			}
			
			if(name.equals("PC")){
				//System.out.println("update item:"+modifiedFlag+" "+value);
			}
		
		
		labelName.setText(name+" ("+intValue+")");
		resetToolTipText();
		
		super.repaint();
	}
	
	private void resetToolTipText(){
		this.setToolTipText(labelName.getText()+"["+reg.getBinaryString()+"]");
	}
}
