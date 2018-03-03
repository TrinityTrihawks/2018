package org.usfirst.frc.team4215.robot.commands;

import org.usfirst.frc.team4215.robot.Robot;
import org.usfirst.frc.team4215.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class liftToheight extends Command {
	
	private boolean runOnce = true;
	private boolean completed = false;
	double height;

    public liftToheight(double height) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.lift);
        this.height = height;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (this.runOnce) {
    		Robot.lift.lift(RobotMap.liftSpeed, 0);
    		runOnce = false;
    		System.out.println("Lifting");
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.lift.liftHeight() < height) {
            return false;

    	}else {
    		System.out.println("Lift finished");
    		return true;
    		
    	}
    	
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lift.lift(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
