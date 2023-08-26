package competition.operator_interface;

import javax.inject.Inject;
import javax.inject.Singleton;

import competition.subsystems.collector.CollectorSubsystem;
import competition.subsystems.collector.commands.EjectCollectorCommand;
import competition.subsystems.collector.commands.ExtendCollectorCommand;
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
            ExtendCollectorCommand extendCollectorCommandForIntake, 
            RetractCollectorCommand retractCollectorCommandForIntake,
            IntakeCollectorCommand intakeCollectorCommand,
            EjectCollectorCommand ejectCollectorCommand,
            StopCollectorCommand stopCollectorCommand,
            StopCollectorCommand stopCollectorCommandForRetract,
            RetractCollectorCommand retractCollectorCommand,
            CollectorSubsystem collector,
            IntakeCollectorCommand intakeCollectorCommandForWhenExtended,
            IntakeCollectorCommand intakeCollectorCommandForWhenRetracted)   {
        resetHeading.setHeadingToApply(90);
        operatorInterface.gamepad.getifAvailable(1).onTrue(resetHeading);


        
        // only runs motors
        operatorInterface.gamepad.getXboxButton(XboxButton.A).onTrue(intakeCollectorCommand);
        operatorInterface.gamepad.getXboxButton(XboxButton.B).onTrue(ejectCollectorCommand);
        operatorInterface.gamepad.getXboxButton(XboxButton.X).onTrue(stopCollectorCommand);
        
        // extends and runs intake, retracts if has piece
        ParallelCommandGroup extendAndIntake = extendCollectorCommandForIntake.alongWith(intakeCollectorCommandForWhenExtended);
        ParallelCommandGroup retractAndIntake = retractCollectorCommandForIntake.alongWith(intakeCollectorCommandForWhenRetracted);
        ConditionalCommand extendAndIntakeThenRetractIfHasPiece = new ConditionalCommand(retractAndIntake, extendAndIntake, () -> collector.hasGamePiece());

        operatorInterface.gamepad.getXboxButton(XboxButton.RightTrigger).onTrue(extendAndIntakeThenRetractIfHasPiece);

        ParallelCommandGroup retractAndStop = retractCollectorCommand.alongWith(stopCollectorCommandForRetract);

        operatorInterface.gamepad.getXboxButton(XboxButton.RightTrigger).onFalse(retractAndStop);
        

      }
}
