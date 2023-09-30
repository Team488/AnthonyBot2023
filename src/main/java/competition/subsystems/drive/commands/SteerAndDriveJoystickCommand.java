package competition.subsystems.drive.commands;

import javax.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.swerve.SwerveDriveSubsystem;
import competition.subsystems.drive.swerve.SwerveSteerSubsystem;
import edu.wpi.first.math.geometry.Rotation2d;
import xbot.common.command.BaseCommand;
import xbot.common.controls.sensors.XCANCoder;
import xbot.common.math.PIDManager;
import xbot.common.math.PIDManager.PIDManagerFactory;
import xbot.common.subsystems.pose.BasePoseSubsystem;

public class SteerAndDriveJoystickCommand extends BaseCommand {

    public SwerveDriveSubsystem swerveDriveSubsystem;
    public SwerveSteerSubsystem swerveSteerSubsystem;
    public OperatorInterface oi;
    public BasePoseSubsystem pose;
    public XCANCoder encoder;
    public PIDManager pid;

    @Inject
    public SteerAndDriveJoystickCommand(SwerveDriveSubsystem swerveDriveSubsystem, SwerveSteerSubsystem swerveSteerSubsystem, OperatorInterface oi, PIDManagerFactory pManagerFactory) {
        this.swerveDriveSubsystem = swerveDriveSubsystem;
        this.swerveSteerSubsystem = swerveSteerSubsystem;
        this.oi = oi;
        this.pid = pManagerFactory.create("SteerPid", 0.01, 0, 0);

        addRequirements(swerveDriveSubsystem, swerveSteerSubsystem);
    }

    @Override
    public void initialize() {
        
    }

    public double calculateHeadingPower() {
        Rotation2d targetAngle = Rotation2d.fromDegrees(oi.driverGamepad.getLeftVector().getAngle());
        double error = swerveSteerSubsystem.getEncoderAngle().minus(targetAngle).getDegrees();

        double steerPower = pid.calculate(0, error);

        return steerPower;
    }

    @Override
    public void execute() {
        // double joyStickPower = oi.driverGamepad.getLeftVector().getMagnitude();

        // swerveDriveSubsystem.setPower(joyStickPower);

        double steerPower = calculateHeadingPower();
        swerveSteerSubsystem.setPower(steerPower);

        
    }
    
}
