package competition.subsystems.drive.commands;

import javax.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.swerve.SwerveDriveSubsystem;
import competition.subsystems.drive.swerve.SwerveSteerSubsystem;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import xbot.common.command.BaseCommand;
import xbot.common.controls.sensors.XCANCoder;
import xbot.common.math.MathUtils;
import xbot.common.math.PIDManager;
import xbot.common.math.PIDManager.PIDManagerFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
import xbot.common.subsystems.pose.BasePoseSubsystem;

public class SteerAndDriveJoystickCommand extends BaseCommand {

    public SwerveDriveSubsystem swerveDriveSubsystem;
    public SwerveSteerSubsystem swerveSteerSubsystem;
    public OperatorInterface oi;
    public BasePoseSubsystem pose;
    public XCANCoder encoder;
    public PIDManager pid;
    public double steerPower;

    public DoubleProperty steerPowerProperty;
    public DoubleProperty optimizedAngleProperty;
    public DoubleProperty joystickAngleProperty;
    public DoubleProperty optimizedPowerProperty;

    public double optimizedPower;

    @Inject
    public SteerAndDriveJoystickCommand(SwerveDriveSubsystem swerveDriveSubsystem, SwerveSteerSubsystem swerveSteerSubsystem, 
                                        OperatorInterface oi, PIDManagerFactory pManagerFactory, PropertyFactory pFact) {
        this.swerveDriveSubsystem = swerveDriveSubsystem;
        this.swerveSteerSubsystem = swerveSteerSubsystem;
        this.oi = oi;
        this.pid = pManagerFactory.create("SteerPid", 0.01, 0, 0);

        this.steerPowerProperty = pFact.createEphemeralProperty("SteerPower", 0);
        this.optimizedAngleProperty = pFact.createEphemeralProperty("OptimizedAngle", 0);
        this.joystickAngleProperty = pFact.createEphemeralProperty("JoystickAngle", 0);
        this.optimizedPowerProperty = pFact.createEphemeralProperty("OptimizedPowerProperty", 0);

        addRequirements(swerveDriveSubsystem, swerveSteerSubsystem);
    }

    @Override
    public void initialize() {
        
    }
    
    @Override
    public void execute() {
        joystickAngleProperty.set(oi.driverGamepad.getLeftVector().getAngle());
        
        steerPower = calculateHeadingPower(oi.driverGamepad.getLeftVector().getMagnitude());
        double drivePower = optimizedPower;
        optimizedPowerProperty.set(optimizedPower);
        if (Math.abs(drivePower) < 0.3) {
            steerPower = 0;
        }
        
        swerveDriveSubsystem.setPower(MathUtils.deadband(drivePower, 0.2));
        swerveSteerSubsystem.setPower(steerPower);
        
        
    }
    
    public double calculateHeadingPower(double power) {
        SwerveModuleState optimizedModuleState = swerveSteerSubsystem.getOptimizedSwerveModuleState(oi.driverGamepad.getLeftVector().getAngle() - 90, power);
        double optimizedAngle = optimizedModuleState.angle.getDegrees();
        Rotation2d targetAngle = Rotation2d.fromDegrees(optimizedAngle);
        double error = swerveSteerSubsystem.getEncoderAngle().minus(targetAngle).getDegrees();
        double steerPower = pid.calculate(0, error);

        optimizedPower = optimizedModuleState.speedMetersPerSecond;

        optimizedAngleProperty.set(optimizedAngle);
        steerPowerProperty.set(steerPower);
    
        return steerPower;
    }
}
