/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4215.robot;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.HashMap;

import org.usfirst.frc.team4215.robot.commandgroup.*;
import org.usfirst.frc.team4215.robot.commands.*;
import org.usfirst.frc.team4215.robot.subsystems.Drivetrain;
import org.usfirst.frc.team4215.robot.subsystems.Intake;
import org.usfirst.frc.team4215.robot.subsystems.Lift;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */

public class Robot extends TimedRobot {

	public enum RobotPositions {
		Left, Right, Middle,
	}

	public enum TeamColor {
		Red, Blue,
	}
	public enum Complexity {
		Do_Not_Move, Basic, Advanced,
	}

	String robotPosition;
	String robotPlan;
	
	char targetPosition;

	SimpleCSVLogger logger = new SimpleCSVLogger();
	
	public static final Drivetrain drivetrain = new Drivetrain();
	public static final Intake intake = new Intake();
	public static final Lift lift = new Lift();
	
	AxisCamera camera;

	final int IMG_WIDTH = 320;
	final int IMG_HEIGHT = 240;

	public static OI m_oi;

	// for choosing autonomous mode (right, left, middle)
	Command m_autonomousCommand;

	SendableChooser<Command> m_chooser = new SendableChooser<>();

	RobotPositions robotPos;
	SendableChooser<RobotPositions> posChooser = new SendableChooser<>();


	boolean teleop;
	TeamColor robotTeam;
	Complexity complexity;
	SendableChooser<Complexity> complex_chooser = new SendableChooser<>();

	SendableChooser<TeamColor> teamChooser = new SendableChooser<>();

	Timer timer = new Timer();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		// m_chooser.addDefault("Cross Auto Line", new DriveForward());

		m_chooser.addDefault("Drive forward", new AutonomousDriveDistanceCommand(24, 1, 0));
		m_chooser.addObject("Turn Right", new Turn(150, 0.5));
		m_chooser.addObject("Go forward and turn", new GoForwardTurnRight());
		m_chooser.addObject("Lift block", new liftToheight(40));
		m_chooser.addObject("Lift while drive", new liftWhileDriving());
		m_chooser.addObject("Strafe right 5 feet", new Strafe(60));
		m_chooser.addObject("Strafe right 5 feet gyro", new StrafeWithGyro(60, .5, (Math.PI/2)+(Math.PI/10)));

		m_chooser.addObject("Strafe left 5 feet with gyro", new StrafeWithGyro(60, .5, (-1*Math.PI/2) + (Math.PI/15)));
		m_chooser.addObject("Strafe with sonics", new StrafewithUltrasonic(120, .5, -Math.PI/2));
		m_chooser.addObject("LeftLeftScale", new LeftPositionLeftScale());
		m_chooser.addObject("RightRightScale", new RightPositionRightScale());
		m_chooser.addObject("Strafe with gyro", new StrafeWithGyro(240, 0.5, Math.PI/2));
		m_chooser.addObject("Go diagonally right", new CenterPositionGoRight());
		m_chooser.addObject("Go diagonally left", new CenterPositionGoLeft());
		m_chooser.addObject("LeftLeft Switch", new LeftPositionLeftSwitch());
		m_chooser.addObject("RightRight Switch", new RightPositionRightSwitch());
		

		SmartDashboard.putData("Auto mode", m_chooser);

		posChooser.addDefault("Middle", RobotPositions.Middle);
		posChooser.addObject("Left", RobotPositions.Left);
		posChooser.addObject("Right", RobotPositions.Right);

		
		teamChooser.addObject("Red", TeamColor.Red);
		teamChooser.addObject("Blue", TeamColor.Blue);
		
		complex_chooser.addObject("Do Not Move", Complexity.Do_Not_Move);
		complex_chooser.addDefault("Basic", Complexity.Basic);
		complex_chooser.addObject("Advanced", Complexity.Advanced);
	
		SmartDashboard.putData("Auto mode", m_chooser);
		SmartDashboard.putData("Complexity", complex_chooser);
		SmartDashboard.putData("Team Chooser", teamChooser);
		SmartDashboard.putData("Position", posChooser);

		m_oi.gyro.reset();
		m_oi.gyro.calibrate();

		// TODO: check if camera defined before adding to CameraServer
		camera = CameraServer.getInstance().addAxisCamera("Front", "10.42.15.39");
		camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
		System.out.println("Camera initialized properly");
		
	}

	@Override
	public void robotPeriodic() {
		SmartDashboard.putNumber("Magnitude", m_oi.getMagnitude());
		SmartDashboard.putNumber("Direction", m_oi.getTheta());
		SmartDashboard.putNumber("Rotation", m_oi.getRotation());
		SmartDashboard.putNumber("Gyro Angle", m_oi.getGyroAngle());
		SmartDashboard.putNumber("Slider", m_oi.getSlider());
		SmartDashboard.putNumber("Lift : Ultrasonic", lift.liftHeight());

		drivetrain.logTalonMotorOutputPercent();
		drivetrain.logTalonBusVoltages();
		
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
		Scheduler.getInstance().removeAll();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();

	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		Alliance alliance = DriverStation.getInstance().getAlliance();
		
		char switchPosition = gameData.charAt(0);
		char scalePosition = gameData.charAt(1);
		
		System.out.println("switchPosition is..." + switchPosition);
		System.out.println("scalePosition is..." + scalePosition);
						
		drivetrain.setRampRate(3);
		
		robotPos = posChooser.getSelected();
		robotTeam = teamChooser.getSelected();

		System.out.println("Robot Position: " + robotPos);
		System.out.println("Robot Team: " + robotTeam);
		
		m_autonomousCommand = m_chooser.getSelected();

		//m_autonomousCommand = new GoFowardCollisionWait();
		System.out.print("Choosing autonomous mode: " + m_autonomousCommand.getName());

		Scheduler.getInstance().add(m_autonomousCommand);

		m_autonomousCommand.start();

		String[] ls = new String[] { "1", "1", "1", "1" };
		//logger.init(ls, ls);

		timer.start();

	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();

//		double getDistance = drivetrain.getDistance();
//		double[] getTalonBusVoltages = drivetrain.getTalonBusVoltages();
//		double[] getWheelOutputs = drivetrain.getMotorOutputPercents();
//		double[] getMotorOutputCurrents = drivetrain.getMotorOutputCurrents();
//
//		double[] log = new double[14];
//		log[0] = timer.get();
//		log[1] = getDistance;
//
//		for (int i = 0; i < 4; i++) {
//			log[2 + i] = getTalonBusVoltages[i];
//		}
//		for (int i = 0; i < 4; i++) {
//			log[6 + i] = getWheelOutputs[i];
//		}
//		for (int i = 0; i < 4; i++) {
//			log[10 + i] = getMotorOutputCurrents[i];
//		}

		//logger.writeData(log);

	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.

		// System.out.println("Autonomous command is canceled: " +
		// m_autonomousCommand.isCanceled());

		Scheduler.getInstance().removeAll();

		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		m_chooser.addDefault("Default Teleop", new teleopDrive());

		// Scheduler.getInstance().disable();
		// drivetrain.Stop();
		System.out.println("Teleop Init");
		teleop = true;
		Scheduler.getInstance().enable();
		drivetrain.setRampRate(0);

	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {

		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		System.out.println("Test Periodic");

	}
	
	private CommandGroup chooseAutonomousRoutine(Complexity complexity, 
			RobotPositions robotPos, char switchPosition, char scalePosition, 
			TeamColor robotTeam) {
		
		HashMap<String, CommandGroup> hmap = new HashMap<String, CommandGroup>();
		
		hmap.put("Basic_Left_Blue_L", new DriveForward());
		hmap.put("Basic_Right_Blue_R", new DriveForward());
		
		hmap.put("Basic_Left_Red_L", new DriveForward());
		hmap.put("Basic_Right_Red_R", new DriveForward());
		
		hmap.put("Basic_Middle_Blue_L", new CenterPositionGoRight());
		hmap.put("Basic_Middle_Blue_R", new CenterPositionGoLeft());
		
		hmap.put("Basic_Middle_Red_L", new CenterPositionGoRight());
		hmap.put("Basic_Middle_Red_R", new CenterPositionGoLeft());
		
		hmap.put("Advanced_Left_Blue_L", new LeftPositionLeftScale());
		hmap.put("Advanced_Right_Blue_R", new RightPositionRightScale());
		
		hmap.put("Advanced_Left_Red_L", new LeftPositionLeftScale());
		hmap.put("Advanced_Right_Red_R", new RightPositionRightScale());
		
		hmap.put("Advanced_Middle_Blue_L", new MiddleStrafeplusLLS());
		hmap.put("Advanced_Middle_Blue_R", new MiddleStrafeplusRRS());
		
		hmap.put("Advanced_Middle_Red_L", new MiddleStrafeplusLLS());
		hmap.put("Advanced_Middle_Red_R", new MiddleStrafeplusRRS());
		
		hmap.put("Advanced_Right_Blue_L", new FarStrafeplusLLS());
		hmap.put("Advanced_Left_Blue_R", new FarStrafePlusRRS());
		
		hmap.put("Advanced_Right_Red_L", new FarStrafeplusLLS());
		hmap.put("Advanced_Left_Red_R", new FarStrafePlusRRS());


		if(complexity.toString() == "Basic") {
			targetPosition = switchPosition;
		} else if(complexity.toString() == "Advanced"){
			targetPosition = scalePosition;
		} else {
			return new NoMovement();
		}
		
		
		String key = String.format("{0}_{1}_{2}_{3}", complexity.toString(), robotPos.toString(), robotTeam.toString(), targetPosition);
		
		if (hmap.containsKey(key)) {
			return hmap.get(key);
		} else {
			return new NoMovement();
		}
		
		
	}
}
