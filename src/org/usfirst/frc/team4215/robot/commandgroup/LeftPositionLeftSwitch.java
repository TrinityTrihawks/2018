package org.usfirst.frc.team4215.robot.commandgroup;

import org.usfirst.frc.team4215.robot.commands.AutonomousDriveDistanceCommand;
import org.usfirst.frc.team4215.robot.commands.RunIntake;
import org.usfirst.frc.team4215.robot.commands.Turn;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftPositionLeftSwitch extends CommandGroup {
	
	public LeftPositionLeftSwitch() {
    	addSequential(new LiftSwitchHeightWhileDriving());
    	addSequential(new Turn(65, 0.5));
    	addSequential(new AutonomousDriveDistanceCommand(24, 0.25, 0));
		addSequential(new RunIntake(false));	
	}
	
}