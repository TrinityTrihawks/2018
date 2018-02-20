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
		power[wheelIndex.backrightwheel.getValue()] = xPower - rotation;
		power[wheelIndex.frontrightwheel.getValue()] = yPower + rotation;
		power[wheelIndex.backleftwheel.getValue()] = yPower - rotation;
		power[wheelIndex.frontleftwheel.getValue()] = xPower + rotation;
		
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
	double deaccel;
	double velocity;
	
	public void brake(double time, double displacement) {
		velocity =  this.wheels[wheelIndex.backrightwheel.getValue()].getSelectedSensorVelocity(wheelIndex.backrightwheel.getValue());
		deaccel = ((displacement-velocity*time-getDistance())/Math.pow(time, 2));
		Drive(Math.abs(deaccel), Math.PI, 0, 1);
		
	}
	
	public void rampRate(int rate) {
		this.wheels[wheelIndex.backrightwheel.getValue()].configOpenloopRamp(rate, 0);
		this.wheels[wheelIndex.frontrightwheel.getValue()].configOpenloopRamp(rate, 0);
		this.wheels[wheelIndex.backleftwheel.getValue()].configOpenloopRamp(rate, 0);
		this.wheels[wheelIndex.frontleftwheel.getValue()].configOpenloopRamp(rate, 0);
	}
	
	public void logTalonBusVoltagesConsole() {
		System.out.println("Back right: "+ this.wheels[wheelIndex.backrightwheel.getValue()].getBusVoltage());
		System.out.println("Front right: "+ this.wheels[wheelIndex.frontrightwheel.getValue()].getBusVoltage());
		System.out.println("Back left: "+ this.wheels[wheelIndex.backleftwheel.getValue()].getBusVoltage());
		System.out.println("Front left: "+ this.wheels[wheelIndex.frontleftwheel.getValue()].getBusVoltage());
	}
	
	public double[] getTalonBusVoltages() {
		double[] voltages = new double[4];
		voltages[0] = this.wheels[wheelIndex.backrightwheel.getValue()].getBusVoltage();
		voltages[1] = this.wheels[wheelIndex.frontrightwheel.getValue()].getBusVoltage();
		voltages[2] = this.wheels[wheelIndex.backleftwheel.getValue()].getBusVoltage();
		voltages[3] = this.wheels[wheelIndex.frontleftwheel.getValue()].getBusVoltage();
		return voltages;
	}
	
	public double[] getMotorOutputPercents() {
		double[] outputs = new double[4];
		outputs[0] = this.wheels[wheelIndex.backrightwheel.getValue()].getMotorOutputPercent();
		outputs[1] = this.wheels[wheelIndex.frontrightwheel.getValue()].getMotorOutputPercent();
		outputs[2] = this.wheels[wheelIndex.backleftwheel.getValue()].getMotorOutputPercent();
		outputs[3] = this.wheels[wheelIndex.frontleftwheel.getValue()].getMotorOutputPercent();
		return outputs;
	}
	
	public double[] getMotorOutputCurrents() {
		double[] outputs = new double[4];
		outputs[0] = this.wheels[wheelIndex.backrightwheel.getValue()].getOutputCurrent();
		outputs[1] = this.wheels[wheelIndex.frontrightwheel.getValue()].getOutputCurrent();
		outputs[2] = this.wheels[wheelIndex.backleftwheel.getValue()].getOutputCurrent();
		outputs[3] = this.wheels[wheelIndex.frontleftwheel.getValue()].getOutputCurrent();
		return outputs;
	}
	

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new teleopDrive());
	}
	
	public void resetDistance()
	{
	    this.wheels[wheelIndex.backrightwheel.getValue()].getSensorCollection().setQuadraturePosition(0, 0);
	    this.wheels[wheelIndex.frontrightwheel.getValue()].getSensorCollection().setQuadraturePosition(0, 0);
	    this.wheels[wheelIndex.backleftwheel.getValue()].getSensorCollection().setQuadraturePosition(0, 0);
	    this.wheels[wheelIndex.frontleftwheel.getValue()].getSensorCollection().setQuadraturePosition(0, 0);
		return;
	}

	public double getDistance()
	{
		int ticks = Math.min(Math.abs(this.wheels[wheelIndex.backrightwheel.getValue()].getSensorCollection().getQuadraturePosition()), 
				Math.abs(this.wheels[wheelIndex.frontrightwheel.getValue()].getSensorCollection().getQuadraturePosition()));
		ticks = Math.min(ticks, Math.abs(this.wheels[wheelIndex.backleftwheel.getValue()].getSensorCollection().getQuadraturePosition()));
		ticks = Math.min(ticks, Math.abs(this.wheels[wheelIndex.frontleftwheel.getValue()].getSensorCollection().getQuadraturePosition()));
		
		// convert encoder ticks
		double distance = ticks/4096 * RobotMap.wheelCircumference;
		
		return distance;
	}
	
	
}
