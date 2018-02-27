package org.usfirst.frc.team4215.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightPositionRightScale extends CommandGroup {

    public RightPositionRightScale() {
        addSequential(new liftWhileDriving());
        addSequential(new liftWhileTurning(-65));
        addSequential(new AutonomousDriveDistanceCommand(8, 0.25, 0));
		addSequential(new RunIntake(false));
    }
}