package org.usfirst.frc.team4215.robot.subsystems;



import org.usfirst.frc.team4215.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Drivetrain extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	
	
	TalonSRX backrightwheel = new TalonSRX(RobotMap.talonWheel_backright);
	TalonSRX frontrightwheel = new TalonSRX(RobotMap.talonWheel_frontright);
	TalonSRX backleftwheel = new TalonSRX(RobotMap.talonWheel_backleft);
	TalonSRX frontleftwheel = new TalonSRX(RobotMap.talonWheel_frontleft);
	
	int numberWheels = 
	
	
	CANTalon[] wheels = new CANTalon[]
	
	private Drivetrain() {
		this.wheels[backrightwheel] = Wheel.Create()
		this.wheels[frontrightwheel] = Wheel.Create()
		this.wheels[backleftwheel] = Wheel.Create()
		this.wheels[frontleftwheel] = Wheel.Create()
	}
	
	
	
	
	
	

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
