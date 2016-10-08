package edu.gwu.core.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import edu.gwu.core.io.IODevice;
import edu.gwu.exception.IllegalDeviceStatusException;

public class CPUOutputSignalBuffer {

	private Vector<CPUOutputSignal> signals;
	
	public CPUOutputSignalBuffer(){
		signals = new Vector<CPUOutputSignal>();
	}
	
	public void checkForSignal(IODevice dev) throws IllegalDeviceStatusException{
		synchronized (dev) {
			List<CPUOutputSignal> toRemove = new ArrayList<CPUOutputSignal>();
			
			for(CPUOutputSignal signal:signals){
				if(dev.getDevid() == signal.targetDeviceId){
					dev.write(new Word(signal.value));
					//signals.remove(signal);
					toRemove.add(signal);
				}
			}
			signals.removeAll(toRemove);
		}
		
	}
	
	public void outputSignal(int devid,int value){
		signals.add(new CPUOutputSignal(devid, value));
	}
}

class CPUOutputSignal {

	public int targetDeviceId;
	public int value;
	public CPUOutputSignal(int devid,int value){
		this.targetDeviceId = devid;
		this.value = value;
	}
}