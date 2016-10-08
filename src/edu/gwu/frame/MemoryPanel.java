package edu.gwu.frame;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import edu.gwu.common.Util;
import edu.gwu.core.MainMemory;
import edu.gwu.core.Setting;
import edu.gwu.core.basic.Word;

public class MemoryPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1819976278005742885L;

	
	private JScrollPane js;
	private JTable table;
	
	
	private TableModel model;
	
	public MemoryPanel(){
		TitledBorder nameTitle =new TitledBorder("Memory"); 	
		this.setBorder(nameTitle);
		
		this.setLayout(new BorderLayout());
		
		model = new TableModel();
		table = new JTable(model);

		DefaultTableCellRenderer d = new DefaultTableCellRenderer();
		d.setHorizontalAlignment(JLabel.CENTER);
		TableColumn col = table.getColumnModel().getColumn(0);
		table.getTableHeader().setResizingColumn(col);
		col.setWidth(35);
		col.setCellRenderer(d);
		
		
		col = table.getColumnModel().getColumn(1);
		table.getTableHeader().setResizingColumn(col);
		col.setCellRenderer(d);
		col.setWidth(150);
		
		col = table.getColumnModel().getColumn(2);
		table.getTableHeader().setResizingColumn(col);
		col.setCellRenderer(d);
		col.setWidth(60);

		table.setEnabled(false);
		
		
		//updated();
		js = new JScrollPane(table);
				
		this.add(js,BorderLayout.CENTER);
	}
	
	class TableModel extends AbstractTableModel{
		Word[] data = MainMemory.getInstance().getMemData();
		String[] title ={ "Addr", "Binary Value","int","c","float"};
		/**
		 * 
		 */
		private static final long serialVersionUID = -4756729938276291370L;

		@Override
		public String getColumnName(int column) {
			return title[column];
		}

		@Override
		public int getRowCount() {
			return Setting.MEM_WORD_LENGTH;
		}

		@Override
		public int getColumnCount() {
			return 5;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Word word = data[rowIndex];
			switch(columnIndex){
			case(0):
				return rowIndex;
			case(1):
				return Util.getBinaryStringFromBitsNoBlank(word.getData());
			case(2):
				return word.intValue();
			case(3):
				int value = word.intValue();
				if(value>0&&value<178)
					return (char)value;
				else
					return "";
			case(4):
				return word.floatValue();
			default:
				return "";
			}
		}
		
	}
}
