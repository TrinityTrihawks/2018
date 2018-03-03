package org.usfirst.frc.team4215.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LiftSwitchHeightWhileDriving extends CommandGroup {

    public LiftSwitchHeightWhileDriving() {
    	addParallel(new liftToheight(24));
		addSequential(new AutonomousDriveDistanceCommand(168, 0.5, 0));

    }
}