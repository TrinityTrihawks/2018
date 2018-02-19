package org.usfirst.frc.team4215.robot.subsystems;

import org.usfirst.frc.team4215.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class analogUltrasonic extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	AnalogInput frontLeft;
	AnalogInput frontRight;
	
	analogUltrasonic(){
		this.frontLeft = new AnalogInput(RobotMap.frontLeftUltrasonicChannel);
		this.frontRight = new AnalogInput(RobotMap.frontRightUltrasonicChannel);
		
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

