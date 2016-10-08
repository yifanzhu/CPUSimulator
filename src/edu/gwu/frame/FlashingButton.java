package edu.gwu.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class FlashingButton extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6126088928261739984L;
	
	
	private JLabel textLabel;
	private boolean flashing = false;
	private int degree = 0;
	private int increase = 1;
	private int MAX = 20;
	
	private Thread runningThread;
	
	public FlashingButton(String text){
	
		textLabel = new JLabel(text,JLabel.CENTER);

		this.setLayout(new BorderLayout());
		this.add(textLabel,BorderLayout.CENTER);
		
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	
	public void startFlash(){
		flashing = true;
		
		if(runningThread==null||!runningThread.isAlive()){
			runningThread = new Thread(){
				@Override
				public void run(){
					while(flashing){
						repaint();
						try {
							Thread.sleep(50);
							degree+=increase;
							if(degree>=MAX)
								increase = -1;
							if(degree<=0)
								increase = 1;
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					runningThread = null;
				}
			};
			runningThread.start();
		}
		//if(runningThread.isInterrupted())
		//if(!runningThread.isAlive())
		//runningThread.start();
		//runningThread = null;
	}
	
	public void stopFlash(){
		flashing = false;
	}
	
	@Override
	protected void paintComponent(Graphics g) {

		
		Dimension size = this.getSize();
		
		g.setColor(new Color(20+8*degree,150+5*degree,20+5*degree));
		g.fillRoundRect(0, 0, size.width-1, size.height-1, 4, 4);
		g.setColor(Color.gray);
		g.drawRoundRect(0, 0, size.width-1, size.height-1, 6, 6);
		
		this.paintComponents(g);
	}
	
	
	
}
