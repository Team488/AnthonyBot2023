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
            IntakeCollectorCommand intakeCollectorCommand,
            EjectCollectorCommand ejectCollectorCommand,
            StopCollectorCommand stopCollectorCommandForIntake,
            StopCollectorCommand stopCollectorCommandForEject)
    {
        resetHeading.setHeadingToApply(90);
        operatorInterface.gamepad.getifAvailable(1).onTrue(resetHeading);

        // collector extend/retract intake
        ParallelCommandGroup extendAndIntake = extendCollectorCommandForIntake.alongWith(intakeCollectorCommand);
        ParallelCommandGroup retractAndStopIntake = retractCollectorCommandForIntake.alongWith(stopCollectorCommandForIntake);
    
        operatorInterface.gamepad.getXboxButton(XboxButton.A).onTrue(extendAndIntake);
        operatorInterface.gamepad.getXboxButton(XboxButton.A).onFalse(retractAndStopIntake);

        // collector extend/retract eject
        ParallelCommandGroup extendAndEject = extendCollectorCommandForEject.alongWith(ejectCollectorCommand);
        ParallelCommandGroup retractAndStopEject = retractCollectorCommandForEject.alongWith(stopCollectorCommandForEject);

        operatorInterface.gamepad.getXboxButton(XboxButton.B).onTrue(extendAndEject);
        operatorInterface.gamepad.getXboxButton(XboxButton.B).onFalse(retractAndStopEject);
    }
}
