package org.usfirst.frc.team4215.robot.subsystems;



import org.usfirst.frc.team4215.robot.RobotMap;
import org.usfirst.frc.team4215.robot.commands.teleopDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;


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
	
	public double voltageSum;
	
	public Drivetrain() {
		
		//instantiates TalonSRX objects 
		this.wheels[wheelIndex.backrightwheel.getValue()] = new TalonSRX(RobotMap.talonWheel_backright);
		this.wheels[wheelIndex.frontrightwheel.getValue()] = new TalonSRX(RobotMap.talonWheel_frontright);
		this.wheels[wheelIndex.backleftwheel.getValue()] = new TalonSRX(RobotMap.talonWheel_backleft);
		this.wheels[wheelIndex.frontleftwheel.getValue()] = new TalonSRX(RobotMap.talonWheel_frontleft);
		
		//inverts back right and front right wheels
		this.wheels[wheelIndex.backrightwheel.getValue()].setInverted(true);
		this.wheels[wheelIndex.frontrightwheel.getValue()].setInverted(true);
		
		
	}
	
	public void Drive(double magnitude, double theta, double rotation, double slider_power) {
		
		System.out.println("Enter Drive Train");
		
		magnitude = magnitude * (4096/RobotMap.wheelCircumference);
		
		double xPower = magnitude * Math.cos(theta + (3*Math.PI / 4))/100;
		double yPower = magnitude * Math.sin(theta - (Math.PI / 4))/100;
				
		//takes values from above doubles and corresponds them with each wheel 
		power[wheelIndex.backrightwheel.getValue()] = slider_power*(xPower - rotation);
		power[wheelIndex.frontrightwheel.getValue()] = slider_power*((yPower - rotation)*.66);
		power[wheelIndex.backleftwheel.getValue()] = slider_power*(yPower + rotation);
		power[wheelIndex.frontleftwheel.getValue()] = slider_power*((xPower + rotation)*.66);
		
		//sets power to all the wheels
		this.wheels[wheelIndex.backrightwheel.getValue()].set(ControlMode.PercentOutput, power[wheelIndex.backrightwheel.getValue()]);
		this.wheels[wheelIndex.frontrightwheel.getValue()].set(ControlMode.PercentOutput, power[wheelIndex.frontrightwheel.getValue()]);
		this.wheels[wheelIndex.backleftwheel.getValue()].set(ControlMode.PercentOutput, power[wheelIndex.backleftwheel.getValue()]);
		this.wheels[wheelIndex.frontleftwheel.getValue()].set(ControlMode.PercentOutput, power[wheelIndex.frontleftwheel.getValue()]);
		
		voltageSum = 0;
		for(int i=0; i < numberWheels; i++) {
			voltageSum += Math.abs(wheels[i].getBusVoltage());
			
		}
		
	}
	public void DriveAutonomous(double distance, double theta, double rotation) {
			
		System.out.println("Entering Drive Autonomous");
		
		distance = distance * 4096;
		
		double xPower = distance * Math.cos(theta + (3*Math.PI / 4));
		double yPower = distance * Math.sin(theta - (Math.PI / 4));
				
		//takes values from above doubles and corresponds them with each wheel 
		power[wheelIndex.backrightwheel.getValue()] = (xPower - rotation);
		power[wheelIndex.frontrightwheel.getValue()] = ((yPower - rotation)*.66);
		power[wheelIndex.backleftwheel.getValue()] = (yPower + rotation);
		power[wheelIndex.frontleftwheel.getValue()] = ((xPower + rotation)*.66);
		
		//sets power to all the wheels
		this.wheels[wheelIndex.backrightwheel.getValue()].set(ControlMode.Position, power[wheelIndex.backrightwheel.getValue()]);
		this.wheels[wheelIndex.frontrightwheel.getValue()].set(ControlMode.Position, power[wheelIndex.frontrightwheel.getValue()]);
		this.wheels[wheelIndex.backleftwheel.getValue()].set(ControlMode.Position, power[wheelIndex.backleftwheel.getValue()]);
		this.wheels[wheelIndex.frontleftwheel.getValue()].set(ControlMode.Position, power[wheelIndex.frontleftwheel.getValue()]);
		
		voltageSum = 0;
		for(int i=0; i < numberWheels; i++) {
			voltageSum += Math.abs(wheels[i].getBusVoltage());	
		}
		
	}
	
	public void Stop() {
		Drive(0,0,0,0);
	}
	

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new teleopDrive());
	}
}
