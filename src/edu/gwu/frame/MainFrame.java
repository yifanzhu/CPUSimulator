package edu.gwu.frame;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import edu.gwu.common.MVCMsg;
import edu.gwu.core.CPU;
import edu.gwu.core.Cache;
import edu.gwu.core.Log;
import edu.gwu.core.MainMemory;
import edu.gwu.core.basic.ConsoleMessage;
import edu.gwu.core.io.IODeviceController;

/**
 * 
 * @author 
 *
 */
public class MainFrame extends JFrame implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9058808062307751300L;
	
	
	private CodeLoader codeLoaderPanel;
	
	private JPanel actionPanel;
	
	private ConsolePanel consolePanel;
	
	private RegisterTableViewPanel registerViewPanel;
	private RegisterItemViewPanel registerItemPanel;
	
	private FileDevicePanel fileDevicePanel;
	
	private MemoryPanel memoryPanel;
	private KeyboardPanel keyboardPanel;
	
	/**
	 * the panel to show cache information
	 */
	private CachePanel cachePanel;
	private PrinterPanel printerPanel;
	
	private JButton powerOnButton,powerOffButton;
	
	private FlashingButton runSingleStep;
	
	private StatusIndicator statusIndicator;
	
	private SimplePipelinePanel pipelinePanel;
	
	private JRadioButton radioModeRun;
    private JRadioButton radioModeDebugInstr;
    private JRadioButton radioModeDebugCycle;
    
    
	public MainFrame(){
		super("CPU Simulator");
		
		this.setSize(865,686);
		this.setLocation(200, 120);
		
		this.setLayout(null);
		
		codeLoaderPanel = new CodeLoader();
		codeLoaderPanel.setBounds(5, 0, 480, 200);
		this.add(codeLoaderPanel);
		
		consolePanel = new ConsolePanel();
		consolePanel.setBounds(5, 205, 360, 250);
		
		actionPanel = new JPanel();
		actionPanel.setBorder(new TitledBorder("Action"));
		Box box = Box.createVerticalBox();
		powerOnButton = new JButton("Power On");
		powerOffButton = new JButton("Power Off");
		powerOffButton.setVisible(false);
		statusIndicator = new StatusIndicator();
		
		JPanel powerAction = new JPanel();
		//powerAction.setLayout(null);
		//powerAction.setPreferredSize(new Dimension(130,30));
		powerAction.setSize(130, 50);
		
		powerAction.add(powerOnButton);
		powerOnButton.setBounds(0, 0, 60, 30);
		powerAction.add(powerOffButton);
		powerAction.add(statusIndicator);
		actionPanel.add(powerAction);
		
		radioModeRun = new JRadioButton("Run");
		radioModeRun.setSelected(true); 
		radioModeDebugInstr = new JRadioButton("Debug(SingleStep)");
		radioModeDebugCycle = new JRadioButton("Debug(SingleCycle)");
		
		ButtonGroup group = new ButtonGroup();
		group.add(radioModeRun);
		group.add(radioModeDebugInstr);
		group.add(radioModeDebugCycle);
		box.add(radioModeRun);
		box.add(radioModeDebugInstr);
		box.add(radioModeDebugCycle);
		
		//reset.setVisible(false);
		
		
		
		//box.add(run);
		//box.add(runSingleStep);
		
		
		actionPanel.add(box);
		runSingleStep = new FlashingButton("Continue");
		runSingleStep.setPreferredSize(new Dimension(120,30));
		runSingleStep.setVisible(false);
		actionPanel.add(runSingleStep);
		
		actionPanel.setBounds(490, 0, 155, 200);
		this.add(actionPanel);
		
		this.add(consolePanel);
		
		registerViewPanel = new RegisterTableViewPanel();
		registerViewPanel.setBounds(405, 460, 245, 200);
		this.add(registerViewPanel);
		
		registerItemPanel = new RegisterItemViewPanel();
		registerItemPanel.setBounds(405, 460, 245, 200);
		this.add(registerItemPanel);
		registerViewPanel.setVisible(false);
		
		
		fileDevicePanel = new FileDevicePanel(3);
		fileDevicePanel.setBounds(365, 210, 285, 80);
		this.add(fileDevicePanel);
		
		FileDevicePanel file4 = new FileDevicePanel(4);
		file4.setBounds(365, 295, 285, 80);
		this.add(file4);
		
		FileDevicePanel file5 = new FileDevicePanel(5);
		file5.setBounds(365, 380, 285, 80);
		this.add(file5);
		
		memoryPanel = new MemoryPanel();
		memoryPanel.setBounds(5, 460, 400, 200);
		this.add(memoryPanel);
		
	
		cachePanel = new CachePanel();
		cachePanel.setBounds(650, 415, 211, 245);
		this.add(cachePanel);
		

		keyboardPanel = new KeyboardPanel();
		keyboardPanel.setBounds(650, 0, 211, 200);
		keyboardPanel.setPreferredSize(new Dimension(211,200));
    	add(keyboardPanel);
		
    	printerPanel = new PrinterPanel();
    	printerPanel.setBounds(650, 205, 211, 125);
    	add(printerPanel);

    	pipelinePanel = new SimplePipelinePanel();
    	pipelinePanel.setBounds(650, 330, 211, 85);
    	add(pipelinePanel);
    	
		this.setVisible(true);
		//this.pack();
		this.setResizable(false);
		this.addListener();
		
		
		
		//observe CPU
		CPU.getInstance().addObserver(this);
		
		CPU.getInstance().addObserver(pipelinePanel);
		
	}
	
	private void addListener(){
		powerOnButton.addMouseListener(
				new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent e) {
						CPU.getInstance().powerOn();
					}

					@Override
					public void mousePressed(MouseEvent e) {
						
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						
					}
					
				});
		
		runSingleStep.addMouseListener(
				new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent e) {
						CPU.getInstance().continueRun(CPU.SIGNAL_DEBUG);
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
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				});
		
		radioModeRun.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						if(radioModeRun.isSelected())
							CPU.getInstance().setRunMode(CPU.MODE_RUN);
					}
				});
		radioModeDebugInstr.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						if(radioModeDebugInstr.isSelected())
							CPU.getInstance().setRunMode(CPU.MODE_DEBUG_INSTRUCTION);
					}
				});
		radioModeDebugCycle.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						if(radioModeDebugCycle.isSelected())
							CPU.getInstance().setRunMode(CPU.MODE_DEBUG_CIRCLE);
					}
				});
		
		powerOffButton.addActionListener(
				new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						MainMemory.getInstance().clear();
						Cache.getInstance().clear();
						CPU.getInstance().powerOff();
						IODeviceController.getInstance().resetAll();
						printerPanel.clearText();
						Log.getInstance().console(ConsoleMessage.TYPE_LOG,"Reset registers,memory,cache");
					}
					
				});
		
		this.addWindowListener(
				new WindowAdapter(){

					@Override
					public void windowClosing(WindowEvent e) {
						codeLoaderPanel.saveEditorContentIntoBackupFile();
					}
					
				});
		
	}
	
	public static void main(String[] args){
		try {
			
			if(System.getProperty("os.name").contains("Windows")){
				String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
				
				UIManager.setLookAndFeel(lookAndFeel);
			}
			//for(Object key:System.getProperties().keySet())
				//System.out.println(key);
			
			
			//SwingUtilities.updateComponentTreeUI(frame);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		
		
		
		Log log = Log.getInstance();
		log.addObserver(frame);
		
		
	}

	@Override
	public void update(Observable o, Object arg) {
		
		
		
		//if(msg.equals())
		
		
		
		if(arg == null){
			consolePanel.update();
			//memoryPanel.update();
			//registerViewPanel.update();
			registerItemPanel.update();
			cachePanel.update();
			//printerPanel.update();
		}else{
			int msg = (Integer)arg;
			
			switch(msg){
			
			case(MVCMsg.MSG_CIRCLE_FINISH):
				registerItemPanel.updateState();
				break;

			case(MVCMsg.MSG_POWERON):
				powerOn();
				break;
			
			case(MVCMsg.MSG_POWEROFF):
				powerOff();
				break;
				
			case(MVCMsg.MSG_CPU_DEBUG_WAITING):
				runSingleStep.setVisible(true);
				runSingleStep.startFlash();
				break;
			
			case(MVCMsg.MSG_CPU_DEBUG_CONTINUE):
				runSingleStep.setVisible(false);
				runSingleStep.stopFlash();
				break;
			default:
				
				break;
			}
			
		}
		
		
		
		
		
		
		

		
	}
	
	private void powerOn(){
		powerOffButton.setVisible(true);
		powerOnButton.setVisible(false);
		statusIndicator.powerOn();
	}
	private void powerOff(){
		powerOnButton.setVisible(true);
		powerOffButton.setVisible(false);
		statusIndicator.powerOff();
		runSingleStep.setVisible(false);
		runSingleStep.stopFlash();
		registerItemPanel.updateState();
		registerItemPanel.updateState();
		keyboardPanel.stopFlash();
	}
}
