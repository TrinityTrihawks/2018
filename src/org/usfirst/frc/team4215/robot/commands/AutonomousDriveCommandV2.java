package org.usfirst.frc.team4215.robot.commands;

import org.usfirst.frc.team4215.robot.Robot;
import org.usfirst.frc.team4215.robot.ultrasonic.IUltrasonic;

/**
 *
 */
public class AutonomousDriveCommandV2 extends AutonomousDriveDistanceCommand {

	private IUltrasonic sensor;
	double currentAcceleration;
	double currentVelocity;

    public AutonomousDriveCommandV2(int distanceInches, double magnitude, double theta, IUltrasonic sonic) {
    		super(distanceInches, magnitude, theta);    		
		this.sensor = sonic;
    } 

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
 		if (!this.sensor.isEnabled()) {
	 		super.execute();				
	 		return;
 		}

//		velocity =  WheelType.backrightwheel.getWheel().getSelectedSensorVelocity(WheelType.backrightwheel.getId());
//		deaccel = ((displacement-velocity*time-getDistance())/Math.pow(time, 2));
//		Drive(Math.abs(deaccel), this.theta, 0, 1);	

 		double currentRange = this.sensor.getRangeMM();
		double currentDistance = Robot.drivetrain.getDistance();
		double rangeScalar = 1.0;
		double distanceScalar = 1.0;
		
		if (currentRange == IUltrasonic.ERR_DISTANCE) {
			// ignore value
		} else if (currentRange <= IUltrasonic.MIN_DISTANCE) {
			System.out.println("AutonomousDriveDistanceWithUltrasonicCommand execute stopped - range");
			rangeScalar = 0;
		} else if (currentRange < 800) {
			System.out.println("AutonomousDriveDistanceWithUltrasonicCommand execute slowed - range");
			rangeScalar = .6;
		} else if (currentRange < 1200) {
			System.out.println("AutonomousDriveDistanceWithUltrasonicCommand execute slowed - range");
			rangeScalar = .7;
		}

		if (currentDistance >= targetDistanceInches) {
			System.out.println("AutonomousDriveDistanceWithUltrasonicCommand execute stopped - distance");
			distanceScalar = 0;
		} else if (currentDistance >= targetDistanceInches * .8) {
			System.out.println("AutonomousDriveDistanceWithUltrasonicCommand execute slowed - distance");
			distanceScalar = .25;
		} else if (currentDistance >= targetDistanceInches * .7) {
			System.out.println("AutonomousDriveDistanceWithUltrasonicCommand execute slowed - distance");
			distanceScalar = .5;
		} else if (currentDistance >= targetDistanceInches * .6) {
			System.out.println("AutonomousDriveDistanceWithUltrasonicCommand execute slowed - distance");
			distanceScalar = .75;
 		} 

		double scalar = Math.min(rangeScalar, distanceScalar);
		System.out.println("SCALAR: " + scalar);

		if (scalar == 0) {
			Robot.drivetrain.Stop();
		} else if (scalar < 1.0) {
			Robot.drivetrain.Drive(this.magnitude * scalar, this.theta, 0.0, 1);			
		} else {
	 		super.execute();				
		}
		
		return;
    	}
}
