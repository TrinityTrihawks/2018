package org.usfirst.frc.team4215.robot.subsystems;

import org.usfirst.frc.team4215.robot.Robot;
import org.usfirst.frc.team4215.robot.RobotMap;
import org.usfirst.frc.team4215.robot.commands.RunLift;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift extends Subsystem {

	Victor victor1;
	Victor victor2;
	
	AnalogInput liftSonic;
	
	double height;
		
	public Lift() {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	victor1 = new Victor(RobotMap.liftvictorOne);
	victor2 = new Victor(RobotMap.liftvictorTwo);
	this.liftSonic = new AnalogInput(RobotMap.liftSonic);
	}
	
	/**
	 * 
	 */
	public void lift(double magnitude, double theta) {
		
		if (Math.abs(theta)>(Math.PI/2)) {
			magnitude *= -1;
		}

		victor1.set(magnitude);
		victor2.set(magnitude);
	}
	
	public double liftHeight() {
		return height = (liftSonic.getVoltage()/(0.0048828)/5);
	}
	
    public void initDefaultCommand() {
        
        setDefaultCommand(new RunLift());
    }
}

