package org.usfirst.frc.team4215.robot.ultrasonic;

import edu.wpi.first.wpilibj.SerialPort;

public class UltrasonicReaderUSB implements Runnable, IUltrasonic {

	private static int SerialBaudRate = 57600;

	// length of the data you hope to get
	private SerialPort serialPort;
	private String name;
	private Thread readThread;
	private volatile boolean readThreadFlag;
	private volatile double distance = ERR_DISTANCE;
	
	public static UltrasonicReaderUSB Create(String name, SerialPort.Port port) {
		try {
			return new UltrasonicReaderUSB(name, port);
	    } catch (Exception e) { 
			System.out.println(e);
			return new UltrasonicReaderUSB(name);
		}
	}
	
	private UltrasonicReaderUSB(String name)  {
		this.name = name;
		this.readThreadFlag = false;
		this.readThread = new Thread(this);
	}

	private UltrasonicReaderUSB(String name, SerialPort.Port port)  {
		this.name = name;
		this.serialPort = new SerialPort(SerialBaudRate, port);
		this.serialPort.setReadBufferSize(7);
		this.serialPort.disableTermination();

		this.readThreadFlag = true;
		this.readThread = new Thread(this);
		this.readThread.start();
	}

	public void stop() {
		this.readThreadFlag = false;
	}
	
	public void finalize() {
		this.readThreadFlag = false;
		this.serialPort.free();
		this.distance = ERR_DISTANCE;
	}
	
	public void run() {
		while (this.readThreadFlag) {
		    try {
		    		this.readSerial();
		        Thread.sleep(25);  
			} catch (Exception e) { 
				System.out.println(e);
				this.readThreadFlag = false;
			}
		}
	}

	private void readSerial() {
		int bytesAvailable = this.serialPort.getBytesReceived();
		
		if (bytesAvailable < 6) {
			return;
		}
		
		byte[] readBuffer = this.serialPort.read(bytesAvailable);
		
		if (readBuffer.length < bytesAvailable)
		{
			bytesAvailable = readBuffer.length;
		}
 
        // Searches for '82' which starts each datastream  
        for (int i = 0; i < bytesAvailable; i++) {  
          if (readBuffer[i] == 82 && (bytesAvailable-i) >= 5) {  
            this.distance = (1000 * (readBuffer[i + 1] - 48)) + (100 * (readBuffer[i + 2] - 48)) + (10 * (readBuffer[i + 3] - 48)) + (readBuffer[i + 4] - 48);
            i += 5;
          }	        	  
        }
	        
        // TODO: not sure if boundary checks are needed
        this.distance = this.distance < MIN_DISTANCE ? MIN_DISTANCE : this.distance;
        this.distance = this.distance > MAX_DISTANCE ? MAX_DISTANCE : this.distance;
 	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public boolean isEnabled() {
		return this.readThread.isAlive() && this.readThreadFlag;
	}

	@Override
	public double getRangeInches() {
		return this.distance / 25.4;
	}

	@Override
	public double getRangeMM() {
		return this.distance;
	}
}