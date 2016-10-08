package edu.gwu.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import edu.gwu.common.Util;
import edu.gwu.core.CPU;
import edu.gwu.core.Log;
import edu.gwu.core.Register;
import edu.gwu.core.basic.ConsoleMessage;

public class RegisterItemViewPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4312495536712333943L;
	private CPU cpu = CPU.getInstance();
	
	private JPanel items;
	private JPanel editor;
	private JLabel labelText;
	private JTextField intText,binaryText;
	private JButton modifyButton;
	private List<RegisterItemPanel> panelItems;
	
	public RegisterItemViewPanel(){
		TitledBorder nameTitle =new TitledBorder("Registers"); 	
		this.setBorder(nameTitle);
		this.setLayout(new BorderLayout());
		List<String> regs = cpu.getRegisterNames();
		
		editor = new JPanel();
		editor.setLayout(new FlowLayout(FlowLayout.LEFT,0, 0));
		//editor.setBackground(Color.red);
		this.add(editor,BorderLayout.NORTH);
		labelText = new JLabel("<REG>");
		labelText.setPreferredSize(new Dimension(40,30));
		intText = new JTextField(3);
		binaryText = new JTextField(7);
		modifyButton = new JButton("OK");
		modifyButton.setPreferredSize(new Dimension(40,20));
		editor.add(labelText);
		editor.add(intText);
		editor.add(binaryText);
		editor.add(modifyButton);
		
		items = new JPanel();
		items.setLayout(new GridLayout(8,4,3,1));
		this.add(items,BorderLayout.CENTER);
		
		
		panelItems = new ArrayList<RegisterItemPanel>();
		for(String name:regs){
			RegisterItemPanel item = new RegisterItemPanel(name);
			item.addMouseListener(
					new MouseListener(){

						@Override
						public void mouseClicked(MouseEvent e) {
							RegisterItemPanel item = (RegisterItemPanel)e.getSource();
							modifyingRegister(item.getRegisterName());
							//System.out.println(item.getRegisterName());;
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
			items.add(item);
			panelItems.add(item);
			//System.out.println(name);
		}
		
		intText.addKeyListener(
				new KeyAdapter(){

					@Override
					public void keyReleased(KeyEvent e) {
						Register reg = CPU.getInstance().getRegisters().get(labelText.getText());
						if(reg==null)
							return;
						int value;
						try {
							value = Integer.parseInt(intText.getText());
							binaryText.setText(Util.getBinaryStringFromIntValue(value, reg.getSize()));
						} catch (NumberFormatException e1) {
							e1.printStackTrace();
						}
					}
				});
		
		binaryText.addKeyListener(
				new KeyAdapter(){

					@Override
					public void keyReleased(KeyEvent e) {
						Register reg = CPU.getInstance().getRegisters().get(labelText.getText());
						if(reg==null)
							return;
						try {
							int data[] = Util.getBitsFromBinaryString(binaryText.getText());
							intText.setText(String.valueOf(Util.getIntValueFromBits(data)));
						} catch (NumberFormatException e1) {
							e1.printStackTrace();
						}
					}
				});
		modifyButton.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						Register reg = CPU.getInstance().getRegisters().get(labelText.getText());
						if(reg==null)
							return;
						try {
							int value = Integer.parseInt(intText.getText());
							reg.setValueByInt(value);
							Log.getInstance().console(ConsoleMessage.TYPE_LOG, "set "+labelText.getText()+" to "+value);
						} catch (NumberFormatException e1) {
							e1.printStackTrace();
						}
					}
				});
		//this.setLayout(new BorderLayout());
	}

	private void modifyingRegister(String name){
		labelText.setText(name);
		Register reg = CPU.getInstance().getRegisters().get(name);
		intText.setText(String.valueOf(reg.intValue()));
		binaryText.setText(reg.getBinaryString());
	}

	public void update() {
		for(RegisterItemPanel item:panelItems)
			item.update();
	}


	public void updateState() {
		for(RegisterItemPanel item:panelItems)
			item.updateState();
	}
	
}
