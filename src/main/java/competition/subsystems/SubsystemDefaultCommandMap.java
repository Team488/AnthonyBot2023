package competition.subsystems;

import javax.inject.Inject;
import javax.inject.Singleton;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.commands.SteerAndDriveJoystickCommand;
import competition.subsystems.drive.commands.SwerveDriveWithJoysticksCommand;
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

    // @Inject
    // public void setupDriveSubsystem(DriveSubsystem driveSubsystem, SwerveDriveWithJoysticksCommand command) {
    //     driveSubsystem.setDefaultCommand(command);
    // }
}
