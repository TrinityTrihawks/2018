package org.usfirst.frc.team4215.robot.subsystems;

import org.usfirst.frc.team4215.robot.RobotMap;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

	private Victor intake1;
	private Victor intake2;
	private double intakePower = 0;
	
	/**
	 * 
	 */
	public Intake(double power) {
		intake1 = new Victor(RobotMap.intakeVictor1);
		intake2 = new Victor(RobotMap.intakeVictor2);
		
		if (power > 1)
		{
			this.intakePower = 1;			
		}
		else
		{
			this.intakePower = power;
		}
	}

	public Intake() {
		this(RobotMap.intakeDefaultPower);
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
