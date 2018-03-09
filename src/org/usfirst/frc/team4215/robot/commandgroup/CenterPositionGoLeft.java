package org.usfirst.frc.team4215.robot.commandgroup;

import org.usfirst.frc.team4215.robot.RobotMap;
import org.usfirst.frc.team4215.robot.commands.AutonomousDriveDistanceCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterPositionGoLeft extends CommandGroup {

    public CenterPositionGoLeft() {
    	//addParallel(new LiftSwitchHeightWhileDriving());

    	addSequential(new AutonomousDriveDistanceCommand(RobotMap.middleBasicAutoDistance, 0.5, -1*Math.PI/4));
    }
}
