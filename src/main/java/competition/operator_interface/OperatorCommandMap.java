package competition.operator_interface;

import javax.inject.Inject;
import javax.inject.Singleton;

import competition.subsystems.collector.commands.EjectCollectorCommand;
import competition.subsystems.collector.commands.ExtendCollectorCommand;
import competition.subsystems.collector.commands.IntakeCollectorCommand;
import competition.subsystems.collector.commands.RetractCollectorCommand;
import competition.subsystems.collector.commands.StopCollectorCommand;
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
            EjectCollectorCommand ejectCollectorCommand)
    {
        resetHeading.setHeadingToApply(90);
        operatorInterface.gamepad.getifAvailable(1).onTrue(resetHeading);

        // collector extend/retract intake
        ParallelCommandGroup extendAndIntake = extendCollectorCommandForIntake.alongWith(intakeCollectorCommandForGroup);
        ParallelCommandGroup retractAndStopIntake = retractCollectorCommandForIntake.alongWith(stopCollectorCommandForIntake);
    
        operatorInterface.gamepad.getXboxButton(XboxButton.A).onTrue(extendAndIntake);
        operatorInterface.gamepad.getXboxButton(XboxButton.A).onFalse(retractAndStopIntake);

        // collector extend/retract eject
        ParallelCommandGroup extendAndEject = extendCollectorCommandForEject.alongWith(ejectCollectorCommandForGroup);
        ParallelCommandGroup retractAndStopEject = retractCollectorCommandForEject.alongWith(stopCollectorCommandForEject);

        operatorInterface.gamepad.getXboxButton(XboxButton.B).onTrue(extendAndEject);
        operatorInterface.gamepad.getXboxButton(XboxButton.B).onFalse(retractAndStopEject);

        // only runs eject and intake
        operatorInterface.gamepad.getXboxButton(XboxButton.LeftTrigger).onTrue(intakeCollectorCommand);
        operatorInterface.gamepad.getXboxButton(XboxButton.RightTrigger).onTrue(ejectCollectorCommand);
    }
}
