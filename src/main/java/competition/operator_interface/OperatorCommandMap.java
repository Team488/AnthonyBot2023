package competition.operator_interface;

import java.util.stream.Collector;

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
            ExtendCollectorCommand extendCollectorCommandForEject, 
            RetractCollectorCommand retractCollectorCommandForIntake,
            RetractCollectorCommand retractCollectorCommandForEject,
            IntakeCollectorCommand intakeCollectorCommandForGroup,
            EjectCollectorCommand ejectCollectorCommandForGroup,
            StopCollectorCommand stopCollectorCommandForIntake,
            StopCollectorCommand stopCollectorCommandForEject,
            IntakeCollectorCommand intakeCollectorCommand,
            EjectCollectorCommand ejectCollectorCommand,
            StopCollectorCommand stopCollectorCommand,
            RetractCollectorCommand retractCollectorCommand,
            CollectorSubsystem collector)   {
        resetHeading.setHeadingToApply(90);
        operatorInterface.gamepad.getifAvailable(1).onTrue(resetHeading);

        // collector extend/retract intake
        ParallelCommandGroup extendAndIntake = extendCollectorCommandForIntake.alongWith(intakeCollectorCommandForGroup);
        // ParallelCommandGroup retractAndStopIntake = retractCollectorCommandForIntake.alongWith(stopCollectorCommandForIntake);
    
        // operatorInterface.gamepad.getXboxButton(XboxButton.A).onTrue(extendAndIntake);
        // operatorInterface.gamepad.getXboxButton(XboxButton.A).onFalse(retractCollectorCommand);

        // collector extend/retract eject
        // ParallelCommandGroup extendAndEject = extendCollectorCommandForEject.alongWith(ejectCollectorCommandForGroup);
        // ParallelCommandGroup retractAndStopEject = retractCollectorCommandForEject.alongWith(stopCollectorCommandForEject);

        // operatorInterface.gamepad.getXboxButton(XboxButton.B).onTrue(extendAndEject);
        // operatorInterface.gamepad.getXboxButton(XboxButton.B).onFalse(retractCollectorCommand);

        // only runs motors
        operatorInterface.gamepad.getXboxButton(XboxButton.A).onTrue(intakeCollectorCommand);
        operatorInterface.gamepad.getXboxButton(XboxButton.B).onTrue(ejectCollectorCommand);
        operatorInterface.gamepad.getXboxButton(XboxButton.X).onTrue(stopCollectorCommand);

        
        ConditionalCommand extendIntakeAndRetractIfHasPiece = new ConditionalCommand(retractCollectorCommand, extendAndIntake, () -> collector.hasGamePiece());

        operatorInterface.gamepad.getXboxButton(XboxButton.RightTrigger).onTrue(extendIntakeAndRetractIfHasPiece);
        operatorInterface.gamepad.getXboxButton(XboxButton.RightTrigger).onFalse(retractCollectorCommand);
      }
}
