
package org.usfirst.frc.team649.robot;

import org.usfirst.frc.team649.robot.commandgroups.Debug;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveForwardRotate;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveSetDistanceWithPID;
import org.usfirst.frc.team649.robot.commands.intakecommands.IntakeTote;
import org.usfirst.frc.team649.robot.commands.intakecommands.RunRoller;
import org.usfirst.frc.team649.robot.commands.lift.ChangeOffsetHeight;
import org.usfirst.frc.team649.robot.commands.lift.FinishRaiseTote;
import org.usfirst.frc.team649.robot.commands.lift.RaiseToteToIntermediateLevel;
import org.usfirst.frc.team649.robot.commands.lift.RunLift;
import org.usfirst.frc.team649.robot.subsystems.AutoWinchSubsystem;
import org.usfirst.frc.team649.robot.subsystems.CameraSubsystem;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.ContainerGrabberSubsystem;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakeLeftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakeRightSubsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */


public class FishyRobot2015 extends IterativeRobot {
	
	public NetworkTable table;

	public static OI oi;
	public static DrivetrainSubsystem drivetrainSubsystem;
	public static ChainLiftSubsystem chainLiftSubsystem;
	public static IntakeLeftSubsystem intakeLeftSubsystem;
	public static IntakeRightSubsystem intakeRightSubsystem;
	public static AutoWinchSubsystem autoWinchSubsystem;
	public static ContainerGrabberSubsystem containerGrabberSubsystem;
	public static CameraSubsystem cameraSubsystem;
	
	
	public SendableChooser autoChooser;
	public Command autoCommand;
	public String autoMode;
	public boolean driveLeftEncoderState, driveRightEncoderState, chainEncoderState;
	
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	drivetrainSubsystem = new DrivetrainSubsystem();
    	chainLiftSubsystem = new ChainLiftSubsystem();
    	intakeLeftSubsystem = new IntakeLeftSubsystem();
    	intakeRightSubsystem = new IntakeRightSubsystem();
    	autoWinchSubsystem = new AutoWinchSubsystem();
    	//containerGrabberSubsystem = new ContainerGrabberSubsystem();
    //	cameraSubsystem = new CameraSubsystem();
    	oi = new OI();
    	
    	table = NetworkTable.getTable("datatable");
    	
    	table.putString("Text Input", "if this works i will die");
    	
    	System.out.println("" + table.containsKey("Text Input"));
    	
    	autoChooser = new SendableChooser();
    	autoChooser.addObject("Debugger Mode", "debugger mode");
    	autoChooser.addObject("Winch Autonomous", "winch in totes");
    	autoChooser.addObject("Get Container and Tote", "container and tote");
    	autoChooser.addObject("Get Just Tote", "just tote");
    	//with container grabber open go right up to container, run intake, clamp on container, run chain up, get a tote, take it into autozone, just drop tote off, turn right
    	autoChooser.addObject("Do Nothing Autonomous", "none");
    	
    	//SmartDashboard.putData("Autonomous Mode", autoChooser);
    	// instantiate the command used for the autonomous period
    	//autonomousCommand = new ExampleCommand();
    	SmartDashboard.putData(Scheduler.getInstance());
    	
    	SmartDashboard.putData("Chain Lift Motor1", chainLiftSubsystem.motors[0]);
    	SmartDashboard.putData("Chain Lift Motor2", chainLiftSubsystem.motors[1]);
    	SmartDashboard.putData("Chain Lift Encoder1", chainLiftSubsystem.encoders[0]);
    	
    	
    	//idk if this works
    	//SmartDashboard.putData("Cam", (Sendable) commandBase.cameraSubsystem.cam);
    	//cam must be configured from smartdashboard
    	
    	LiveWindow.addActuator("IntakeLeftSubsystem", "roller", intakeLeftSubsystem.roller);
    	LiveWindow.addActuator("IntakeLeftSubsystem", "arm", intakeLeftSubsystem.arm);
    	
    	LiveWindow.addActuator("IntakeRightSubsystem", "roller", intakeRightSubsystem.roller);
    	LiveWindow.addActuator("IntakeRightSubsystem", "arm", intakeRightSubsystem.arm);
    	
    	LiveWindow.addActuator("ChainLiftSubsystem", "motor1", chainLiftSubsystem.motors[0]);
    	LiveWindow.addActuator("ChainLiftSubsystem", "motor2", chainLiftSubsystem.motors[1]);
    	LiveWindow.addSensor("ChainLiftSubsystem", "Encoder1", chainLiftSubsystem.encoders[0]);
    	LiveWindow.addSensor("ChainLiftSubsystem", "Encoder1", chainLiftSubsystem.encoders[1]);
    	
    
    	LiveWindow.addActuator("DrivetrainSubsystem", "leftmotor1", drivetrainSubsystem.motors[0]);
    	LiveWindow.addActuator("DrivetrainSubsystem", "leftmotor2", drivetrainSubsystem.motors[1]);
    	LiveWindow.addActuator("DrivetrainSubsystem", "rightmotor1", drivetrainSubsystem.motors[2]);
    	LiveWindow.addActuator("DrivetrainSubsystem", "rightmotor2", drivetrainSubsystem.motors[3]);
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
      
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
    	
    	//FIGURE OUT HOW TO CLEAR SMARTDASHBOARD REMOTELY
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

    }

    /**
     * This function is called periodically during operator control
     */
	public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
        SmartDashboard.putData("Encoder1", chainLiftSubsystem.encoders[0]);
        SmartDashboard.putData("Encoder2", chainLiftSubsystem.encoders[1]);
        
        new RunLift(oi.operatorJoystick.getY()).start();
        
        if (oi.operator.isIntakeButtonPressed()){
        	//new RaiseToteToIntermediateLevel(true).start();
        	new Debug().start();
        }
        
        SmartDashboard.putNumber("Setpoint Height", chainLiftSubsystem.setpointHeight);

        SmartDashboard.putNumber("offest Height", chainLiftSubsystem.offsetHeight);
        

        SmartDashboard.putNumber("Goal Height", chainLiftSubsystem.setpointHeight + chainLiftSubsystem.offsetHeight);
    }
    

	/**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	System.out.println("IN TEST MODE");
        LiveWindow.run();
        
    }
}
