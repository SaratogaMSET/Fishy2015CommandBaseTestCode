package org.usfirst.frc.team649.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	    private Joystick operatorJoystick;
	    public final Operator operator;

	    public OI() {
	        this.operatorJoystick = new Joystick(RobotMap.JOYSTICK_OPERATOR);
	        operator = new Operator();
	    }
	
	
	public class Operator {
		
		public boolean isRaiseToteButtonPressed() {	
			return operatorJoystick.getRawButton(5);
		}
		
		public boolean isLowerToteButtonPressed() {
			return operatorJoystick.getRawButton(6);
		}
	
		public boolean isScoreAllButtonPressed() {
			return operatorJoystick.getRawButton(7);
		}
		
		public boolean isStepButtonPressed() {
			return operatorJoystick.getRawButton(8);
		}
		
		public boolean isStoreButtonPressed() {
			return operatorJoystick.getRawButton(9);
		}
	}
}

