package org.usfirst.frc.team649.robot.commands;

import org.usfirst.frc.team649.robot.OI;
import org.usfirst.frc.team649.robot.commands.lift.ChangeLevelOfTotes;
import org.usfirst.frc.team649.robot.commands.lift.ChangeOffsetHeight;
import org.usfirst.frc.team649.robot.commands.lift.ScoreAllTotesAndResetEncoders;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CommandBase {

	public OI oi = new OI();
	public ChainLiftSubsystem chainLiftSubsystem = new ChainLiftSubsystem();

	public CommandBase() {

	}

	public Command changeLevelOfTotes(boolean up) {
		return new ChangeLevelOfTotes(up);
	}

	public Command changeOffsetHeight(boolean storeHeight) {
		return new ChangeOffsetHeight(storeHeight);
	}

	public Command scoreAllTotesAndResetEncoders() {
		return new ScoreAllTotesAndResetEncoders();
	}

}
