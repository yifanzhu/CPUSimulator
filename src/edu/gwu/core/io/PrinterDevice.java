package edu.gwu.core.io;

import edu.gwu.core.basic.Word;

public class PrinterDevice extends IODevice{
	
	
	public PrinterDevice(){
		this.devid = IODevice.DEVID_PRINTER;
	}
	
	/**
	 * information will be written(displayed) on PrinterPanel
	 */
	@Override
	public void write(Word value) {
		
		//System.out.println(value.intValue());
		this.setChanged();
		this.notifyObservers(value);
	}

	/**
	 * actually this read-method will never be called. 
	 */
	@Override
	public Word read() {
		return null;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}


}
