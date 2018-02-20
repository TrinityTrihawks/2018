package org.usfirst.frc.team4215.robot.commands;

import org.usfirst.frc.team4215.robot.Robot;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 *
 */
public class visionDrive extends Command {

	VisionThread visionThread;
    CvSink camera;
    GripPipelineOh pipeline;
    ListenerforVision listener;
    
    
    visionDrive() {
        // Use requires() here to declare subsystem dependencies
         requires(Robot.drivetrain);
         
         
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	this.pipeline = new GripPipelineOh();
        this.camera = CameraServer.getInstance().getVideo();

    	this.visionThread = new VisionThread(camera, pipeline, listener);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
