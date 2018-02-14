package org.usfirst.frc.team4215.robot.subsystems;

import org.usfirst.frc.team4215.robot.RobotMap;
import org.usfirst.frc.team4215.robot.commands.teleopDrive;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;


public class Drivetrain extends Subsystem {

	private final double QUARTERPI = Math.PI / 4;
	
	//the port numbers of each of the wheels
	//also used as the index for the wheels array
	private enum WheelIndex {
		backrightwheel(RobotMap.talonWheel_backright),
		frontrightwheel(RobotMap.talonWheel_frontright),
		backleftwheel(RobotMap.talonWheel_backleft),
		frontleftwheel(RobotMap.talonWheel_frontleft); 
		
		private int wheelID;
		private WheelIndex (int value) {
			this.wheelID = value;
		}

		public int getValue() {
			return wheelID;
		}
	}
	
	int numberWheels = RobotMap.numberOfWheels;
	
	//stores all the TalonSRX wheel objects
	TalonSRX[] wheels = new TalonSRX[numberWheels];
	
	//power values for each wheel
	public double [] power = new double [4];
	
	public Drivetrain() {
		
		//instantiates TalonSRX objects 
		this.wheels[WheelIndex.backrightwheel.getValue()] = new TalonSRX(RobotMap.talonWheel_backright);
		this.wheels[WheelIndex.frontrightwheel.getValue()] = new TalonSRX(RobotMap.talonWheel_frontright);
		this.wheels[WheelIndex.backleftwheel.getValue()] = new TalonSRX(RobotMap.talonWheel_backleft);
		this.wheels[WheelIndex.frontleftwheel.getValue()] = new TalonSRX(RobotMap.talonWheel_frontleft);
		
		//inverts back right and front right wheels
		this.wheels[WheelIndex.backrightwheel.getValue()].setInverted(true);
		this.wheels[WheelIndex.frontrightwheel.getValue()].setInverted(true);
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
		magnitude = magnitude * (4096/RobotMap.wheelCircumference);
		
		double xPower = magnitude * Math.cos(theta + QUARTERPI)/100;
		double yPower = magnitude * Math.sin(theta - QUARTERPI)/100;
						
		//sets power to all the wheels
		this.wheels[WheelIndex.backrightwheel.getValue()].set(ControlMode.PercentOutput, (xPower - rotation));
		this.wheels[WheelIndex.frontrightwheel.getValue()].set(ControlMode.PercentOutput, (yPower - rotation));
		this.wheels[WheelIndex.backleftwheel.getValue()].set(ControlMode.PercentOutput, (yPower + rotation));
		this.wheels[WheelIndex.frontleftwheel.getValue()].set(ControlMode.PercentOutput, (xPower + rotation));
		
		logTalonBusVoltages();
	}
	
	public void Stop() {
		this.Drive(0,0,0);
	}
	
	public void logTalonBusVoltages() {
		for (WheelIndex w : WheelIndex.values()) {
			System.out.println(w.toString() + ": " + this.wheels[w.getValue()].getBusVoltage());
		}
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new teleopDrive());
	}
}
