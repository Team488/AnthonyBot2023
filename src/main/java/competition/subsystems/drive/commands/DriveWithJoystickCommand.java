package competition.subsystems.drive.commands;

import javax.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.swerve.SwerveDriveSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.MathUtils;

public class DriveWithJoystickCommand extends BaseCommand {

    public SwerveDriveSubsystem swerveDriveSubsystem;
    public OperatorInterface oi;

    @Inject
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
        double joystickPower = oi.driverGamepad.getLeftStickY();
        double power = MathUtils.deadband(joystickPower, 0.2);

        swerveDriveSubsystem.setPower(power);
    }

}
