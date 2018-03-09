package org.usfirst.frc.team4215.robot.commandgroup;

import org.usfirst.frc.team4215.robot.commands.StrafeWithGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FarStrafeplusLLS extends CommandGroup {

    public FarStrafeplusLLS() {
        addSequential(new StrafeWithGyro(350, .5, -Math.PI/2));
        addSequential(new LeftPositionLeftScale());
    }
}
