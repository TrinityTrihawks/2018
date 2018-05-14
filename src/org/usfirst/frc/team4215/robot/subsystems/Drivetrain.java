package org.usfirst.frc.team4215.robot.subsystems;

import org.usfirst.frc.team4215.robot.RobotMap;
import org.usfirst.frc.team4215.robot.commands.teleopDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Drivetrain extends PIDSubsystem {
	
	
	//the port numbers of each of the wheels
	//also used as the index for the wheels array
	private enum WheelType {
		
		backrightwheel(RobotMap.talonWheel_backright),
		frontrightwheel(RobotMap.talonWheel_frontright),
		backleftwheel(RobotMap.talonWheel_backleft),
		frontleftwheel(RobotMap.talonWheel_frontleft); 
		
		private int wheelId;
		private TalonSRX wheel;
		private WheelType (int id) {
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
	
	PigeonIMU pigeon1 = new PigeonIMU(WheelType.frontrightwheel.getWheel());

	public Drivetrain() {
		super("Drivetrain", 2, 0, 0);
		setAbsoluteTolerance(0.05);
		getPIDController().setContinuous();
		
		WheelType.frontleftwheel.getWheel().setInverted(true);
		WheelType.frontrightwheel.getWheel().setInverted(true);
		
		
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
		
		
		//rotation *= -.5;
		rotation *= -1; //doing this one for a test
		
		if (magnitude <= .08 && magnitude >= -.08) {
			theta = 0;
			magnitude = 0;
		}
		
		//not sure what this is supposed to be 
		//if (theta <= Math.PI/30 && theta >= -Math.PI/30)
				
		//rotation = 0;
		double xPower = magnitude * Math.sin(-theta - Math.PI / 4);
		double yPower = magnitude * Math.cos(-theta - Math.PI / 4);
				
		//takes values from above doubles and corresponds them with each wheel 		
		WheelType.backrightwheel.getWheel().set(ControlMode.PercentOutput, xPower - rotation);
		WheelType.frontrightwheel.getWheel().set(ControlMode.PercentOutput, yPower + rotation);
		WheelType.backleftwheel.getWheel().set(ControlMode.PercentOutput, yPower - rotation);
		WheelType.frontleftwheel.getWheel().set(ControlMode.PercentOutput, xPower + rotation);
		
	}

	public void Stop() {
		Drive(0,0,0,0);
	}
	double deaccel;
	double velocity;
	
	public void brake(double time, double displacement) {
		velocity =  WheelType.backrightwheel.getWheel().getSelectedSensorVelocity(WheelType.backrightwheel.getId());
		deaccel = ((displacement-velocity*time-getDistance())/Math.pow(time, 2));
		Drive(Math.abs(deaccel), Math.PI, 0, 1);	
	}
	
	public void setRampRate(int rate) {
		for (WheelType w : WheelType.values()) { 
			w.getWheel().configOpenloopRamp(rate, 0);
		}
	}	

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new teleopDrive());
	}
	
	public void resetDistance()
	{
		for (WheelType w : WheelType.values()) { 
			w.getWheel().getSensorCollection().setQuadraturePosition(0, 0);
		}
		return;
	}
	
	double [] ypr = new double[3];
	public void getPigeonYawPitchRoll() {
		pigeon1.getYawPitchRoll(ypr);
		SmartDashboard.putNumber("Yaw", ypr[0]);
		SmartDashboard.putNumber("Pitch", ypr[1]);
		SmartDashboard.putNumber("Roll", ypr[2]);
	}
	
	public double getPigeonYaw() {
		double[] ypr = new double[3];
		pigeon1.getYawPitchRoll(ypr);
		return ypr[0];
	}
	
	public double getDistance()
	{
		int ticks = Math.abs(WheelType.frontleftwheel.getWheel().getSensorCollection().getQuadraturePosition());
		// convert encoder ticks
		double distance = ticks/4096 * RobotMap.wheelCircumference;
		return distance;
	}

	//
	// logging functions
	//
	/*public void logTalonBusVoltages() {
		for (WheelType w : WheelType.values()) {
			SmartDashboard.putNumber("Wheel Voltage  ( " + w.toString() + " )   :", w.getWheel().getBusVoltage());
		}
	}*/

	public void logTalonMotorOutputPercent() {
		for (WheelType w : WheelType.values()) {
			SmartDashboard.putNumber("Wheel Power  ( " + w.toString() + " )   :", w.getWheel().getMotorOutputPercent());
		}
	}

	@Override
	protected double returnPIDInput() {
		return getPigeonYaw();
	}

	@Override
	protected void usePIDOutput(double output) {
		WheelType.backrightwheel.getWheel().set(ControlMode.PercentOutput, -1 * output);
		WheelType.frontrightwheel.getWheel().set(ControlMode.PercentOutput, -1 * output);
		WheelType.backleftwheel.getWheel().set(ControlMode.PercentOutput, output);
		WheelType.frontleftwheel.getWheel().set(ControlMode.PercentOutput, output);
		
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
