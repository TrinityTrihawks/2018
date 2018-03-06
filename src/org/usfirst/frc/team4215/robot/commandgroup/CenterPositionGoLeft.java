package org.usfirst.frc.team4215.robot.commandgroup;

import org.usfirst.frc.team4215.robot.commands.AutonomousDriveDistanceCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterPositionGoLeft extends CommandGroup {

    public CenterPositionGoLeft() {
    	addSequential(new AutonomousDriveDistanceCommand(48, 0.5, -1*Math.PI/4));
    }
}
