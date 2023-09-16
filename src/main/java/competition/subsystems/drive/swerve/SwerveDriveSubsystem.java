package competition.subsystems.drive.swerve;

import javax.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANSparkMax;
import xbot.common.controls.actuators.XCANSparkMax.XCANSparkMaxFactory;
import xbot.common.injection.electrical_contract.DeviceInfo;

@Singleton
public class SwerveDriveSubsystem extends BaseSubsystem {
    
    public XCANSparkMax driveMotor;

    public SwerveDriveSubsystem(XCANSparkMaxFactory sparkMaxFactory) {
        this.driveMotor = sparkMaxFactory.create(new DeviceInfo(28, true), getPrefix(), "DriveMotor");
    }

    public void setPower(Double power) {
        this.driveMotor.set(power);
    }
}
