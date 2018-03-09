package org.usfirst.frc.team4215.robot.commandgroup;

import org.usfirst.frc.team4215.robot.commands.AutonomousDriveDistanceCommand;
import org.usfirst.frc.team4215.robot.commands.RunIntake;
import org.usfirst.frc.team4215.robot.commands.StrafeWithGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class amendedfarsideRight extends CommandGroup {

    public amendedfarsideRight() {
        addSequential(new StrafeWithGyro(350, .5, Math.PI/2));
        addSequential(new liftWhileDriving());
        addSequential(new liftWhileTurning(-65));
        //addSequential(new AutonomousDriveDistanceCommand(8, 0.25, 0));
    }
}
