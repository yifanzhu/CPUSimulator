package edu.gwu.frame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import edu.gwu.common.MVCMsg;
import edu.gwu.core.CPU;

/**
 * Panel for Presentation for Simple Pipeline
 * @author yangmang
 *
 */
public class SimplePipelinePanel extends JPanel implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8244264703614161598L;

	private int pc = -1;
	private int sx = 7;
	private int sy = 19;
	private int cellH = 12;
	private int cellW = 22;
	
	private int padding = 3;
	private int fontSize = 11;
	
	/**
	 * color for next line
	 */
	private static Color nextColor = new Color(130,130,255);
	private static Color refillColor = new Color(255,130,130);
	
	private Color firstLine = Color.green;
	private Color nextLine = nextColor;
	
	private boolean refillFlag = false;
	
	
	private void setState(int next, int stage){
		if(next!=pc&&next!=pc+1){
			refillFlag = true;
			pc = next;
		}else{
			refillFlag = false;
			pc = next;
		}
		repaint();
	}
	
	public SimplePipelinePanel(){
		TitledBorder nameTitle =new TitledBorder("SimplePipeLine"); 	
		this.setBorder(nameTitle);
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(refillFlag){
			nextLine = refillColor;
		}else{
			nextLine = nextColor;
		}
		
		g.setFont(getFont().deriveFont(10f));
		g.setColor(firstLine);
		g.fillRect(sx, sy, cellW*5, cellH);
		
		g.setColor(nextLine);
		g.fillRect(sx+cellW, sy+cellH, cellW*5, cellH);
		g.fillRect(sx+cellW*2, sy+cellH*2, cellW*5, cellH);
		g.fillRect(sx+cellW*3, sy+cellH*3, cellW*5, cellH);
		g.fillRect(sx+cellW*4, sy+cellH*4, cellW*5, cellH);
		
		g.setColor(Color.black);
		
		g.drawRect(sx, sy, cellW*5, cellH);
		g.drawRect(sx+cellW, sy, cellW*3, cellH);
		g.drawRect(sx+cellW*2, sy, cellW, cellH);
		
		
		g.drawRect(sx+cellW, sy+cellH, cellW*5, cellH);
		g.drawRect(sx+cellW*2, sy+cellH, cellW*3, cellH);
		g.drawRect(sx+cellW*3, sy+cellH, cellW, cellH);
		
		g.drawRect(sx+cellW*2, sy+cellH*2, cellW*5, cellH);
		g.drawRect(sx+cellW*3, sy+cellH*2, cellW*3, cellH);
		g.drawRect(sx+cellW*4, sy+cellH*2, cellW, cellH);
		
		g.drawRect(sx+cellW*3, sy+cellH*3, cellW*5, cellH);
		g.drawRect(sx+cellW*4, sy+cellH*3, cellW*3, cellH);
		g.drawRect(sx+cellW*5, sy+cellH*3, cellW, cellH);
		
		g.drawRect(sx+cellW*4, sy+cellH*4, cellW*5, cellH);
		g.drawRect(sx+cellW*5, sy+cellH*4, cellW*3, cellH);
		g.drawRect(sx+cellW*6, sy+cellH*4, cellW, cellH);
		
		
		
		for(int i=0;i<5;i++){
			g.drawString("IF", sx+padding + i*cellW, sy+fontSize+i*cellH);
			g.drawString("ID", sx+padding + (i+1)*cellW, sy+fontSize+i*cellH);
			g.drawString("EX", sx+padding + (i+2)*cellW, sy+fontSize+i*cellH);
			g.drawString("ME", sx+padding + (i+3)*cellW, sy+fontSize+i*cellH);
			g.drawString("WB", sx+padding + (i+4)*cellW, sy+fontSize+i*cellH);
			
			if(pc>=0){
				g.drawString("PC="+(pc+i), 
						sx+padding+(i+5)*cellW-((i>1)?(cellW*7):0), 
						sy+fontSize+i*cellH-1);
				
			}
		}
		
		
		
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg==null)
			return;
		
		int msg = (Integer)arg;
		
		if(msg==MVCMsg.MSG_CPU_PIPELINE){
			this.setState(CPU.getInstance().getRegisters().get("PC").intValue(), 0);
		}
	}
	
}
