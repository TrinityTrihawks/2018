package org.usfirst.frc.team4215.robot.commands;

import org.usfirst.frc.team4215.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonomousDriveDistanceCommand extends Command {

	private double targetDistanceInches = 0;
	private double magnitude = 0;
	private double theta = 0;
	private boolean runOnce = true;
	private boolean completed = false;
	
    public AutonomousDriveDistanceCommand(int distanceInches, double magnitude, double theta) {
    		requires(Robot.drivetrain);

    		this.targetDistanceInches = distanceInches;
    		this.magnitude = magnitude;
    		this.theta = theta;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Initializing autonomous command");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		if (this.runOnce) {
        		Robot.drivetrain.resetDistance();
    			Robot.drivetrain.Drive(this.magnitude, this.theta, 0.0, 1);
    			runOnce = false;
    			
    			System.out.println("Executed once");
    		}
    		else
    		{
    			
    		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Robot.drivetrain.getDistance() >= targetDistanceInches) {
			this.completed = true;
		}
    	
		return this.completed;
		
		
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.brake(2, 10);
		Robot.drivetrain.Stop();
		Robot.drivetrain.resetDistance();
		System.out.println("DriveDistance Command ended");

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
		Robot.drivetrain.Stop();
    		this.completed = true;
    }
}
