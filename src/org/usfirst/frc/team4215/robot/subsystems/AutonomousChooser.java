package org.usfirst.frc.team4215.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class AutonomousChooser extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	double gameplan;
	AutonomousChooser(){
		
	}
	
	public double ChooseForward() {
		
		return gameplan;
	}
	
	public double Choose(String gameData, int position, Alliance alliance){
		
		return gameplan;
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

