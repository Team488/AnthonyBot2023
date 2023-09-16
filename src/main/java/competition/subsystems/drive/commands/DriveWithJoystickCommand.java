package competition.subsystems.drive.commands;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.swerve.SwerveDriveSubsystem;
import xbot.common.command.BaseCommand;

public class DriveWithJoystickCommand extends BaseCommand {

    SwerveDriveSubsystem swerveDriveSubsystem;
    OperatorInterface oi;

    public DriveWithJoystickCommand(SwerveDriveSubsystem swerveDriveSubsystem, OperatorInterface oi) {
        this.swerveDriveSubsystem = swerveDriveSubsystem;
        this.oi = oi;
        
        addRequirements(swerveDriveSubsystem);
    }
    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

    }
    
}
