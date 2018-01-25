package org.usfirst.frc.team4215.robot.subsystems;



import org.usfirst.frc.team4215.robot.RobotMap;
import org.usfirst.frc.team4215.robot.commands.teleopDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Drivetrain extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
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
	
	TalonSRX[] wheels = new TalonSRX[numberWheels];
	
	public double [] power = new double [4];
	
	public Drivetrain() {
		
		
		this.wheels[wheelIndex.backrightwheel.getValue()] = new TalonSRX(RobotMap.talonWheel_backright);
		this.wheels[wheelIndex.frontrightwheel.getValue()] = new TalonSRX(RobotMap.talonWheel_frontright);
		this.wheels[wheelIndex.backleftwheel.getValue()] = new TalonSRX(RobotMap.talonWheel_backleft);
		this.wheels[wheelIndex.frontleftwheel.getValue()] = new TalonSRX(RobotMap.talonWheel_frontleft);
		
	}
	
	public void mecanumDrive_Polar(double magnitude, double theta, double rotation) {
	}
	
	public void Drive(double magnitude, double theta, double rotation) {
		
		System.out.println("Enter Drive Train");
		
		
		/*
		magnitude = magnitude * (4096/RobotMap.wheelCircumference);
		
		double xPower = magnitude * Math.cos(theta - (Math.PI / 4));
		double yPower = magnitude * Math.sin(theta + (Math.PI / 4));
		
		//double xPower = 0.5;
		//double yPower = 0.5;
		
		// TODO: We need to reevaluate rotation. It shouldnt be directly from the joystick
		rotation = 0;
		
		power[wheelIndex.backrightwheel.getValue()] = (xPower - rotation)/175;
		power[wheelIndex.frontrightwheel.getValue()] = (xPower - rotation)/175;
		power[wheelIndex.backleftwheel.getValue()] = -1*(yPower + rotation)/175;
		power[wheelIndex.frontleftwheel.getValue()] = -1*(yPower + rotation)/175;
		
		this.wheels[wheelIndex.backrightwheel.getValue()].set(ControlMode.PercentOutput, power[wheelIndex.backrightwheel.getValue()]);
		this.wheels[wheelIndex.frontrightwheel.getValue()].set(ControlMode.PercentOutput, power[wheelIndex.frontrightwheel.getValue()]);
		this.wheels[wheelIndex.backleftwheel.getValue()].set(ControlMode.PercentOutput, power[wheelIndex.backleftwheel.getValue()]);
		this.wheels[wheelIndex.frontleftwheel.getValue()].set(ControlMode.PercentOutput, power[wheelIndex.frontleftwheel.getValue()]);
		*/
		
		
	}
	
	public void Stop() {
		Drive(0,0,0);
	}
	
	

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new teleopDrive());
	}
}
