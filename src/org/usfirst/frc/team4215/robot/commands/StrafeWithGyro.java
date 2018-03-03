package org.usfirst.frc.team4215.robot.commands;

import org.usfirst.frc.team4215.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StrafeWithGyro extends Command {
	
	private double Kp = 0;
	
	private double initialAngle;
    private double targetDistanceInches = 0;
	private double magnitude = 0;
	private double theta = 0;
	
	private boolean completed = false;



    public StrafeWithGyro(int distanceInches, double magnitude, double theta) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        
        this.targetDistanceInches = distanceInches;
		this.magnitude = magnitude;
		this.theta = theta;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.m_oi.gyro.reset();
    	//initialAngle = Robot.m_oi.getGyroAngle();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double drift = Robot.m_oi.getGyroAngle();
    	if (Math.abs(Robot.m_oi.getGyroAngle())>1) {
        	Robot.drivetrain.Drive(this.magnitude, this.theta, -1*Math.signum(Robot.m_oi.getGyroAngle())*.05, 1);        	
    	}
    	else if (Math.abs(Robot.m_oi.getGyroAngle())>0) {
        	Robot.drivetrain.Drive(this.magnitude, this.theta, 0, 1);        	

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
    	Robot.drivetrain.Stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
