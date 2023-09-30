package competition.subsystems.drive.commands;

import javax.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.swerve.SwerveSteerSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.MathUtils;

public class SteerWithJoystickCommand extends BaseCommand {

    public SwerveSteerSubsystem swerveSteerSubsystem;
    public OperatorInterface oi;

    @Inject
    public SteerWithJoystickCommand(SwerveSteerSubsystem swerveSteeringSubsystem, OperatorInterface oi) {
        this.swerveSteerSubsystem = swerveSteeringSubsystem;
        this.oi = oi;

        addRequirements(swerveSteeringSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double joystickPower = oi.driverGamepad.getRightStickX();
        double power = MathUtils.deadband(joystickPower, 0.2);

        swerveSteerSubsystem.setPower(power);
    }
    
}
