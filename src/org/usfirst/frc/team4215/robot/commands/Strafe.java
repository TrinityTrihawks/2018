package org.usfirst.frc.team4215.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Strafe extends CommandGroup {

    public Strafe(int distanceInches) {
    	addSequential(new AutonomousDriveDistanceCommand(distanceInches, 0.5, Math.PI/2));
    }
}
