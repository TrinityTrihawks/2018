package org.usfirst.frc.team4215.robot.commandgroup;

import org.usfirst.frc.team4215.robot.commands.AutonomousDriveDistanceCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterPositionGoRight extends CommandGroup {

    public CenterPositionGoRight() {
    	addSequential(new AutonomousDriveDistanceCommand(48, 0.5, Math.PI/4));

    }
}
