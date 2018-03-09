package org.usfirst.frc.team4215.robot.commandgroup;

import org.usfirst.frc.team4215.robot.commands.RightPositionRightScale;
import org.usfirst.frc.team4215.robot.commands.StrafeWithGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FarStrafePlusRRS extends CommandGroup {

    public FarStrafePlusRRS() {
        addSequential(new StrafeWithGyro(350, .5, Math.PI/2));
        addSequential(new RightPositionRightScale());
    }
}
