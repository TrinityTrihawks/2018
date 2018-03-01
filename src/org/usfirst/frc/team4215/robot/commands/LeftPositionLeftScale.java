package org.usfirst.frc.team4215.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftPositionLeftScale extends CommandGroup {

    public LeftPositionLeftScale() {
        addSequential(new liftWhileDriving());
    	addSequential(new liftWhileTurning(65));
    	addSequential(new AutonomousDriveDistanceCommand(8, 0.25, 0));
		addSequential(new RunIntake(false));	
    }
}
