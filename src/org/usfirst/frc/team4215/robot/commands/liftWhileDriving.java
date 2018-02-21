package org.usfirst.frc.team4215.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class liftWhileDriving extends CommandGroup {

    public liftWhileDriving() {
    	addParallel(new liftToheight(40));
		addSequential(new AutonomousDriveDistanceCommand(256, 0.5, 0));		
			
    }
}
