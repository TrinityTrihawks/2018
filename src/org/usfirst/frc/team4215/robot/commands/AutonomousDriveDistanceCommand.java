package org.usfirst.frc.team4215.robot.commands;

import org.usfirst.frc.team4215.robot.Robot;
import org.usfirst.frc.team4215.robot.ultrasonic.IUltrasonic;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonomousDriveDistanceCommand extends Command {

	protected double targetDistanceInches = 0;
	protected double magnitude = 0;
	protected double theta = 0;
	protected boolean completed = false;
	
    public AutonomousDriveDistanceCommand(int distanceInches, double magnitude, double theta) {
    		requires(Robot.drivetrain);

    		this.targetDistanceInches = distanceInches;
    		this.magnitude = magnitude;
    		this.theta = theta;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    		System.out.println("Initializing autonomous command");
    		Robot.drivetrain.resetDistance();
    }

    
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		Robot.drivetrain.Drive(this.magnitude, this.theta, 0.0, 1);	
		return;
    	}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    		if (!this.completed) {
    			if (Robot.drivetrain.getDistance() >= targetDistanceInches) {
    				this.completed = true;
    				//Robot.drivetrain.brake(2, 10);
    			}    			
    		}
    		
		return this.completed;
    }

    // Called once after isFinished returns true
    protected void end() {
    		this.completed = true;
		Robot.drivetrain.Stop();
		Robot.drivetrain.resetDistance();
		System.out.println("DriveDistance Command ended");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
		end();
    }
}
