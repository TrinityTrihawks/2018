package org.usfirst.frc.team4215.robot.commandgroup;

import org.usfirst.frc.team4215.robot.commands.Turn;
import org.usfirst.frc.team4215.robot.commands.liftToheight;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class liftWhileTurning extends CommandGroup {

    public liftWhileTurning(double angle) {
    	
    	// 65 degrees works now
    	addParallel(new liftToheight(60));
		addSequential(new Turn(angle, .5));
    }
}
