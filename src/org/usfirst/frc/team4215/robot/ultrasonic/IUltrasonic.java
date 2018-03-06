/**
 * 
 */
package org.usfirst.frc.team4215.robot.ultrasonic;

/**
 * @author MrE
 *
 */
public interface IUltrasonic {

	public static final double MIN_DISTANCE = 300.0;
	public static final double MAX_DISTANCE = 5000.0;
	public static final double ERR_DISTANCE = 0.0;

	public boolean isEnabled();
	public double getRangeInches();
	public double getRangeMM();
	public String getName();
}
