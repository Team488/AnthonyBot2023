package competition.subsystems.drive;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.log4j.Logger;

import competition.electrical_contract.ElectricalContract;
import competition.subsystems.drive.swerve.SwerveModuleSubsystem;
import xbot.common.math.PIDManager;
import xbot.common.math.XYPair;
import xbot.common.math.PIDManager.PIDManagerFactory;
import xbot.common.properties.PropertyFactory;
import xbot.common.properties.XPropertyManager;
import xbot.common.subsystems.drive.BaseDriveSubsystem;

@Singleton
public class DriveSubsystem extends BaseDriveSubsystem {
    private static Logger log = Logger.getLogger(DriveSubsystem.class);

    private final SwerveModuleSubsystem frontLeftSwerveModuleSubsystem;
    private final SwerveModuleSubsystem frontRightSwerveModuleSubsystem;
    private final SwerveModuleSubsystem rearLeftSwerveModuleSubsystem;
    private final SwerveModuleSubsystem rearRightSwerveModuleSubsystem;
    
    ElectricalContract contract;
    
    private final PIDManager positionPid;
    private final PIDManager rotationPid;

    // private SwerveModuleSubsystem swerveModule;

    // private enum SwerveModuleLocation {
    //     FRONT_LEFT,
    //     FRONT_RIGHT,
    //     REAR_LEFT,
    //     REAR_RIGHT
    // }

    // private SwerveModuleLocation swerveModuleLocation;

    @Inject
    public DriveSubsystem(XPropertyManager propManager, ElectricalContract contract, PIDManagerFactory pidFactory, PropertyFactory pf,
                            SwerveModuleSubsystem frontLeftSwerveModuleSubsystem,
                            SwerveModuleSubsystem frontRightSwerveModuleSubsystem,
                            SwerveModuleSubsystem rearLeftSwerveModuleSubsystem,
                            SwerveModuleSubsystem rearRightSwerveModuleSubsystem) {
// 30 31 51
        this.frontLeftSwerveModuleSubsystem = frontLeftSwerveModuleSubsystem;
        this.frontRightSwerveModuleSubsystem = frontRightSwerveModuleSubsystem;
        this.rearLeftSwerveModuleSubsystem = rearLeftSwerveModuleSubsystem;
        this.rearRightSwerveModuleSubsystem = rearRightSwerveModuleSubsystem;

        log.info("Creating DriveSubsystem");

        positionPid = pidFactory.create(getPrefix() + "PositionPID");
        rotationPid = pidFactory.create(getPrefix() + "RotationPID");
    }

    public void drive(double angle, double magnitude) {

    }

    @Override
    public PIDManager getPositionalPid() {
        return positionPid;
    }

    @Override
    public PIDManager getRotateToHeadingPid() {
        return rotationPid;
    }

    @Override
    public PIDManager getRotateDecayPid() {
        return null;
    }

    @Override
    public void move(XYPair translate, double rotate) {

    }

    @Override
    public double getLeftTotalDistance() {
        return 0;
    }

    @Override
    public double getRightTotalDistance() {
        return 0;
    }

    @Override
    public double getTransverseDistance() {
        return 0;
    }
}
