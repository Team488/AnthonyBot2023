package competition.operator_interface;

import javax.inject.Inject;
import javax.inject.Singleton;

import competition.subsystems.collector.commands.ExtendCollectorCommand;
import competition.subsystems.collector.commands.RetractCollectorCommand;
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
            RetractCollectorCommand retractCollectorCommand)
    {
        resetHeading.setHeadingToApply(90);
        operatorInterface.gamepad.getifAvailable(1).onTrue(resetHeading);

        operatorInterface.gamepad.getXboxButton(XboxButton.A).onTrue(extendCollectorCommand);
        operatorInterface.gamepad.getXboxButton(XboxButton.B).onTrue(retractCollectorCommand);
    }
}
