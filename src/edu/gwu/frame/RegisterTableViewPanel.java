package edu.gwu.frame;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import edu.gwu.common.Util;
import edu.gwu.core.CPU;
import edu.gwu.core.Register;

public class RegisterTableViewPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1639344321590175320L;

	
	private CPU cpu = CPU.getInstance();
	private JScrollPane js;
	private JTable table;
	
	/**
	 * 
	 */
	public RegisterTableViewPanel(){
		
		TitledBorder nameTitle =new TitledBorder("Registers"); 	
		this.setBorder(nameTitle);
		
		this.setLayout(new BorderLayout());
		
		
		table = new RegisterTable(cpu.getRegisterSize(),3);
		String[] tableHeads = new String[] { "Register", "Binary Value", "Decimal Value"};
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setColumnIdentifiers(tableHeads);

		TableColumn col = table.getColumnModel().getColumn(0);
		table.getTableHeader().setResizingColumn(col);
		col.setWidth(60);
		col = table.getColumnModel().getColumn(1);
		//col.
		table.getTableHeader().setResizingColumn(col);
		col.setWidth(160);

		table.addMouseListener(
				new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent e) {
						
						//check if it's double click
						if(e.getClickCount()>1){
							//JOptionPane.showInputDialog("hi");
							int row = (table.rowAtPoint(e.getPoint()));
							int column = (table.columnAtPoint(e.getPoint()));
							
							//which register is being editing
							String registerName = String.valueOf(table.getValueAt(row, 0));
							Register register = cpu.getRegisters().get(registerName);
							
							//how many bits does that register has
							int bitSize = register.getSize();
							int maxValue = (int)Math.pow(2, bitSize)-1;
							
							//which type of value are editing
							String msg = null;
							if(column==1){
								msg = "Please input a new value(binary) for"+registerName;
							}
							else if(column==2){
								msg = "Please input a new value(decimal) for "+registerName;
							}
							if(msg!=null){
								try {
									//get the input string
									String outValue = JOptionPane.showInputDialog(null,msg,
											"Edit Register",JOptionPane.PLAIN_MESSAGE);
									//turn into decimal value
									int decimalValue = 0;
									if(column==1)
										decimalValue = Util.getIntValueFromBits(Util.getBitsFromBinaryString(outValue));
									else
										decimalValue = Integer.parseInt(outValue);
									
									//check if value if legal
									if(decimalValue>=0&&decimalValue<=maxValue){
										//set register and update panel
										cpu.getRegisters().get(registerName).setValueByInt(decimalValue);
										update();
									}
								} catch (HeadlessException e1) {
									//e1.printStackTrace();
								} catch (NumberFormatException e1) {
									//e1.printStackTrace();
								}
								
							}
						}
						
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
		
//		table.addKeyListener(
//				new KeyListener(){
//
//					@Override
//					public void keyTyped(KeyEvent e) {
//						System.out.println(e.getKeyCode());
//						
//					}
//
//					@Override
//					public void keyPressed(KeyEvent e) {
//						// TODO Auto-generated method stub
//						
//					}
//
//					@Override
//					public void keyReleased(KeyEvent e) {
//						// TODO Auto-generated method stub
//						
//					}
//					
//				});
		table.getModel().addTableModelListener(
				new TableModelListener(){

					@Override
					public void tableChanged(TableModelEvent e) {
						//System.out.println("e");
					}
					
				});
//		table.getColumnModel().getColumn(2).getCellEditor().addCellEditorListener(
//				new CellEditorListener(){
//					@Override
//					public void editingStopped(ChangeEvent e) {
//						System.out.println("stopped");
//					}
//
//					@Override
//					public void editingCanceled(ChangeEvent e) {
//						System.out.println("canceled");
//					}
//					
//				});
		//table.setEnabled(false);
		
		
		
		update();
		js = new JScrollPane(table);
				
		this.add(js,BorderLayout.CENTER);
	}
	
	
	public void update(){
		Map<String,Register> regs = cpu.getRegisters();
		int i = 0;
		for(String key:cpu.getRegisterNames()){
			table.setValueAt(key, i, 0);
			Register reg = regs.get(key);
			table.setValueAt(reg.getBinaryString(), i, 1);
			table.setValueAt(reg.intValue(), i, 2);
			i++;
		}
	}
	
	class RegisterTable extends JTable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5136571063471054001L;

		public RegisterTable() {
			super();
		}

		public RegisterTable(int rows, int cols) {
			super(rows, cols);
		}

		/**
		 * set the first column not editable (Register title)
		 */
		public boolean isCellEditable(int row, int column) {
			return false;
		}

	}
}
