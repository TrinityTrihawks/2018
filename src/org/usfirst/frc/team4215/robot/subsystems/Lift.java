package org.usfirst.frc.team4215.robot.subsystems;

import org.usfirst.frc.team4215.robot.RobotMap;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Victor victor1 = new Victor(RobotMap.liftvictorOne); 
	Victor victor2 = new Victor(RobotMap.liftvictorTwo); 

	
	public Lift(){
		
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

