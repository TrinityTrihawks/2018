package org.usfirst.frc.team4215.robot.commandgroup;

import org.usfirst.frc.team4215.robot.commands.AutonomousDriveDistanceCommand;
import org.usfirst.frc.team4215.robot.commands.liftToheight;

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
