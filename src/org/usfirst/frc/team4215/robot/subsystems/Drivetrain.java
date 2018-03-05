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
	private enum TalonSRXWheelEnum {
		
		backrightwheel(RobotMap.talonWheel_backright),
		frontrightwheel(RobotMap.talonWheel_frontright),
		backleftwheel(RobotMap.talonWheel_backleft),
		frontleftwheel(RobotMap.talonWheel_frontleft); 
		
		private int wheelId;
		private TalonSRX wheel;
		private TalonSRXWheelEnum (int id) {
			this.wheelId = id;
			this.wheel = new TalonSRX(this.wheelId);			
		}
		
		public int getId() {
			return this.wheelId;
		}
		public TalonSRX getWheel() {
			return this.wheel;
		}
	}
		
	public Drivetrain() {
				
		TalonSRXWheelEnum.frontleftwheel.getWheel().setInverted(true);
		TalonSRXWheelEnum.frontrightwheel.getWheel().setInverted(true);		
	}
	
	/**
	 * Dives the robot
	 * @param magnitude
	 * @param theta
	 * @param rotation
	 * @param slider_power
	 */
	public void Drive(double magnitude, double theta, double rotation, double slider_power) {
		
		//System.out.println("Enter Drive Train");
		rotation *= -.5;
		if (magnitude <= .08 && magnitude >= -.08) {
			theta = 0;
			magnitude = 0;
		}
		
		
		//not sure what this is supposed to be 
		//if (theta <= Math.PI/30 && theta >= -Math.PI/30)
		
			
		//magnitude *= slider_power;
		
		//rotation = 0;
		double xPower = magnitude * Math.sin(-theta - Math.PI / 4);
		double yPower = magnitude * Math.cos(-theta - Math.PI / 4);
				
		//takes values from above doubles and corresponds them with each wheel 		
		TalonSRXWheelEnum.backrightwheel.getWheel().set(ControlMode.PercentOutput, xPower - rotation);
		TalonSRXWheelEnum.frontrightwheel.getWheel().set(ControlMode.PercentOutput, yPower + rotation);
		TalonSRXWheelEnum.backleftwheel.getWheel().set(ControlMode.PercentOutput, yPower - rotation);
		TalonSRXWheelEnum.frontleftwheel.getWheel().set(ControlMode.PercentOutput, xPower + rotation);
		
		logTalonBusVoltages();
	}

	public void Stop() {
		Drive(0,0,0,0);
	}
	double deaccel;
	double velocity;
	
	public void brake(double time, double displacement) {
		velocity =  TalonSRXWheelEnum.backrightwheel.getWheel().getSelectedSensorVelocity(TalonSRXWheelEnum.backrightwheel.getId());
		deaccel = ((displacement-velocity*time-getDistance())/Math.pow(time, 2));
		Drive(Math.abs(deaccel), Math.PI, 0, 1);
		
	}
	
	public void setRampRate(int rate) {
		for (TalonSRXWheelEnum w : TalonSRXWheelEnum.values()) { 
			w.getWheel().configOpenloopRamp(rate, 0);
		}
	}	

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new teleopDrive());
	}
	
	public void resetDistance()
	{
		for (TalonSRXWheelEnum w : TalonSRXWheelEnum.values()) { 
			w.getWheel().getSensorCollection().setQuadraturePosition(0, 0);
		}
		return;
	}

	public double getDistance()
	{
		int ticks = Math.abs(TalonSRXWheelEnum.frontleftwheel.getWheel().getSensorCollection().getQuadraturePosition());
				
		
		// convert encoder ticks
		double distance = ticks/4096 * RobotMap.wheelCircumference;
		
		return distance;
	}

	//
	// logging functions
	//
	public void logTalonBusVoltages() {
		for (TalonSRXWheelEnum w : TalonSRXWheelEnum.values()) {
			SmartDashboard.putNumber("Wheel Voltage  ( " + w.toString() + " )   :", w.getWheel().getBusVoltage());
		}
	}

	public void logTalonMotorOutputPercent() {
		for (TalonSRXWheelEnum w : TalonSRXWheelEnum.values()) {
			SmartDashboard.putNumber("Wheel Power  ( " + w.toString() + " )   :", w.getWheel().getMotorOutputPercent());
		}
	}

	//	public double[] getMotorOutputPercents() {
//		double[] outputs = new double[4];
//		outputs[0] = this.wheels[TalonSRXWheelEnum.backrightwheel.getWheel()].getMotorOutputPercent();
//		outputs[1] = this.wheels[TalonSRXWheelEnum.frontrightwheel.getWheel()].getMotorOutputPercent();
//		outputs[2] = this.wheels[TalonSRXWheelEnum.backleftwheel.getWheel()].getMotorOutputPercent();
//		outputs[3] = this.wheels[TalonSRXWheelEnum.frontleftwheel.getWheel()].getMotorOutputPercent();
//		return outputs;
//	}
//	
//	public double[] getMotorOutputCurrents() {
//		double[] outputs = new double[4];
//		outputs[0] = this.wheels[TalonSRXWheelEnum.backrightwheel.getWheel()].getOutputCurrent();
//		outputs[1] = this.wheels[TalonSRXWheelEnum.frontrightwheel.getWheel()].getOutputCurrent();
//		outputs[2] = this.wheels[TalonSRXWheelEnum.backleftwheel.getWheel()].getOutputCurrent();
//		outputs[3] = this.wheels[TalonSRXWheelEnum.frontleftwheel.getWheel()].getOutputCurrent();
//		return outputs;
//	}
}
