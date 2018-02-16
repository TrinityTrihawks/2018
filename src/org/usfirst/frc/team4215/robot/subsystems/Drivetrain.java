package org.usfirst.frc.team4215.robot.subsystems;



import org.usfirst.frc.team4215.robot.RobotMap;
import org.usfirst.frc.team4215.robot.commands.teleopDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Drivetrain extends Subsystem {
	
	//the port numbers of each of the wheels
	//also used as the index for the wheels array
	private enum wheelIndex {
		backrightwheel(RobotMap.talonWheel_backright),
		frontrightwheel(RobotMap.talonWheel_frontright),
		backleftwheel(RobotMap.talonWheel_backleft),
		frontleftwheel(RobotMap.talonWheel_frontleft); 
		
		private int wheel;
		private wheelIndex (int value) {
			this.wheel = value;
		}
		public int getValue() {
			return wheel;
		}
	}
	
	
	int numberWheels = RobotMap.numberOfWheels;
	
	//stores all the TalonSRX wheel objects
	TalonSRX[] wheels = new TalonSRX[numberWheels];
	
	//power values for each wheel
	public double [] power = new double [4];
	
	public Drivetrain() {
		
		//instantiates TalonSRX objects 
		this.wheels[wheelIndex.backrightwheel.getValue()] = new TalonSRX(RobotMap.talonWheel_backright);
		this.wheels[wheelIndex.frontrightwheel.getValue()] = new TalonSRX(RobotMap.talonWheel_frontright);
		this.wheels[wheelIndex.backleftwheel.getValue()] = new TalonSRX(RobotMap.talonWheel_backleft);
		this.wheels[wheelIndex.frontleftwheel.getValue()] = new TalonSRX(RobotMap.talonWheel_frontleft);
		
		//this.wheels[wheelIndex.backleftwheel.getValue()].setInverted(true);
		this.wheels[wheelIndex.frontleftwheel.getValue()].setInverted(true);
		//this.wheels[wheelIndex.backrightwheel.getValue()].setInverted(true);
		this.wheels[wheelIndex.frontrightwheel.getValue()].setInverted(true);
		
		
	}
	/**
	 * Dives the robot
	 * @param magnitude
	 * @param theta
	 * @param rotation
	 * @param slider_power
	 */
	public void Drive(double magnitude, double theta, double rotation, double slider_power) {
		
		System.out.println("Enter Drive Train");
		rotation *= -1;
		//magnitude = magnitude * (4096/RobotMap.wheelCircumference);
		
		//rotation = 0;
		
		double xPower = magnitude * Math.sin(-theta - Math.PI / 4);
		double yPower = magnitude * Math.cos(-theta - Math.PI / 4);
				
		//takes values from above doubles and corresponds them with each wheel 
		power[wheelIndex.backrightwheel.getValue()] = xPower - rotation;
		power[wheelIndex.frontrightwheel.getValue()] = yPower - rotation;
		power[wheelIndex.backleftwheel.getValue()] = yPower + rotation;
		power[wheelIndex.frontleftwheel.getValue()] = xPower + rotation;
		
		/*power[wheelIndex.backrightwheel.getValue()] = magnitude;
		 
		power[wheelIndex.frontrightwheel.getValue()] = magnitude;
		power[wheelIndex.backleftwheel.getValue()] = magnitude;
		power[wheelIndex.frontleftwheel.getValue()] = magnitude;*/
		
		this.wheels[wheelIndex.backrightwheel.getValue()].set(ControlMode.PercentOutput, power[wheelIndex.backrightwheel.getValue()]);
		this.wheels[wheelIndex.frontrightwheel.getValue()].set(ControlMode.PercentOutput, power[wheelIndex.frontrightwheel.getValue()]);
		this.wheels[wheelIndex.backleftwheel.getValue()].set(ControlMode.PercentOutput, power[wheelIndex.backleftwheel.getValue()]);
		this.wheels[wheelIndex.frontleftwheel.getValue()].set(ControlMode.PercentOutput, power[wheelIndex.frontleftwheel.getValue()]);
		
		logTalonBusVoltages();
		
	}
	
	public double[] volatges;
	 public void logTalonBusVoltages() {
		 
		 
		 
		    SmartDashboard.putNumber("Back right: ", this.wheels[wheelIndex.backrightwheel.getValue()].getBusVoltage());
		 
		    SmartDashboard.putNumber("Front right: ", this.wheels[wheelIndex.frontrightwheel.getValue()].getBusVoltage());
		 
		    SmartDashboard.putNumber("Back left: ", this.wheels[wheelIndex.backleftwheel.getValue()].getBusVoltage());
		 
		    SmartDashboard.putNumber("Front left: ", this.wheels[wheelIndex.frontleftwheel.getValue()].getBusVoltage());
		 
		  }
		 
	 public void TalonOutputVoltage() {
		 for(int j = 0; j<4;j++) {
			 SmartDashboard.putNumber("outputVoltage"+j, this.wheels[j].getMotorOutputVoltage());
		 }
	 }
	
	public void Stop() {
		Drive(0,0,0,0);
	}
	
	public void logTalonBusVoltagesConsole() {
		System.out.println("Back right: "+ this.wheels[wheelIndex.backrightwheel.getValue()].getBusVoltage());
		System.out.println("Front right: "+ this.wheels[wheelIndex.frontrightwheel.getValue()].getBusVoltage());
		System.out.println("Back left: "+ this.wheels[wheelIndex.backleftwheel.getValue()].getBusVoltage());
		System.out.println("Front left: "+ this.wheels[wheelIndex.frontleftwheel.getValue()].getBusVoltage());
	}
	
	

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new teleopDrive());
	}
}
