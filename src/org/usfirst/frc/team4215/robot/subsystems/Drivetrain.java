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
	private enum wheelIndex {
		backrightwheel(0),
		frontrightwheel(1),
		backleftwheel(2),
		frontleftwheel(3); 
		
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
	
	private Drivetrain() {
		
		this.wheels[wheelIndex.backrightwheel.getValue()] = new TalonSRX(RobotMap.talonWheel_backright);
		this.wheels[wheelIndex.frontrightwheel.getValue()] = new TalonSRX(RobotMap.talonWheel_frontright);
		this.wheels[wheelIndex.backleftwheel.getValue()] = new TalonSRX(RobotMap.talonWheel_backleft);
		this.wheels[wheelIndex.frontleftwheel.getValue()] = new TalonSRX(RobotMap.talonWheel_frontleft);
	}
	
	
	
	
	
	

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
