package edu.gwu.frame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.gwu.core.io.FileDevice;
import edu.gwu.core.io.IODeviceController;
import edu.gwu.exception.IllegalDeviceStatusException;

public class FileDevicePanel extends JPanel implements Observer{

	
	private int devid;
	
	private FileDevice fileDevice;
	
	private JTextField fileLocation;
	private JButton changeBtn;
	private JButton resetBtn;
	
	private JLabel availableLabel;
	private JLabel availableTitle;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8109231785169972365L;

	
	public FileDevicePanel(int devId){
		
		this.devid = devId;
		fileDevice = IODeviceController.getInstance().getFileDevice(devid);
		fileDevice.addObserver(this);
		
		
		TitledBorder nameTitle =new TitledBorder("File IO (DevID:"+devid+",Type:"+(fileDevice.isInputFile()?"INPUT":"OUTPUT")+")"); 	
		this.setBorder(nameTitle);
		
		this.setLayout(new GridLayout(2,1,0,0));
		
		fileLocation = new JTextField(fileDevice.getFile().getPath());
		
		
		
		this.add(fileLocation);
		
		changeBtn = new JButton("Change");
		changeBtn.setPreferredSize(new Dimension(70,25));
		resetBtn = new JButton("Reset");
		resetBtn.setPreferredSize(new Dimension(70,25));
		
		JPanel btns = new JPanel();
		btns.setLayout(new FlowLayout(FlowLayout.RIGHT,0,0));
		
		if(fileDevice.isInputFile()){
			availableTitle = new JLabel("Available Words:");
			btns.add(availableTitle);
			availableLabel = new JLabel(String.valueOf(fileDevice.getAvailableSize()));
			btns.add(availableLabel);
		}
		
		
		
		
		btns.add(changeBtn);
		btns.add(resetBtn);
		
		
		this.add(btns);
		
		
		
		addEventListener();
	}
	
	
	private void addEventListener() {
		
		changeBtn.addActionListener(
				new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						JFileChooser fileChooser = new JFileChooser(fileDevice.getFile());
						FileNameExtensionFilter ff = new FileNameExtensionFilter( ".txt(as io device source)", "txt");  
						fileChooser.setFileFilter(ff);
						int option = fileChooser.showOpenDialog(null);
						
						if(option == JFileChooser.APPROVE_OPTION){
							File file = fileChooser.getSelectedFile();
							fileDevice.setFile(file);
							fileLocation.setText(file.getPath());
							//System.out.println(file.getAbsolutePath());
						}
						
					}
					
				});
		
		resetBtn.addActionListener(
				new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {

						try {
							fileDevice.reset();;
							//fileLocation.setText(file.getPath());
							if(fileDevice.isInputFile())
								availableLabel.setText(String.valueOf(fileDevice.getAvailableSize()));
						} catch (IllegalDeviceStatusException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
					
				});
		
	}


	@Override
	public void update(Observable o, Object arg) {
		if(fileDevice.isInputFile()){
			availableLabel.setText(String.valueOf(fileDevice.getAvailableSize()));
		}
	}


}
