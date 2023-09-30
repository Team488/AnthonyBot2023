package competition.subsystems.drive.swerve;

import javax.inject.Inject;
import javax.inject.Singleton;

import competition.operator_interface.OperatorInterface;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANSparkMax;
import xbot.common.controls.actuators.XCANSparkMax.XCANSparkMaxFactory;
import xbot.common.injection.electrical_contract.DeviceInfo;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class SwerveDriveSubsystem extends BaseSubsystem {
    
    public XCANSparkMax driveMotor;
    public DoubleProperty leftJoystickPower;
    public OperatorInterface oi;

    @Inject
    public SwerveDriveSubsystem(XCANSparkMaxFactory sparkMaxFactory, PropertyFactory pFact, OperatorInterface oi) {
        this.driveMotor = sparkMaxFactory.create(new DeviceInfo(29, false), getPrefix(), "DriveMotor");
        this.oi = oi;

        pFact.setPrefix(this);

        leftJoystickPower = pFact.createEphemeralProperty("LeftJoystickPower", 0);
    }

    public void setPower(Double power) {
        driveMotor.set(power);
    }

    @Override
    public void periodic() {
        
        // checks joystick power using ephemeral property
        leftJoystickPower.set(oi.driverGamepad.getLeftStickY());
    }
}
