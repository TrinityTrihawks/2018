package org.usfirst.frc.team4215.robot.commandgroup;

import org.usfirst.frc.team4215.robot.commands.StrafeWithGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MiddleStrafeplusLLS extends CommandGroup {

    public MiddleStrafeplusLLS() {
        addSequential(new StrafeWithGyro(175, .5, -Math.PI/2));
        addSequential(new LeftPositionLeftScale());
    }
}
