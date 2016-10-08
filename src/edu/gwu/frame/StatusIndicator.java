package edu.gwu.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class StatusIndicator extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3362094260658059777L;

	private Color offColor = Color.gray;
	private Color onColor = Color.green;
	
	
	private Color state = offColor;
	
	
	
	public StatusIndicator(){
		
		super();
		
		this.setPreferredSize(new Dimension(15,15));
	}



	@Override
	public void paint(Graphics g) {
		Dimension size = this.getPreferredSize();
		g.setColor(state);
		g.fillOval(1, 1, size.width-2, size.height-2);
		g.setColor(Color.gray);
		g.drawOval(1, 1, size.width-2, size.height-2);
		//super.paint(g);
	}


	public void powerOn(){
		state = onColor;
		repaint();
	}
	
	public void powerOff(){
		state = offColor;
		repaint();
	}

	
}
