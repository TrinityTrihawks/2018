/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4215.robot;

import org.usfirst.frc.team4215.robot.subsystems.UltrasonicReader;

import edu.wpi.first.wpilibj.Joystick;

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
	
//	Joystick joystick = new Joystick(RobotMap.driveStick);
	public Joystick joystick;
	public UltrasonicReader ultrasonic;
	
	public double getMagnitude() {  
		return joystick.getMagnitude(); 
		}
	public double getTheta() { 
		return joystick.getDirectionRadians(); 
		}
	public double getRotation() { 
		return joystick.getTwist(); 
		}
	public double getSlider() {
		double SliderVal = (joystick.getRawAxis(3)+1)/2;
		return SliderVal;
	}
	
	public double getUltrasonicDistance() {
		return ultrasonic.getDistance();
	}
	
	public OI() {
		super();
		this.joystick = new Joystick(RobotMap.driveStick);
		this.ultrasonic = UltrasonicReader.Create(RobotMap.ultrasonicPortName);
	}

}
