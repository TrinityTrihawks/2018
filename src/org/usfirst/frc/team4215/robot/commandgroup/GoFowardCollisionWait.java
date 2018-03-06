package org.usfirst.frc.team4215.robot.commandgroup;

import org.usfirst.frc.team4215.robot.Robot;
import org.usfirst.frc.team4215.robot.commands.AutonomousDriveDistanceCommand;
import org.usfirst.frc.team4215.robot.commands.AutonomousDriveCommandV2;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GoFowardCollisionWait extends CommandGroup {

    public GoFowardCollisionWait() {
		addSequential(new AutonomousDriveCommandV2(30, 0.5, 0, Robot.frontLeftUltrasonic));
    }
}
