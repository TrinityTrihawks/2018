package org.usfirst.frc.team4215.robot.subsystems;

import org.usfirst.frc.team4215.robot.RobotMap;
import org.usfirst.frc.team4215.robot.commands.teleopDrive;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;


public class Drivetrain extends Subsystem {
	
	private final double QUARTERPI = Math.PI / 4;
	//private final int NUMBER_WHEELS = 4;
	
	//the port numbers of each of the wheels
	//also used as the index for the wheels array
	private enum WheelIndex {
		backrightwheel(RobotMap.talonWheel_backright),
		frontrightwheel(RobotMap.talonWheel_frontright),
		backleftwheel(RobotMap.talonWheel_backleft),
		frontleftwheel(RobotMap.talonWheel_frontleft); 
		
		private int wheelID;
		private TalonSRX wheel;
		
		private WheelIndex (int value) {
			this.wheelID = value;
			this.wheel = new TalonSRX(value);
		}

		public TalonSRX getWheel() {
			return this.wheel;
		}

		public int getID() {
			return this.wheelID;
		}
	}
			
	public Drivetrain() {
		
		//TalonSRX instantiated by WheelIndex
		WheelIndex.backrightwheel.getWheel().setInverted(true);
		WheelIndex.frontrightwheel.getWheel().setInverted(true);
	}
	
	/**
	 * Drives the robot
	 * @param magnitude
	 * @param theta
	 * @param rotation
	 * @param slider_power
	 */
	public void Drive(double magnitude, double theta, double rotation) {
		
		System.out.println("Enter Drive Train");
		magnitude = magnitude * (4096/RobotMap.talonWheel_wheelCircumference);
		
		double xPower = magnitude * Math.cos(theta + QUARTERPI)/100;
		double yPower = magnitude * Math.sin(theta - QUARTERPI)/100;
						
		//sets power to all the wheels
		WheelIndex.backrightwheel.getWheel().set(ControlMode.PercentOutput, (xPower - rotation));
		WheelIndex.frontrightwheel.getWheel().set(ControlMode.PercentOutput, (yPower - rotation));
		WheelIndex.backleftwheel.getWheel().set(ControlMode.PercentOutput, (yPower + rotation));
		WheelIndex.frontleftwheel.getWheel().set(ControlMode.PercentOutput, (xPower + rotation));
		
		logTalonBusVoltages();
	}
	
	public void Stop() {
		this.Drive(0,0,0);
	}
	
	public void logTalonBusVoltages() {
		for (WheelIndex w : WheelIndex.values()) {
			System.out.println(w.toString() + " ( " + w.getID() + " )  : " + w.getWheel().getBusVoltage());
		}
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new teleopDrive());
	}
}
