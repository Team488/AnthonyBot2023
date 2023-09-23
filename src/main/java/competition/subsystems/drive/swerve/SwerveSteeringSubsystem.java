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
public class SwerveSteeringSubsystem extends BaseSubsystem {

    public XCANSparkMax steerMotor;
    public DoubleProperty rightJoystickPower;
    public OperatorInterface oi;

    @Inject
    public SwerveSteeringSubsystem(XCANSparkMaxFactory sparkMaxFactory, PropertyFactory pFact, OperatorInterface oi) {
        this.steerMotor = sparkMaxFactory.create(new DeviceInfo(28, true), getPrefix(), "SteerMotor");
        this.oi = oi;

        pFact.setPrefix(this);

        rightJoystickPower = pFact.createEphemeralProperty("joystickPower", 0);
    }

    public void setPower(double power) {
        steerMotor.set(power);
    }

    @Override
    public void periodic() {

        // checks joystick power using ephemeral property
        rightJoystickPower.set(oi.driverGamepad.getRightStickX());
    }
    
}
