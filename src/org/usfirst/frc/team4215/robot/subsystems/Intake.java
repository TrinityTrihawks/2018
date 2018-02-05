package org.usfirst.frc.team4215.robot.subsystems;

import org.usfirst.frc.team4215.robot.RobotMap;
import org.usfirst.frc.team4215.robot.commands.RunIntake;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {
	private Victor intake;
	
	private double intakePower = .5;
	
	public Intake() {
		intake = new Victor(RobotMap.intakeVictor);
	}
	
	public void setIntakeOn () {
		intake.set(intakePower);
	}
	
	public void setIntakeOff () {
		intake.set(0);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
