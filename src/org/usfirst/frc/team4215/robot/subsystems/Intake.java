package org.usfirst.frc.team4215.robot.subsystems;

import org.usfirst.frc.team4215.robot.RobotMap;
import org.usfirst.frc.team4215.robot.commands.RunIntake;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {
	private Victor intake1;
	private Victor intake2;
	
	private double intakePower = .5;
	
	/**
	 * 
	 */
	public Intake() {
		intake1 = new Victor(RobotMap.intakeVictor1);
		intake2 = new Victor(RobotMap.intakeVictor2);
	}
	
	public void setIntakeOn () {
		intake1.set(intakePower);
		intake2.set(intakePower);
	}
	
	public void setIntakeOff () {
		intake1.set(0);
		intake2.set(0);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
