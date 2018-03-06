/**
 * 
 */
package org.usfirst.frc.team4215.robot.ultrasonic;

import edu.wpi.first.wpilibj.Ultrasonic;

/**
 * @author douglas
 *
 */
public class UltrasonicReaderAnalog implements IUltrasonic {
	
	private Ultrasonic reader;

	public UltrasonicReaderAnalog(Ultrasonic reader)
	{
		this.reader = reader;
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team4215.robot.ultrasonic.IUltrasonic#getRangeInches()
	 */
	@Override
	public double getRangeInches() {
		return this.reader.getRangeInches();
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team4215.robot.ultrasonic.IUltrasonic#getRangeInches()
	 */
	@Override
	public double getRangeMM() {
		return this.reader.getRangeMM();
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team4215.robot.ultrasonic.IUltrasonic#getName()
	 */
	@Override
	public String getName() {
		return this.reader.getName();
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team4215.robot.ultrasonic.IUltrasonic#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return this.reader.isEnabled();
	}

}
