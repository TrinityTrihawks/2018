/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4215.robot;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4215.robot.commands.AutonomousDriveDistanceCommand;
import org.usfirst.frc.team4215.robot.commands.GoForwardTurnRight;
import org.usfirst.frc.team4215.robot.commands.LeftPositionLeftScale;
import org.usfirst.frc.team4215.robot.commands.RightPositionRightScale;
import org.usfirst.frc.team4215.robot.commands.Strafe;
import org.usfirst.frc.team4215.robot.commands.StrafeWithGyro;
import org.usfirst.frc.team4215.robot.commands.StrafewithUltrasonic;
import org.usfirst.frc.team4215.robot.commands.Turn;
import org.usfirst.frc.team4215.robot.commands.liftToheight;
import org.usfirst.frc.team4215.robot.commands.liftWhileDriving;
import org.usfirst.frc.team4215.robot.commands.teleopDrive;
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

	enum RobotPositions {
		Left, Right, Middle,
	}

	enum TeamColor {
		Red, Blue,
	}

	String robotPosition;
	String robotPlan;

	SimpleCSVLogger logger = new SimpleCSVLogger();

	NetworkTableEntry entry;

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
		m_chooser.addObject("Turn Right", new Turn(90, 0.5));
		m_chooser.addObject("Go forward and turn", new GoForwardTurnRight());
		m_chooser.addObject("Lift block", new liftToheight(40));
		m_chooser.addObject("Lift while drive", new liftWhileDriving());
		m_chooser.addObject("Strafe right 2 feet", new Strafe(120));
		m_chooser.addObject("Strafe with sonics", new StrafewithUltrasonic(120, .5, -Math.PI/2));
		m_chooser.addObject("LeftLeftScale", new LeftPositionLeftScale());
		m_chooser.addObject("RightRightScale", new RightPositionRightScale());
		m_chooser.addObject("Strafe with gyro", new StrafeWithGyro(240, 0.5, Math.PI/2));

		SmartDashboard.putData("Auto mode", m_chooser);

		posChooser.addDefault("Middle", RobotPositions.Middle);
		posChooser.addObject("Left", RobotPositions.Left);
		posChooser.addObject("Right", RobotPositions.Right);

		
		teamChooser.addObject("Red", TeamColor.Red);
		teamChooser.addObject("Blue", TeamColor.Blue);

		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
		//m_oi.gyro.reset();
		m_oi.gyro.calibrate();

		// sets up NetworkTable on robot side
		NetworkTableInstance inst = NetworkTableInstance.getDefault();
		NetworkTable table = inst.getTable("datatable");
		entry = table.getEntry("X");
		System.out.println("Created Network Table entry 'X'");

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
		SmartDashboard.putNumber("Ultrasonic", lift.liftHeight());


		// System.out.println(m_oi.getMagnitude() + " " + m_oi.getTheta() + " " +
		// m_oi.getRotation());
		// SmartDashboard.putNumberArray("Motor Powers", drivetrain.power);
		SmartDashboard.putNumber("X", entry.getDouble(0));

		for (int k = 0; k < 4; k++) {
			SmartDashboard.putNumber("power" + k, drivetrain.power[k]);
		}
		drivetrain.logTalonBusVoltages();
		drivetrain.TalonOutputVoltage();
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
		
		drivetrain.rampRate(3);
		robotPos = posChooser.getSelected();
		robotTeam = teamChooser.getSelected();

		System.out.println("Robot Position: " + robotPos);
		System.out.println("Robot Team: " + robotTeam);

		m_autonomousCommand = m_chooser.getSelected();
		System.out.print("Choosing autonomous mode: " + m_autonomousCommand.getName());

		Scheduler.getInstance().add(m_autonomousCommand);

		m_autonomousCommand.start();

		// this.m_autonomousCommand = new AutonomousDriveDistanceCommand(24, 1, 0);

		// this.m_autonomousCommand = new AutonomousDriveDistanceCommand(24, 1, 0);

		// this.autonomousTurn = new Turn(90, 0.5);

		// this.m_autonomousCommand = new GoForwardTurnRight();

		String[] ls = new String[] { "1", "1", "1", "1" };
		//logger.init(ls, ls);

		timer.start();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 * switch(autoSelected) { case "My Auto": autonomousCommand = new
		 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
		 * ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)

		/*
		 * if (m_autonomousCommand != null) { //m_autonomousCommand.start(); }
		 */

		if (robotPosition == "Left") {
			if (robotPlan == "DriveForward") {

			} else if (robotPlan == "DeadReckoningTurn") {

			} else {

			}

		} else if (robotPosition == "Right") {
			if (robotPlan == "DriveForward") {

			} else if (robotPlan == "DeadReckoningTurn") {

			} else {

			}

		} else {
			if (robotPlan == "DriveForwardLeft") {

			} else if (robotPlan == "DriveForwardRight") {

			} else if (robotPlan == "VisionLeft") {

			} else {

			}
		}

	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();

		double getDistance = drivetrain.getDistance();
		double[] getTalonBusVoltages = drivetrain.getTalonBusVoltages();
		double[] getWheelOutputs = drivetrain.getMotorOutputPercents();
		double[] getMotorOutputCurrents = drivetrain.getMotorOutputCurrents();

		double[] log = new double[14];
		log[0] = timer.get();
		log[1] = getDistance;

		for (int i = 0; i < 4; i++) {
			log[2 + i] = getTalonBusVoltages[i];
		}
		for (int i = 0; i < 4; i++) {
			log[6 + i] = getWheelOutputs[i];
		}
		for (int i = 0; i < 4; i++) {
			log[10 + i] = getMotorOutputCurrents[i];
		}

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
		drivetrain.rampRate(0);

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
	
	private Command chooseAutonomousRoutine(RobotPositions robotPos, String fieldData) {
		switch(robotPos) {
		
			case Left:
				
				switch(fieldData.charAt(1)) { //the switch
					case 'L':
						break;
					default:
						break; 
					}
				
					break;
				
			case Right:
				
				switch(fieldData.charAt(1)) { //the switch
					case 'L':
						break;
					default:
						break; 
				}
				
				break;
			
			default:
				
				switch(fieldData.charAt(1)) { //the switch
					case 'L':
						break;
					default:
						break;
				}
				
				break;
		}
		
		//just for now
		return m_autonomousCommand;

			
	}
	
	
}
