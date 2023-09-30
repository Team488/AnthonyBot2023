package competition.subsystems;

import javax.inject.Inject;
import javax.inject.Singleton;

import competition.subsystems.drive.commands.SteerAndDriveJoystickCommand;
import competition.subsystems.drive.swerve.SwerveSteerSubsystem;

/**
 * For setting the default commands on subsystems
 */
@Singleton
public class SubsystemDefaultCommandMap {

    @Inject
    public SubsystemDefaultCommandMap() {}

    // @Inject
    // public void setupSwerveDriveSubsystem(SwerveDriveSubsystem swerveDriveSubsystem, DriveWithJoystickCommand driveWithJoystickCommand) {
    //     swerveDriveSubsystem.setDefaultCommand(driveWithJoystickCommand);
    // }

    @Inject
    public void setupSwerveSteeringSubsystem(SwerveSteerSubsystem swerveSteerSubsystem, SteerAndDriveJoystickCommand steerAndDriveJoystickCommand) {
        swerveSteerSubsystem.setDefaultCommand(steerAndDriveJoystickCommand);
    }
}
