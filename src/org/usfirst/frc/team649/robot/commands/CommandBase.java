package org.usfirst.frc.team649.robot.commands;

import org.usfirst.frc.team649.robot.OI;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveForwardRotate;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveSetDistanceWithPID;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.TurnWithPIDCommand;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CommandBase {

	public OI oi = new OI();
	//public ChainLiftSubsystem chainLiftSubsystem = new ChainLiftSubsystem();
	public DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();
	
	public CommandBase() {

	}

	public Command driveForwardRotate(double fwd, double rot) {
		return new DriveForwardRotate(fwd, rot);
	}
	
	public Command turnWithPIDCommand(double setPoint) {
		return new TurnWithPIDCommand(setPoint);
	}
	
	public Command driveSetDistanceWithPID(double distance) {
		System.out.println("called level 1");
		return new DriveSetDistanceWithPID(distance);
	}
}
