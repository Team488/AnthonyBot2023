package competition.operator_interface;

import javax.inject.Inject;
import javax.inject.Singleton;

import competition.subsystems.collector.CollectorSubsystem;
import competition.subsystems.collector.commands.EjectCollectorCommand;
import competition.subsystems.collector.commands.ExtendCollectorCommand;
import competition.subsystems.collector.commands.ExtendIntakeAndRetractIfHasPieceCommand;
import competition.subsystems.collector.commands.IntakeCollectorCommand;
import competition.subsystems.collector.commands.RetractCollectorCommand;
import competition.subsystems.collector.commands.StopCollectorCommand;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import xbot.common.controls.sensors.XXboxController.XboxButton;
import xbot.common.subsystems.pose.commands.SetRobotHeadingCommand;

/**
 * Maps operator interface buttons to commands
 */
@Singleton
public class OperatorCommandMap {

    @Inject
    public OperatorCommandMap() {}
    
    // Example for setting up a command to fire when a button is pressed:
    @Inject
    public void setupMyCommands(
            OperatorInterface operatorInterface,
            SetRobotHeadingCommand resetHeading,
            RetractCollectorCommand retractCollectorCommandForIntake,
            RetractCollectorCommand retractCollectorCommandForStop,
            IntakeCollectorCommand intakeCollectorCommand,
            EjectCollectorCommand ejectCollectorCommand,
            StopCollectorCommand stopCollectorCommand,
            CollectorSubsystem collector,
            ExtendIntakeAndRetractIfHasPieceCommand extendIntakeAndRetractIfHasPieceCommand,
            IntakeCollectorCommand intakeCollectorCommandForRetract,
            StopCollectorCommand stopCollectorCommandForRetract)   {
        resetHeading.setHeadingToApply(90);
        operatorInterface.gamepad.getifAvailable(1).onTrue(resetHeading);


        
        // only runs motors
        operatorInterface.gamepad.getXboxButton(XboxButton.A).onTrue(intakeCollectorCommand);
        operatorInterface.gamepad.getXboxButton(XboxButton.A).onFalse(stopCollectorCommand);
        operatorInterface.gamepad.getXboxButton(XboxButton.B).onTrue(ejectCollectorCommand);
        operatorInterface.gamepad.getXboxButton(XboxButton.B).onFalse(stopCollectorCommand);
        operatorInterface.gamepad.getXboxButton(XboxButton.X).onTrue(stopCollectorCommand);

        
        // extends and runs intake, retracts if has piece
        operatorInterface.gamepad.getXboxButton(XboxButton.RightTrigger).onTrue(extendIntakeAndRetractIfHasPieceCommand);
        
        ParallelCommandGroup retractWithIntake = retractCollectorCommandForIntake.alongWith(intakeCollectorCommandForRetract);
        ParallelCommandGroup retractWithStop = retractCollectorCommandForStop.alongWith(stopCollectorCommandForRetract);
        ConditionalCommand retract = new ConditionalCommand(retractWithIntake, retractWithStop, () -> collector.hasGamePiece());

        operatorInterface.gamepad.getXboxButton(XboxButton.RightTrigger).onFalse(retract);
        
      }
}
