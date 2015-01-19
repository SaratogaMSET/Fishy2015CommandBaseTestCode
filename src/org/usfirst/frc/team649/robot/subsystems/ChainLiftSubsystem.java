package org.usfirst.frc.team649.robot.subsystems;


import org.usfirst.frc.team649.robot.RobotMap;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;


/**
 *
 */
public class ChainLiftSubsystem extends PIDSubsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Victor[] motors;
	Encoder[] encoders;
	public PIDController pid;
	DigitalInput limitMax;
	DigitalInput limitReset;
	public double setpointHeight;
	public double offsetHeight;

	
	public static class PIDConstants {
		//PID
		public static final double LIFT_P_VALUE = 0.0;
		public static final double LIFT_I_VALUE = 0.0;
		public static final double LIFT_D_VALUE = 0.0;
		public static final double ENCODER_DISTANCE_PER_PULSE = 0;
		public static final double ABS_TOLERANCE = 0;
		//In inches
		public static final double STORE_TO_STEP_LEVEL_DIFFRERANCE = 5.0;
		public static final double STORE_TO_NEXT_LEVEL_DIFFRERANCE = 18.0;
		public static final double PLATFORM_DRIVE_OFFSET = 3;
		public static final double STEP_OFFSET = 8;

		//Other
		public static final double UNLOAD_TOTES_MOTOR_POWER = -.5;

	}

	public ChainLiftSubsystem() {
		super("Lift PID", PIDConstants.LIFT_P_VALUE, PIDConstants.LIFT_I_VALUE, PIDConstants.LIFT_D_VALUE);
	 	motors = new Victor[RobotMap.CHAIN_LIFT.MOTORS.length];
		for (int i = 0; i < RobotMap.CHAIN_LIFT.MOTORS.length; i++) {
            motors[i] = new Victor(RobotMap.CHAIN_LIFT.MOTORS[i]);
        }
    	pid = this.getPIDController();
    	pid.setAbsoluteTolerance(PIDConstants.ABS_TOLERANCE);
    	
    	//TODO: ALTER FOR DEFNED NUM OF ENCODERS
    	encoders = new Encoder[RobotMap.CHAIN_LIFT.ENCODERS.length / 2];
        for (int x = 0; x < RobotMap.CHAIN_LIFT.ENCODERS.length; x += 2) {
            encoders[x / 2] = new Encoder(RobotMap.CHAIN_LIFT.ENCODERS[x], RobotMap.CHAIN_LIFT.ENCODERS[x + 1], x == 0, EncodingType.k2X);
            encoders[x / 2].setDistancePerPulse(PIDConstants.ENCODER_DISTANCE_PER_PULSE);
        }
        
        
        limitMax = new DigitalInput(RobotMap.CHAIN_LIFT.MAX_LIM_SWITCH);
        limitReset = new DigitalInput(RobotMap.CHAIN_LIFT.RESET_LIM_SWITCH);
        setpointHeight = 0;
        offsetHeight = 3;
    }
	
    public void setPower(double power) {
        for (int i =0; i < motors.length; i++) {
            motors[i].set(power);
        }
    }
    
    public boolean isMaxLimitPressed() {
    	return limitMax.get();
    }
    
    public boolean isResetLimitPressed() {
    	return limitReset.get();
    }
    
    public double getHeight() {
    	int numEncoders = encoders.length;
        double totalVal = 0;
        for (int i = 0; i < numEncoders; i++) {
            totalVal += encoders[i].getDistance();
        }
        return totalVal / numEncoders;    	
    }

    public void resetEncoders() {
        for (int x = 0; x < encoders.length; x++) {
            encoders[x].reset();
        }
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return this.getHeight();
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		this.setPower(output);
	}

}

