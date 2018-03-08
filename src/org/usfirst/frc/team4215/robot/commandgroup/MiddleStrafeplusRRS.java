package org.usfirst.frc.team4215.robot.commandgroup;

import org.usfirst.frc.team4215.robot.commands.RightPositionRightScale;
import org.usfirst.frc.team4215.robot.commands.StrafeWithGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MiddleStrafeplusRRS extends CommandGroup {

    public MiddleStrafeplusRRS() {
        addSequential(new StrafeWithGyro(102, .5, Math.Pi/2));
        addSequential(new RightPositionRightScale());
    }
}
