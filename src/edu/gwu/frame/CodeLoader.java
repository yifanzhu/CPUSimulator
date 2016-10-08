package edu.gwu.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

import edu.gwu.common.MVCMsg;
import edu.gwu.core.CPU;
import edu.gwu.core.ProgramCompiler;
import edu.gwu.core.ROMLoader;
import edu.gwu.core.basic.Word;
import edu.gwu.core.io.IODeviceController;

public class CodeLoader extends JPanel implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7881020205292583683L;

	
	
	private ROMLoader romLoader = ROMLoader.getInstance();
	
	
	private JTabbedPane tab;
	
	/**
	 * Tab: boot program options
	 */
	private JButton restoreBootButton,setResevedMemoryButton;
	
	/**
	 * Tab: user program
	 */
	private JRadioButton userSourceEditor,userSourceP1,userSourceP2,userPart4;
	
	/**
	 * code edit panel for boot and user program
	 */
	private CodeEditPanel userProgram,bootProgram;
	
	/**
	 * confirm button
	 */
	private FlashingButton confirmSourceBtn;
	/**
	 * save the codes in editor
	 */
	private List<String> editorContent;
	
	/**
	 * which program is been shown under user program editor
	 */
	private int currentProgram = PRO_0;
	
	/**
	 * Editor program
	 */
	public static final int PRO_0 = 0;
	/**
	 * Program 1
	 */
	public static final int PRO_1 = 1;
	/**
	 * Program 2
	 */
	public static final int PRO_2 = 2;
	/**
	 * Part 4
	 */
	public static final int PRO_P4 = 4;
	
	
	public CodeLoader(){
		//TitledBorder nameTitle =new TitledBorder("Code Editor"); 	
		//this.setBorder(nameTitle);
		
		//let this panel observe CodeEditorDevice;
		IODeviceController.getInstance().getCodeEditorDevice().addObserver(this);
		
		tab = new JTabbedPane(JTabbedPane.TOP);
		
		
		this.setLayout(new GridLayout());

		//Boot program panel
		bootProgram = new CodeEditPanel(CPU.CPU_BOOT_START_LINE);
		restoreBootButton = new JButton("Restore");
		setResevedMemoryButton = new JButton("Setting");
		
		restoreBootButton.setEnabled(false);
		setResevedMemoryButton.setEnabled(false);
		
		JPanel bootOptionPanel = new JPanel();
		bootOptionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		bootOptionPanel.setPreferredSize(new Dimension(130,100));
		bootOptionPanel.add(restoreBootButton);
		bootOptionPanel.add(setResevedMemoryButton);
		
		
		
		JPanel bootPanel = new JPanel();
		bootPanel.setLayout(new BorderLayout());
		bootPanel.add(bootProgram,BorderLayout.CENTER);
		bootPanel.add(bootOptionPanel,BorderLayout.WEST);
		tab.add(bootPanel,"Boot Program");
		
		
		//User program panel
		userProgram = new CodeEditPanel(CPU.CPU_USER_START_LINE);
		userSourceEditor = new JRadioButton("Editor");
		userSourceP1 = new JRadioButton("Program 1");
		//userSourceP1.setEnabled(false);
		userSourceP2 = new JRadioButton("Program 2");
		
		
		userPart4 = new JRadioButton("Program P4");
		
		
		ButtonGroup userButtons = new ButtonGroup();
		userButtons.add(userSourceEditor);
		userButtons.add(userSourceP1);
		userButtons.add(userSourceP2);
		userButtons.add(userPart4);
		
		JPanel userButtonsPanel = new JPanel();
		userButtonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT,4,3));
		userButtonsPanel.setPreferredSize(new Dimension(130,90));
		userButtonsPanel.add(userSourceEditor);
		userButtonsPanel.add(userSourceP1);
		userButtonsPanel.add(userSourceP2);
		userButtonsPanel.add(userPart4);
		confirmSourceBtn = new FlashingButton("<html><body style='text-align:center'>Confirm<br>Program Source</body></html>");
		confirmSourceBtn.setPreferredSize(new Dimension(120,40));
		//confirmSourceBtn.startFlash();
		userButtonsPanel.add(confirmSourceBtn);
		
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new BorderLayout());
		userPanel.add(userProgram,BorderLayout.CENTER);
		userPanel.add(userButtonsPanel,BorderLayout.WEST);
		
		tab.add(userPanel,"User Program");
		tab.setSelectedIndex(1);
		
		
		this.setLayout(new BorderLayout());
		this.add(tab);
		
		
		//this.add(action,BorderLayout.EAST);
		
		this.addListener();
		//updateBinaryCode();
		
		editorContent = IODeviceController.getInstance().getCodeEditorDevice().readBackup();
		//userProgram.setCode(editorContent);
		
		
		userPart4.setSelected(true);
		userProgram.setCode(romLoader.getProgramPart4());
		currentProgram = PRO_P4;
		hideConfirmButton();
		update(null,null);
	}

	/**
	 * when current editor is showing user program (neither program1 nor program2)
	 * <br>codes should be saved before user click Program1 or Program2 radio button
	 */
	private void saveEditorContent(){
		editorContent.clear();
		BufferedReader reader = new BufferedReader(new StringReader(userProgram.getCodeText()));
		try {
			String line = reader.readLine();
			while(line!=null){
				editorContent.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * add action listener
	 */
	private void addListener(){
		userSourceEditor.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						userProgram.setCode(editorContent);
						currentProgram = PRO_0;
					}
				});
		userSourceP1.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						if(currentProgram == PRO_0)
							saveEditorContent();
						userProgram.setCode(romLoader.getProgram1());
						currentProgram = PRO_1;
					}
				});
		userSourceP2.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						if(currentProgram == PRO_0)
							saveEditorContent();
						userProgram.setCode(romLoader.getProgram2());
						currentProgram = PRO_2;
					}
				});
		userPart4.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						if(currentProgram == PRO_0)
							saveEditorContent();
						userProgram.setCode(romLoader.getProgramPart4());
						currentProgram = PRO_P4;
					}
				});
		
		
		confirmSourceBtn.addMouseListener(
				new MouseAdapter(){
					@Override
					public void mouseClicked(MouseEvent e) {
						prepareSource();
					}
				});
		
		
		
		restoreBootButton.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						//prepareSource();
						
					}
				});
		
		setResevedMemoryButton.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						//prepareSource();
					}
				});
		
		
	}

	/**
	 * set user program ready for BOOT program to copy into memory
	 */
	private void prepareSource(){
		
		//update binary code and check if there exist errors
		userProgram.updateBinaryCode();
		if(userProgram.hasError()){
			JOptionPane.showMessageDialog(null, "Errors exist in User Program, please check!",
					"Error Exists", JOptionPane.CANCEL_OPTION);
			return;
		}
		
		try {
			/**
			 * read every line in User Program
			 * compile to binary code
			 * fill it into device(31)
			 */
			List<Word>data = new ArrayList<Word>();
			BufferedReader reader = new BufferedReader(new StringReader(userProgram.getCodeText()));
			String line = reader.readLine();
			while(line!=null){
				data.add(new Word(ProgramCompiler.decodeInstruction(line)));
				line = reader.readLine();
			}
			IODeviceController.getInstance().getCodeEditorDevice().fillDeviceWithCodes(data);
			hideConfirmButton();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	

	@Override
	public void update(Observable o, Object arg) {
		
		if(arg==null){
			bootProgram.setCode(romLoader.getBootCodes());
		}
		else{
			
			int msg = (Integer)arg;
			switch(msg){
			case(MVCMsg.MSG_POWEROFF):
				hideConfirmButton();
				break;
			
			case(MVCMsg.MSG_USER_PROGRAM_CONFIRM):
				tab.setSelectedIndex(1);
				showConfirmButton();
				break;
				
			default:
				
				break;
			}
			
		}
		
	}
	
	/**
	 * show the Confirm Button
	 */
	private void showConfirmButton(){
		confirmSourceBtn.setVisible(true);
		confirmSourceBtn.startFlash();
	}
	
	/**
	 * hide the Confirm Button
	 */
	private void hideConfirmButton(){
		confirmSourceBtn.setVisible(false);
		confirmSourceBtn.stopFlash();
	}
	
	/**
	 * persist editing codes into a file
	 */
	public void saveEditorContentIntoBackupFile(){
		if(currentProgram == PRO_0)
			IODeviceController.getInstance().getCodeEditorDevice().backup(userProgram.getCodeText());
		else{
			IODeviceController.getInstance().getCodeEditorDevice().backup(editorContent);
		}
	}
}
