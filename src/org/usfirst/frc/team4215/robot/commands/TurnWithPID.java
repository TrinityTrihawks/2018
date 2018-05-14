package org.usfirst.frc.team4215.robot.commands;

import org.usfirst.frc.team4215.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnWithPID extends Command {
	
    private double desiredTurn;
	private double speed;
	
	private boolean runOnce = true;
	private double initialGyroValue;


    public TurnWithPID(double desiredTurn) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	requires(Robot.drivetrain);
    	
    	this.desiredTurn = desiredTurn;
    }


    // Called just before this Command runs the first time
    protected void initialize() {
    	//Robot.m_oi.gyro.reset();
    	System.out.println("TurnWithPID command: initializing");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(runOnce) {
    		
    		initialGyroValue = Robot.drivetrain.getPigeonYaw();
    		
    		double turnDirection = desiredTurn / Math.abs(desiredTurn);
    		
    		//Robot.drivetrain.Drive(0, 0, turnDirection*speed, 1);
    		Robot.drivetrain.setSetpointRelative(desiredTurn);
    		System.out.println("Enabling the PIDController");
    		Robot.drivetrain.enable();
    		
    		runOnce = false;
    		System.out.println("Turn Direction: " + turnDirection);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {    	
	    	//as soon as button is released i.e. false value, then end command
    		if(Robot.drivetrain.onTarget())
    		{
    			System.out.println("TurnWithPID finished");

    			return true;
    		} else {
    			return false;
    		}
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.disable();
    	Robot.drivetrain.Stop();
    	System.out.println("TurnWithPID command: ended");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
