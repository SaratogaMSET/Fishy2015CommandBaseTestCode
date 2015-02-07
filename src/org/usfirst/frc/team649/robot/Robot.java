
package org.usfirst.frc.team649.robot;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team649.robot.commands.CommandBase;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	
public static CommandBase commandBase = new CommandBase();
	
	public void robotInit() {
        // instantiate the command used for the autonomous period
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        // schedule the autonomous command (example)
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
       // commandBase.driveForwardRotate(commandBase.oi.driver.getDriveForward(), commandBase.oi.driver.getDriveRotation()).start();
        if(commandBase.oi.operator.isIntakeButtonPressed()) {
        	commandBase.turnWithPIDCommand(90).start();
        }
        if(commandBase.oi.operator.isScoreAllButtonPressed()) {
        	commandBase.driveForwardRotate(1, 0).start();
        }
        if(commandBase.oi.operator.isStepButtonPressed()) {
        	commandBase.driveSetDistanceWithPID(120).start();
        }
        SmartDashboard.putNumber("Gyro", commandBase.drivetrainSubsystem.gyro.getAngle());
        SmartDashboard.putNumber("Enc right", commandBase.drivetrainSubsystem.encoders[0].getDistance());
        SmartDashboard.putNumber("Enc left", commandBase.drivetrainSubsystem.encoders[1].getDistance());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
