package competition.operator_interface;

import javax.inject.Inject;
import javax.inject.Singleton;

import competition.subsystems.collector.CollectorSubsystem;
import competition.subsystems.collector.commands.EjectCollectorCommand;
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
    public void setupCollectorCommands(
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
        operatorInterface.operatorGamepad.getifAvailable(1).onTrue(resetHeading);


        
        // only runs motors
        operatorInterface.operatorGamepad.getXboxButton(XboxButton.A).onTrue(intakeCollectorCommand);
        operatorInterface.operatorGamepad.getXboxButton(XboxButton.A).onFalse(stopCollectorCommand);
        operatorInterface.operatorGamepad.getXboxButton(XboxButton.B).onTrue(ejectCollectorCommand);
        operatorInterface.operatorGamepad.getXboxButton(XboxButton.B).onFalse(stopCollectorCommand);
        operatorInterface.operatorGamepad.getXboxButton(XboxButton.X).onTrue(stopCollectorCommand);
        operatorInterface.operatorGamepad.getXboxButton(XboxButton.LeftTrigger).onTrue(ejectCollectorCommand);
        operatorInterface.operatorGamepad.getXboxButton(XboxButton.LeftTrigger).onFalse(stopCollectorCommand);

        
        // extends and runs intake, retracts if has piece
        operatorInterface.operatorGamepad.getXboxButton(XboxButton.RightTrigger).onTrue(extendIntakeAndRetractIfHasPieceCommand);
        
        ParallelCommandGroup retractWithIntake = retractCollectorCommandForIntake.alongWith(intakeCollectorCommandForRetract);
        ParallelCommandGroup retractWithStop = retractCollectorCommandForStop.alongWith(stopCollectorCommandForRetract);
        ConditionalCommand retract = new ConditionalCommand(retractWithIntake, retractWithStop, () -> collector.hasGamePiece());

        operatorInterface.operatorGamepad.getXboxButton(XboxButton.RightTrigger).onFalse(retract);
        
      }

}
