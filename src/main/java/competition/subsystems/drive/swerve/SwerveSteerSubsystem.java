package competition.subsystems.drive.swerve;

import javax.inject.Inject;
import javax.inject.Singleton;

import competition.electrical_contract.ElectricalContract;
import competition.operator_interface.OperatorInterface;
import edu.wpi.first.math.geometry.Rotation2d;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANSparkMax;
import xbot.common.controls.actuators.XCANSparkMax.XCANSparkMaxFactory;
import xbot.common.controls.sensors.XCANCoder;
import xbot.common.controls.sensors.XCANCoder.XCANCoderFactory;
import xbot.common.injection.electrical_contract.DeviceInfo;
import xbot.common.math.PIDManager.PIDManagerFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class SwerveSteerSubsystem extends BaseSubsystem {

    public XCANSparkMax steerMotor;
    public DoubleProperty rightJoystickPower;
    public OperatorInterface oi;
    public XCANCoder encoder;
    public ElectricalContract contract;
    public DoubleProperty wheelAngle;

    @Inject
    public SwerveSteerSubsystem(XCANSparkMaxFactory sparkMaxFactory, PropertyFactory pFact, OperatorInterface oi,
                                XCANCoderFactory canCoderFactory, ElectricalContract electricalContract, PIDManagerFactory pidManagerFactory,
                                PropertyFactory pFactory) {
        this.steerMotor = sparkMaxFactory.create(new DeviceInfo(28, false), getPrefix(), "SteerMotor");
        this.oi = oi;
        this.contract = electricalContract;
        this.encoder = canCoderFactory.create(new DeviceInfo(52, false), getPrefix());
        this.wheelAngle = pFact.createEphemeralProperty("Wheel Angle", 0);

        pFact.setPrefix(this);

        rightJoystickPower = pFact.createEphemeralProperty("RightJoystickPower", 0);
    }

    public void setPower(double power) {
        steerMotor.set(power);
    }

    public Rotation2d getEncoderAngle() {
        return Rotation2d.fromDegrees(encoder.getAbsolutePosition());
    }

    @Override
    public void periodic() {

        // checks joystick power using ephemeral property
        rightJoystickPower.set(oi.driverGamepad.getRightStickX());
        wheelAngle.set(encoder.getAbsolutePosition());
    }
    
}
