package org.usfirst.frc.team4215.robot.commandgroup;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class NoMovement extends CommandGroup {

    public NoMovement() {
        addSequential(new noMovement);
    }
}
