package org.usfirst.frc.team4215.robot.commands;

import org.usfirst.frc.team4215.robot.commandgroup.LiftSwitchHeightWhileDriving;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightPositionRightSwitch extends CommandGroup {

    public RightPositionRightSwitch() {
    	addSequential(new LiftSwitchHeightWhileDriving());
    	addSequential(new Turn(-65, 0.5));
    	addSequential(new AutonomousDriveDistanceCommand(24, 0.25, 0));
		addSequential(new RunIntake(false));	
    }
}