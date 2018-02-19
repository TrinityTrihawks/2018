package org.usfirst.frc.team4215.robot.subsystems;

import org.usfirst.frc.team4215.robot.Robot;
import org.usfirst.frc.team4215.robot.RobotMap;
import org.usfirst.frc.team4215.robot.commands.runLift;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift extends Subsystem {

	Victor victor1;
	Victor victor2;
	
	private int reverse;
	
	
	public Lift() {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	victor1 = new Victor(RobotMap.liftvictorOne);
	victor2 = new Victor(RobotMap.liftvictorTwo);
	}
	/**
	 * 
	 */
	public void lift(double magnitude, double theta){
		
		if (Math.abs(theta)>(Math.PI/2)){
			reverse = -1;
		}
		else {
			reverse = 1;
		}
		
		magnitude = magnitude;
		
		victor1.set(reverse*magnitude);
		victor2.set(reverse*magnitude);
	}
	
    public void initDefaultCommand() {
        
        setDefaultCommand(new runLift());
    }
}

