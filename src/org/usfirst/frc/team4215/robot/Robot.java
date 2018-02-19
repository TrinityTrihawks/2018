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
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4215.robot.commands.AutonomousDriveDistanceCommand;
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
		Left,
		Right,
		Middle,
	}
	
	enum TeamColor {
		Red,
		Blue,
	}
	

	String robotPosition;
	String robotPlan;

	SimpleCSVLogger logger = new SimpleCSVLogger();

	NetworkTableEntry entry;
	
	public static final Drivetrain drivetrain = new Drivetrain();
	public static final Intake intake = new Intake();
	public static final Lift lift = new Lift();
	
	
	AxisCamera cameraBack ;
	AxisCamera cameraFront ;
	
	
	final int IMG_WIDTH = 320;
	final int IMG_HEIGHT = 240;
	
	public static OI m_oi;

	//for choosing autonomous mode (right, left, middle)
	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();
	
	RobotPositions robotPos;
	SendableChooser<RobotPositions> posChooser = new SendableChooser<>();
	
	boolean teleop;
	TeamColor robotTeam;
	SendableChooser<TeamColor> teamChooser = new SendableChooser<>();
	
	Timer timer;
	



	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		//m_chooser.addDefault("Cross Auto Line", new DriveForward());
		
		posChooser.addDefault("Middle", RobotPositions.Middle);
		posChooser.addObject("Left", RobotPositions.Left);
		posChooser.addObject("Right", RobotPositions.Right);
		
		teamChooser.addObject("Red", TeamColor.Red);
		teamChooser.addObject("Blue", TeamColor.Blue);
		
		// chooser.addObject("My Auto", new MyAutoCommand());
		//SmartDashboard.putData("Auto mode", m_chooser);
		
		m_oi.gyro.calibrate();
	
		//sets up NetworkTable on robot side
		NetworkTableInstance inst = NetworkTableInstance.getDefault();
		NetworkTable table = inst.getTable("datatable");
		entry = table.getEntry("X");
		System.out.println("Created Network Table entry 'X'");
	
		 cameraFront = CameraServer.getInstance().addAxisCamera("Front", "10.42.15.39");
		 cameraFront.setResolution(IMG_WIDTH, IMG_HEIGHT);
		 System.out.println("Front camera initialized properly");
		 
	}
	@Override
	public void robotPeriodic() {
		SmartDashboard.putNumber("Magnitude", m_oi.getMagnitude());
		SmartDashboard.putNumber("Direction", m_oi.getTheta());
		SmartDashboard.putNumber("Rotation", m_oi.getRotation());
		SmartDashboard.putNumber("Gyro Angle", m_oi.getGyroAngle());
		SmartDashboard.putNumber("Slider", m_oi.getSlider());
		
		//System.out.println(m_oi.getMagnitude() + "   " + m_oi.getTheta() + "    " + m_oi.getRotation());
		//SmartDashboard.putNumberArray("Motor Powers", drivetrain.power);
		SmartDashboard.putNumber("X", entry.getDouble(0));

		
		for (int k = 0; k<4; k++) {
			SmartDashboard.putNumber("power" + k, drivetrain.power[k]);
		}
		drivetrain.logTalonBusVoltages();
		drivetrain.TalonOutputVoltage();
	}
	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	int k = 0;
	@Override
	public void disabledInit() {
	/*	Scheduler.getInstance().disable();
		drivetrain.Stop();
	*/
		
		//Scheduler.getInstance().removeAll();
		System.out.println("Disabled Init");
		if(teleop == true) {
			Scheduler.getInstance().removeAll();;
			teleop = false;
		}
	}

	@Override
	public void disabledPeriodic() {
		//Scheduler.getInstance().run();
		//drivetrain.Stop();
		//System.out.println("Disabled Periodic");

	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
//		this.m_autonomousCommand = m_chooser.getSelected();
		this.m_autonomousCommand = new AutonomousDriveDistanceCommand(24, 1, 0);
		robotPos = posChooser.getSelected();
		robotTeam = teamChooser.getSelected();
		
		System.out.println("Robot Position: " + robotPos);
		System.out.println("Robot Team: " + robotTeam);
		String[] ls = new String[] { "1", "1", "1", "1"};
		logger.init(ls, ls);
		
		timer.start();
		

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		
		if(robotPosition == "Left") {
			if(robotPlan == "DriveForward") {
				
			} else if(robotPlan == "DeadReckoningTurn") {
				
			} else {
				
			}
			
		} else if (robotPosition == "Right") {
			if(robotPlan == "DriveForward") {
				
			} else if(robotPlan == "DeadReckoningTurn") {
				
			} else {
				
			}
			
		} else { 
			if(robotPlan == "DriveForwardLeft") {
				
			}
			else if(robotPlan == "DriveForwardRight") {
				
			} else if(robotPlan == "VisionLeft") {
				
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
		
		double[] log = new double[13];
		log[0] = timer.get();
		log[1] = getDistance;
		
		for(int i = 0; i < 4; i++) {
			log[2 + i] = getTalonBusVoltages[i];
		}
		for(int i = 0; i < 4; i++) {
			log[6 + i] = getWheelOutputs[i];
		}
		for(int i = 0; i < 4; i++) {
			log[10 + i] = getMotorOutputCurrents[i];
		}

		logger.writeData(log);

	}
	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		m_chooser.addDefault("Default Teleop", new teleopDrive());

		
		//Scheduler.getInstance().disable();
		//drivetrain.Stop();
		System.out.println("Teleop Init");
		teleop = true;
		Scheduler.getInstance().enable();
		

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
	
	
}
