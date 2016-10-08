package edu.gwu.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import edu.gwu.common.Util;
import edu.gwu.core.ProgramCompiler;

public class CodeEditPanel extends JPanel{

	private int startLine = 0;
	
	private static int TYPE_NORMAL = 0;
	private static int TYPE_ERROR = 1;
	private static int TYPE_DEBUG = 2;
	private SimpleAttributeSet[] attrs;

	//private Map<Integer,Integer>lineStatusInfo;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1838598168369255574L;
	private JTextArea code;
	private JTextArea binCode;
	private JTextArea lineText;
	private JScrollPane js;
	
	
	public CodeEditPanel(int start){
		code = new JTextArea();
		//code.setBounds(0, 0, 400, 200);
		binCode = new JTextArea();
		//binCode.setBounds(0, 0, 400, 200);
		
		binCode.setBackground(new Color(230,230,230));
		binCode.setPreferredSize(new Dimension(155,100));
		lineText = new JTextArea();
		lineText.setBackground(new Color(193,220,250));
		lineText.setForeground(new Color(130,130,130));
		lineText.setEditable(false);
		lineText.setEnabled(false);
		lineText.setPreferredSize(new Dimension(30,100));
		
		JPanel inner = new JPanel();
		inner.setLayout(new BorderLayout());
		inner.setBackground(Color.red);
		inner.add(code,BorderLayout.CENTER);
		inner.add(binCode,BorderLayout.EAST);
		inner.add(lineText,BorderLayout.WEST);
		
		binCode.setEditable(false);
		js = new JScrollPane(inner);
		
		this.setLayout(new GridLayout(1,1,0,0));
		this.add(js);
		
		
		attrs = new SimpleAttributeSet[3];
		attrs[TYPE_NORMAL] = new SimpleAttributeSet();
		
		attrs[TYPE_DEBUG] = new SimpleAttributeSet();
		StyleConstants.setBackground(attrs[TYPE_DEBUG], new Color(192,218,167));
		
		attrs[TYPE_ERROR] = new SimpleAttributeSet();
		StyleConstants.setBackground(attrs[TYPE_ERROR], Color.red);
		
		//lineStatusInfo = new HashMap<Integer,Integer>();
		
		
		this.startLine = start;
		addListener();
		updateBinaryCode();
	}
	
//	private int getLineType(int line){
//		Integer type = lineStatusInfo.get(line);
//		if(type==null)
//			return TYPE_NORMAL;
//		else
//			return type;
//	}
	
	// private void clearLineType(){
	// lineStatusInfo.clear();
	// }
	//
	// private void insertLineType(int line,int type){
	// lineStatusInfo.put(line, type);
	// }
	
	private int lastScrollValue = 0;
	
	public void updateBinaryCode(){
		lastScrollValue = js.getVerticalScrollBar().getValue();
		//js.getVerticalScrollBar().get
		BufferedReader reader = new BufferedReader(new StringReader(code.getText()));
		try {
			//binCode.setText("");
			StringBuffer binBuf = new StringBuffer();
			lineText.setText("");
			
			
			
			
			//Document lineDoc = lineText.getDocument();
			String line = reader.readLine();
			int count = startLine;
			while(line!=null){
				
				int[] bin = ProgramCompiler.decodeInstruction(line);
				binBuf.append(Util.getBinaryStringFormBitsInInstructionFormat(bin));
				binBuf.append("\n");
				
				//lineDoc.insertString(lineDoc.getLength(), " "+count+"\n", attrs[getLineType(count)]);
				lineText.append(" "+count+"\n");
				count++;
				line = reader.readLine();
			}
			
			
			//Dimension binSize = binCode.getPreferredSize();
			//binSize.height = code.getPreferredSize().height;
			//binCode.setPreferredSize(binSize);
			//binCode.setBackground(Color.blue);
			binCode.setText(binBuf.toString());
			//js.getVerticalScrollBar().setValue(0);
			//binCode.append(Util.getBinaryStringFormBitsInInstructionFormat(null));
			//binCode.append("\n");
			//lineText.append(" "+count+"\n");
			//lineDoc.insertString(lineDoc.getLength(), " "+count+"\n", null);
			
			//System.out.println(code.getSelectionStart());
			//code.setSelectionStart(code.getSelectionStart());
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			
			new Thread(){
				public void run(){
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					js.getVerticalScrollBar().setValue(lastScrollValue);
				}
			}.start();
			
		}
	}

	private void addListener(){
		code.addKeyListener(
				new KeyListener(){

					@Override
					public void keyTyped(KeyEvent e) {
						
					}

					@Override
					public void keyPressed(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub
						if(e.getKeyCode()==8||e.getKeyCode()==10)
							updateBinaryCode();
					}
					
				});
		
	}
	
	
	public void setCode(List<String> codes){
		code.setText("");
		for(int i=0;i<codes.size();i++){
			if(i>0)
				code.append("\n");
			code.append(codes.get(i));
			
		}
		updateBinaryCode();
	}
	
	
	public String getCodeText(){
		return code.getText();
	}

	public boolean hasError() {
		
		String binary = binCode.getText();
		
		int index = binary.indexOf("----");
		if(index<0)
			return false;
			
		
		int lineCounter = 0;
		for(int i=0;i<index;i++)
			if(binary.charAt(i)=='\n')
				lineCounter++;
		
		int start=0,end=0;
		String codeText = code.getText();
		int codeTextLength = codeText.length();
		for(int i=0;i<codeTextLength;i++){
			if(codeText.charAt(i)=='\n')
				lineCounter--;
			if(lineCounter==1)
				start = i+2;
			else if(lineCounter==-1){
				end = i;
				break;
			}
		}
		
		code.setSelectionStart(start);
		code.setSelectionEnd(end);
		code.grabFocus();
		
		//System.out.println(lineCounter+" "+index);
		
		
		
		return true;
	}
	
}
