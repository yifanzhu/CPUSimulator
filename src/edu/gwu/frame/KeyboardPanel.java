package edu.gwu.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import edu.gwu.common.MVCMsg;
import edu.gwu.core.io.IODeviceController;
import edu.gwu.core.io.KeyboardDevice;

public class KeyboardPanel extends JPanel implements Observer{

	private static final long serialVersionUID = 5783683260444893043L;
    
	private JButton aB = new JButton("A");
	private JButton bB = new JButton("B");
	private JButton cB = new JButton("C");
	private JButton oneB = new JButton("1");
	private JButton twoB = new JButton("2");
	private JButton threeB = new JButton("3");
	private JButton fourB = new JButton("4");
	private JButton fiveB = new JButton("5");   
	private JButton sixB = new JButton("6");
	private JButton sevenB = new JButton("7");
	private JButton eightB = new JButton("8");
	private JButton nineB = new JButton("9");
	private JButton zeroB = new JButton("0"); 
	private JButton dotB = new JButton(".");
	private JButton enterB = new JButton("Enter");
	private JButton addB = new JButton("+"); 
	private JButton minusB = new JButton("-"); 
	private JButton divideB = new JButton("/");
	private JButton multiplyB = new JButton("*");
	
	
	
	private JCheckBox linkToKeyboard = new JCheckBox("Link to keyboard");
	
	private Font buttonFont = new Font("Times New Roman", Font.BOLD, 17);
	
	//private FlashingButton background;
	
	private KeyboardDevice keyboard = (KeyboardDevice)IODeviceController.getInstance().getKeyboard();
	
	private boolean flashing = false;
	private int degree = 0;
	private int increase = 1;
	private int MAX = 20;
	
	private Thread runningThread;
	
	public KeyboardPanel(){	
		
		keyboard.addObserver(this);
		
		TitledBorder nameTitle =new TitledBorder("Keyboard (DEVID:0)"); 	
		this.setBorder(nameTitle);
		
        this.setLayout(null);
        
        
        this.setOpaque(false);
        
        
        //
        //this.add(aB);
        aB.setBounds(5, 18, 50, 35);
        aB.setFont(buttonFont);
        aB.setEnabled(false);
        
       // this.add(bB);
        bB.setBounds(55, 18, 50, 35);
        bB.setFont(buttonFont);
        bB.setEnabled(false);
        
      //  this.add(cB);
        cB.setBounds(105, 18, 50, 35);
        cB.setFont(buttonFont);
        cB.setEnabled(false);
        
        
        linkToKeyboard.setBounds(5, 18, 150, 35);
        this.add(linkToKeyboard);
        linkToKeyboard.setOpaque(false);
        linkToKeyboard.addKeyListener(
        		new KeyListener(){

					@Override
					public void keyTyped(KeyEvent e) {
						//System.out.println("Keyboard typed: "+e.getKeyCode());
					}

					@Override
					public void keyPressed(KeyEvent e) {
						//System.out.println("Keyboard pressed: "+e.getKeyCode());
						
						
						int value = (int)e.getKeyChar();
						if(value>0&&value<128)
							keyboard.activeEvent(value);
					}

					@Override
					public void keyReleased(KeyEvent e) {
						//System.out.println("Keyboard released: "+e.getKeyCode());
					}
        			
        		});
        linkToKeyboard.addFocusListener(
        		new FocusListener(){

					@Override
					public void focusGained(FocusEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void focusLost(FocusEvent e) {
						linkToKeyboard.setSelected(false);
					}
        			
        		});
        
        this.add(addB);
        addB.setBounds(155, 18, 50, 35);
        addB.setFont(buttonFont);
        addB.setEnabled(false);
        addB.setOpaque(false);
        //
        this.add(oneB);
        oneB.setBounds(5, 53, 50, 35);
        oneB.setFont(buttonFont);       
        oneB.setOpaque(false);
        
        this.add(twoB);
        twoB.setBounds(55, 53, 50, 35);
        twoB.setFont(buttonFont);
        twoB.setOpaque(false);
        
        this.add(threeB);
        threeB.setBounds(105, 53, 50, 35);
        threeB.setFont(buttonFont);
        threeB.setOpaque(false);
        
        this.add(minusB);
        minusB.setBounds(155, 53, 50, 35);
        minusB.setFont(buttonFont);
        minusB.setEnabled(false);
        minusB.setOpaque(false);
        
        //
        this.add(fourB);
        fourB.setBounds(5, 88, 50, 35);
        fourB.setFont(buttonFont);
        fourB.setOpaque(false);
       
        this.add(fiveB);
        fiveB.setBounds(55, 88, 50, 35);
        fiveB.setFont(buttonFont);
        fiveB.setOpaque(false);
        
        this.add(sixB);
        sixB.setBounds(105, 88, 50, 35);
        sixB.setFont(buttonFont);
        sixB.setOpaque(false);
   
        this.add(multiplyB);
        multiplyB.setBounds(155, 88, 50, 35);
        multiplyB.setFont(buttonFont);
        multiplyB.setEnabled(false);
        multiplyB.setOpaque(false);
        //
        this.add(sevenB);
        sevenB.setBounds(5, 123, 50, 35);
        sevenB.setFont(buttonFont);
        sevenB.setOpaque(false);
        
        this.add(eightB);
        eightB.setBounds(55, 123, 50, 35);
        eightB.setFont(buttonFont);
        eightB.setOpaque(false);
        
        this.add(nineB);
        nineB.setBounds(105, 123, 50, 35);
        nineB.setFont(buttonFont);
        nineB.setOpaque(false);
        
        this.add(divideB);
        divideB.setBounds(155, 123, 50, 35);
        divideB.setFont(buttonFont); 
        divideB.setEnabled(false);
        divideB.setOpaque(false);
        //
        this.add(zeroB);
        zeroB.setBounds(5, 158, 50, 35);
        zeroB.setFont(buttonFont);   
        zeroB.setOpaque(false);
        
        this.add(dotB);
        dotB.setBounds(55, 158, 50, 35);
        dotB.setFont(buttonFont);
        dotB.setEnabled(false);
        dotB.setOpaque(false);
        
        this.add(enterB);
        enterB.setBounds(105, 158, 100, 35);
        enterB.setFont(buttonFont);
        enterB.setOpaque(false);
   
        aB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent f) {
            	keyboard.activeEvent(65);//65 is decimal ASCII value for A
            }
        });
        
        bB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent f) {
            	keyboard.activeEvent(66);//66 is decimal ASCII value for B
            }
        });
        
        cB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent f) {
            	keyboard.activeEvent(67);//67 is decimal ASCII value for C
            }
        });
        
        addB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent f) {
            	keyboard.activeEvent(43);//43 is decimal ASCII value for +
            }
        });
        
        oneB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent f) {
            	keyboard.activeEvent(49);//49 is decimal ASCII value for 1
            }
        });
        
        twoB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent f) {
            	keyboard.activeEvent(50);//50 is decimal ASCII value for 2
            }
        });
        
        threeB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent f) {
            	keyboard.activeEvent(51);//51 is decimal ASCII value for 3
            }
        });
        
        minusB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent f) {
            	keyboard.activeEvent(45);//45 is decimal ASCII value for -
            }
        });
        
        fourB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent f) {
            	keyboard.activeEvent(52);//52 is decimal ASCII value for 4
            }
        });
        
        fiveB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent f) {
            	keyboard.activeEvent(53);//53 is decimal ASCII value for 5
            }
        });
        
        sixB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent f) {
            	keyboard.activeEvent(54);//54 is decimal ASCII value for 6
            }
        });
        
        multiplyB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent f) {
            	keyboard.activeEvent(42);//42 is decimal ASCII value for *
            }
        });
        
        sevenB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent f) {
            	keyboard.activeEvent(55);//55 is decimal ASCII value for 7
            }
        });
        
        eightB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent f) {
            	keyboard.activeEvent(56);//56 is decimal ASCII value for 8
            }
        });
        
        nineB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent f) {
            	keyboard.activeEvent(57);//57 is decimal ASCII value for 9
            }
        });
        
        divideB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent f) {
            	keyboard.activeEvent(47);//47 is decimal ASCII value for/
            }
        });
        
        zeroB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent f) {
            	keyboard.activeEvent(48);//30 is decimal ASCII value for 0
            }
        });
        
        dotB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent f) {
            	keyboard.activeEvent(46);//46 is decimal ASCII value for .
            }
        });
        
        enterB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent f) {
            	keyboard.activeEvent(10);//10 is decimal ASCII value for Enter
            }
        });
        
        
        
        
        //background = new FlashingButton("");
        //background.setBounds(5, 17, 200, 175);
        //background.startFlash();
        //this.add(background);
        
        //this.startFlash();
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
	}
	
	public void stopFlash(){
		flashing = false;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		
		//Dimension size = this.getSize();
		if(flashing){
			g.setColor(new Color(20+8*degree,150+5*degree,20+5*degree));
			g.fillRoundRect(5, 17, 200, 175, 4, 4);
			g.setColor(Color.gray);
			g.drawRoundRect(5, 17, 200, 175, 6, 6);
		}
		
		super.paintComponent(g);
		this.paintComponents(g);
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg==null){
			
		}else{
			int msg = (Integer)arg;
			switch(msg){
			case(MVCMsg.MSG_KEYBOARD_WAITING):
				this.startFlash();
			break;
			case(MVCMsg.MSG_KEYBOARD_PRESSED):
				this.stopFlash();
			break;
			}
		}
	}

}


