package org.usfirst.frc.team4215.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class liftWhileTurning extends CommandGroup {

    public liftWhileTurning(double angle) {
    	
    	// 65 degrees works now
    	addParallel(new liftToheight(67));
		addSequential(new Turn(angle, .5));
    }
}
