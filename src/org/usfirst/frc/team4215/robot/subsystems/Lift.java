package org.usfirst.frc.team4215.robot.subsystems;

import org.usfirst.frc.team4215.robot.RobotMap;

import com.sun.glass.ui.Robot;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift extends Subsystem {

	Victor victor1;
	Victor victor2;
	
	
	
	public Lift( ) {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	victor1 = new Victor(RobotMap.liftvictorOne); 
	victor2 = new Victor(RobotMap.liftvictorTwo); 
	}
	/**
	 * 
	 */
	public setLiftOn(){
		victor1.set(Robot.m_oi.getLiftPower);
	}
	public setLiftOff() {
		victor2.set();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

