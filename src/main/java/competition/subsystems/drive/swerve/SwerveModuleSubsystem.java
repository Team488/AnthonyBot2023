package competition.subsystems.drive.swerve;

import javax.inject.Inject;

import xbot.common.command.BaseSubsystem;

public class SwerveModuleSubsystem extends BaseSubsystem {
    
    public SwerveSteerSubsystem steer;
    public SwerveDriveSubsystem drive;

    @Inject
    public SwerveModuleSubsystem(int steerChannel, int driveChannel, int encoderChannel, SwerveSteerSubsystem steer, SwerveDriveSubsystem drive) {
        this.steer = steer;
        this.drive = drive;

        steer.setChannels(steerChannel, encoderChannel);
        drive.setChannel(driveChannel);
    }
}
