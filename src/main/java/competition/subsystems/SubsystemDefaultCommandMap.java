package competition.subsystems;

import javax.inject.Inject;
import javax.inject.Singleton;

import competition.subsystems.drive.commands.DriveWithJoystickCommand;
import competition.subsystems.drive.commands.SteerWithJoystickCommand;
import competition.subsystems.drive.swerve.SwerveDriveSubsystem;
import competition.subsystems.drive.swerve.SwerveSteeringSubsystem;

/**
 * For setting the default commands on subsystems
 */
@Singleton
public class SubsystemDefaultCommandMap {

    @Inject
    public SubsystemDefaultCommandMap() {}

    @Inject
    public void setupSwerveDriveSubsystem(SwerveDriveSubsystem swerveDriveSubsystem, DriveWithJoystickCommand driveWithJoystickCommand) {
        swerveDriveSubsystem.setDefaultCommand(driveWithJoystickCommand);
    }

    @Inject
    public void setupSwerveSteeringSubsystem(SwerveSteeringSubsystem swerveSteeringSubsystem, SteerWithJoystickCommand steerWithJoystickCommand) {
        swerveSteeringSubsystem.setDefaultCommand(steerWithJoystickCommand);
    }
}
