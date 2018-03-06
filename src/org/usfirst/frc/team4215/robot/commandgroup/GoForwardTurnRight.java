package org.usfirst.frc.team4215.robot.commandgroup;

import org.usfirst.frc.team4215.robot.commands.AutonomousDriveDistanceCommand;
import org.usfirst.frc.team4215.robot.commands.Turn;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GoForwardTurnRight extends CommandGroup{
	
	public GoForwardTurnRight() {
		addSequential(new AutonomousDriveDistanceCommand(250, 0.5, 0));
		addSequential(new Turn(90, 0.5));
	}
}
