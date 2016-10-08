package edu.gwu.core.io;

import java.util.HashMap;
import java.util.Map;

import edu.gwu.core.basic.Word;
import edu.gwu.exception.IllegalDeviceStatusException;

/**
 * all the io devices are registered here
 * @author yangmang
 *
 */
public class IODeviceController {
	
	/**
	 * store all the IO devices indexed by devID
	 */
	private Map<Integer,IODevice> devices;
	
	/**
	 * an instance
	 */
	private static IODeviceController _instance;
	public static IODeviceController getInstance(){
		if(_instance==null)
			_instance = new IODeviceController();
		return _instance;
	}
	
	/**
	 * constructor
	 */
	public IODeviceController(){
		devices = new HashMap<Integer,IODevice>();
		
		//keyboard
		KeyboardDevice keyboard = new KeyboardDevice();
		devices.put(keyboard.devid, keyboard);
		
		//printer
		PrinterDevice printer = new PrinterDevice();
		devices.put(printer.devid, printer);
		
		//file: devid:3
		FileDevice file3 = new FileDevice(3,FileDevice.IOTYPE_INPUT);
		file3.setDefaultFile("./devid3.txt");
		devices.put(file3.devid, file3);
		
		
		//file: devid:3
		FileDevice file4 = new FileDevice(4,FileDevice.IOTYPE_OUTPUT);
		file4.setDefaultFile("./devid4.txt");
		devices.put(file4.devid, file4);
		
		FileDevice file5 = new FileDevice(5,FileDevice.IOTYPE_INPUT,false);
		file5.setDefaultFile("./part4data.txt");
		devices.put(file5.devid, file5);
		
	
		CodeEditorDevice codeEditorDevice = new CodeEditorDevice();
		devices.put(codeEditorDevice.devid, codeEditorDevice);
	}
	
	/**
	 * for CPU to write data into Device
	 * @param devid
	 * @param value
	 * @throws IllegalDeviceStatusException: Device not found, Device not ready, Device error, etc
	 */
	public void output(int devid,Word value) throws IllegalDeviceStatusException{
		//find the device
		IODevice dev = devices.get(devid);
		if(dev==null){
			throw new IllegalDeviceStatusException("Device("+devid+") not found");
		}
		//use device to write data, Exception may be thrown
		dev.write(value);
	}
	/**
	 * for CPU to read data from Device
	 * @param devid
	 * @return
	 * @throws IllegalDeviceStatusException: Device not found, Device not ready, Device error, etc
	 */
	public Word input(int devid) throws IllegalDeviceStatusException{
		//find the device
		IODevice dev = devices.get(devid);
		if(dev==null){
			throw new IllegalDeviceStatusException("Device("+devid+") not found");
		}
		//fetching data from device, the fetching may cause CPU waiting. Exception may be thrown
		return dev.read();
	}
	
	/**
	 * get Keyboard device
	 * @return
	 */
	public IODevice getKeyboard(){
		return devices.get(IODevice.DEVID_KEYBOARD);
	}
	
	/**
	 * get Printer device
	 * @return
	 */
	public IODevice getPrinter(){
		return devices.get(IODevice.DEVID_PRINTER);
	}
	
	public FileDevice getFileDevice(int devid){
		return (FileDevice)devices.get(devid);
	}
	
	public CodeEditorDevice getCodeEditorDevice(){
		return (CodeEditorDevice)devices.get(IODevice.DEVID_EDITOR);
	}
	
	public void resetAll(){
		try {
			for(IODevice dev:devices.values()){
				dev.reset();
			}
		} catch (IllegalDeviceStatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
