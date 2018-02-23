package org.usfirst.frc.team4215.robot.commands;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team4215.robot.Robot;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 *
 */
public class visionDrive extends Command {

    AxisCamera camera;
    //Pipeline pipeline;
    ListenerforVision listener;
    private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	// Sets Camera Image Resolution
	
	private VisionThread visionThread;			//Creates Vision Thread for future use
	private double centerX = 0.0;			//Creates the variable centerX. 
	
	private final Object imgLock = new Object();
	public Object visionStop;
    
    
    visionDrive() {
        // Use requires() here to declare subsystem dependencies
         requires(Robot.drivetrain);
         
         
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	camera = CameraServer.getInstance().addAxisCamera("10.42.15.39");
		camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
		
        
        visionThread = new VisionThread(camera, new MyVisionPipeline(), pipeline -> {
            if (!pipeline.filterContoursOutput().isEmpty()) {
                Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
                synchronized (imgLock) {
                    centerX = r.x + (r.width / 2);
                }
            }
        });
        visionThread.start();
	    
    	
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
