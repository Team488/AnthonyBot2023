package competition.operator_interface;

import javax.inject.Inject;
import javax.inject.Singleton;

import competition.subsystems.collector.commands.EjectCollectorCommand;
import competition.subsystems.collector.commands.ExtendCollectorCommand;
import competition.subsystems.collector.commands.IntakeCollectorCommand;
import competition.subsystems.collector.commands.RetractCollectorCommand;
import competition.subsystems.collector.commands.StopCollectorCommand;
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
            ExtendCollectorCommand extendCollectorCommand, 
            RetractCollectorCommand retractCollectorCommand,
            IntakeCollectorCommand intakeCollectorCommand,
            EjectCollectorCommand ejectCollectorCommand,
            StopCollectorCommand stopCollectorCommand)
    {
        resetHeading.setHeadingToApply(90);
        operatorInterface.gamepad.getifAvailable(1).onTrue(resetHeading);

        // collector extend and retract
        operatorInterface.gamepad.getXboxButton(XboxButton.A).onTrue(extendCollectorCommand);
        operatorInterface.gamepad.getXboxButton(XboxButton.B).onTrue(retractCollectorCommand);
        
        // collector motor intake and eject
        operatorInterface.gamepad.getXboxButton(XboxButton.Y).onTrue(intakeCollectorCommand);
        operatorInterface.gamepad.getXboxButton(XboxButton.X).onTrue(ejectCollectorCommand);
        operatorInterface.gamepad.getXboxButton(XboxButton.RightBumper).onTrue(stopCollectorCommand);

    }
}
