package org.usfirst.frc.team4215.robot.commands;

import org.usfirst.frc.team4215.robot.Robot;
import org.usfirst.frc.team4215.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StrafewithUltrasonic extends Command {
	
	private enum MovementState {
		Moving,
		Stopped,
	}

	MovementState state;
	
    private double targetDistanceInches = 0;
	private double magnitude = 0;
	private double theta = 0;
	
	private double failSafeReading = 400;
	private double desiredReading = 400;
	
	private boolean completed = false;
	
	//private UltrasonicReader reader;

    public StrafewithUltrasonic(int distanceInches, double magnitude, double theta) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        if(theta == Math.PI/2) {
        	//reader = UltrasonicReader.Create(RobotMap.rightUsbUltrasonic);
        } else if(theta == -Math.PI/2){
        	//reader = UltrasonicReader.Create(RobotMap.leftUsbUltrasonic);
        } else {
        	System.out.println("Ultrasonic direction invalid");
        }
        
        this.targetDistanceInches = distanceInches;
		this.magnitude = magnitude;
		this.theta = theta;
    }

	

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Initializing autonomous command");
		
    	Robot.drivetrain.resetDistance();
    	Robot.drivetrain.Drive(this.magnitude, this.theta, 0.0, 1);
		state = MovementState.Moving;

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	/*
    		if(reader.getDistance() < failSafeReading) {
    			if(state == MovementState.Moving) {
    				
    				Robot.drivetrain.Stop();
    				state = MovementState.Stopped;
    			}
    		} else {
    			if(state == MovementState.Stopped) {
    				
    		    	Robot.drivetrain.Drive(this.magnitude, this.theta, 0.0, 1);
    				state = MovementState.Moving;
    			}
    		}
    		*/
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
