/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import org.usfirst.frc.team4215.robot.commands.RunIntake;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
	protected Joystick joystick;
	protected Joystick adjutantJoystick;
	protected AnalogGyro gyro;
	protected JoystickButton intakeButton;
	
	protected Boolean applyCorrectedPolarCoordinates = true;
	protected Boolean applySlider = true;

	private final double TWOPI = Math.PI * 2; 
	
	/**
	 * Magnitude from the drive Joystick
	 * @return Magnitude
	 */
	public double getMagnitude() {
		if (this.applySlider) {
			//return joystick.getMagnitude() * this.getSlider(); 
		}
		
		return joystick.getMagnitude(); 
	}
	
	/**
	 * Theta value (radians) from the drive Joystick
	 * @return Theta
	 */
	public double getTheta() { 
		
		double theta = joystick.getDirectionRadians();

		if (this.applyCorrectedPolarCoordinates) {
			if (theta < 0) {
				theta = TWOPI - Math.abs(theta);
			}
			
			theta += (Math.PI / 2);
		}
		
		return theta; 
	}

	/**
	 * Rotation value from the drive Joystick
	 * @return Rotations
	 * {-1 to 1}
	 */
	public double getRotation() {
		return joystick.getTwist(); 
	}
	
	/**
	 * Slider value from the drive Joystick
	 * @return Slider
	 * {0 to 1}
	 */
	protected double getSlider() {
		double SliderVal = ((joystick.getRawAxis(3)-1)/2)*-1;
		return SliderVal;
	}
	
	/**
	 *  Gyro value from the gyro
	 * @return Gyro Angle
	 */
	public double getGyroAngle(){
		return gyro.getAngle() ;	
	}
	
	/**
	 * intakeButton value from the drive Joystick
	 * @return True or False
	 */
	public boolean getIntakeButtonValue() {
		return joystick.getRawButton(RobotMap.intakeButton);
	}
	
	/**
	 * Magnitude value from adjutant Joystick
	 * @return adjutant Magnitude
	 */
	public double getliftPower() {
		return adjutantJoystick.getMagnitude();
	}
	
	public boolean getLiftButtonValue() {
		return adjutantJoystick.getRawButton(RobotMap.lifttoggleButton);
	}
	
	public OI() {
		super();
		//instantiates joystick and intake button
		this.joystick = new Joystick(RobotMap.driveStick);
		this.intakeButton = new JoystickButton(joystick, RobotMap.intakeButton);
		
		this.adjutantJoystick = new Joystick(RobotMap.adjutantJoystick);

	
		//instantiates the gyro
	   this.gyro = new AnalogGyro(RobotMap.gyroChannel); 
	   this.gyro.initGyro();
	   		
	    //starts a new command based on input
		//this.intakeButton.whileHeld(new RunIntake());
		this.intakeButton.whenPressed(new RunIntake());
	}

	/**
	 * @return the applyCorrectedPolarCoordinates
	 */
	public Boolean getApplyCorrectedPolarCoordinates() {
		return this.applyCorrectedPolarCoordinates;
	}

	/**
	 * @param applyCorrectedPolarCoordinates the applyCorrectedPolarCoordinates to set
	 */
	public void setApplyCorrectedPolarCoordinates(Boolean applyCorrectedPolarCoordinates) {
		this.applyCorrectedPolarCoordinates = applyCorrectedPolarCoordinates;
	}

	/**
	 * @return the applySlider
	 */
	public Boolean getApplySlider() {
		return this.applySlider;
	}

	/**
	 * @param applySlider the applySlider to set
	 */
	public void setApplySlider(Boolean applySlider) {
		this.applySlider = applySlider;
	}

}
