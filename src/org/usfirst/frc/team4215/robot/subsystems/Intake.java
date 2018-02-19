package org.usfirst.frc.team4215.robot.subsystems;

import org.usfirst.frc.team4215.robot.RobotMap;
import org.usfirst.frc.team4215.robot.commands.RunIntake;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {
	private Victor intake1;
	private Victor intake2;
	
	private int reverse;
	
	private double intakePower = .5;
	
	/**
	 * 
	 */
	public Intake() {
		intake1 = new Victor(RobotMap.intakeVictor1);
		intake2 = new Victor(RobotMap.intakeVictor2);
	}
	
	public void setIntakeOn (boolean toggle) {
		if (toggle == true){
			reverse = 1;
		} else{
			reverse = -1;
		}
		
		intake1.set(intakePower*reverse);
		intake2.set(intakePower*reverse);
		System.out.println("Set Intake on");
	}
	
	public void setIntakeOff () {
		intake1.set(0);
		intake2.set(0);
		System.out.println("Set Intake off");

	}

	@Override
	protected void initDefaultCommand() {
		
	}
}
