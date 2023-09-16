package competition.subsystems.collector;

import javax.inject.Inject;
import javax.inject.Singleton;

import competition.operator_interface.OperatorInterface;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANSparkMax;
import xbot.common.controls.actuators.XSolenoid;
import xbot.common.controls.sensors.XTimer;
import xbot.common.injection.electrical_contract.DeviceInfo;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class CollectorSubsystem extends BaseSubsystem {
    public XSolenoid collectorSolenoid;
    public XCANSparkMax collectorMotor;
    public DoubleProperty intakePower;
    public DoubleProperty ejectPower;
    public DoubleProperty motorSpeed;
    public BooleanProperty hasGamePiece;
    public DoubleProperty hasGamePieceThreshold;
    public double hasPieceTime;
    public double startOfHasPiece;
    public DoubleProperty hasGamePieceMotorPower;
    public CollectorState collectorState;

    public final OperatorInterface oi;
    

    public enum CollectorState {
        Retracted,
        Extended
    }

    public enum IntakeState {
        Intaking,
        Ejecting,
        Stopped
    }

    IntakeState intakeState = IntakeState.Stopped;

    @Inject
    public CollectorSubsystem(XSolenoid.XSolenoidFactory xSolenoidFactory, XCANSparkMax.XCANSparkMaxFactory sparkMaxFactory, PropertyFactory pFact, OperatorInterface oi) {
        this.collectorSolenoid = xSolenoidFactory.create(2);
        this.collectorMotor = sparkMaxFactory.create(new DeviceInfo(25, true), getPrefix(), "CollectorMotor");

        pFact.setPrefix(this);

        intakePower = pFact.createPersistentProperty("IntakePower", 0.1);
        ejectPower = pFact.createPersistentProperty("EjectPower", -0.1);
        motorSpeed = pFact.createEphemeralProperty("MotorSpeed", 0);
        hasGamePiece = pFact.createEphemeralProperty("HasGamePiece", false);
        hasGamePieceThreshold = pFact.createPersistentProperty("GamePieceThreshold", 1000);
        hasGamePieceMotorPower = pFact.createPersistentProperty("HasGamePieceMotorPower", 0.05);

        this.oi = oi;

    }

    private void changeCollector(CollectorState state) {
        if (state == CollectorState.Extended) {
            collectorSolenoid.setOn(true);
            collectorState = CollectorState.Extended;
        }
        else if (state == CollectorState.Retracted) {
            collectorSolenoid.setOn(false);
            collectorState = CollectorState.Retracted;
        }
    }

    public void extend() {
        changeCollector(CollectorState.Extended);

    }

    public void retract() {
        changeCollector(CollectorState.Retracted);
    }

    private void setMotorPower(double power) {
        collectorMotor.set(power);
    }

    public void intake() {
        if (hasGamePiece()) {
            setMotorPower(hasGamePieceMotorPower.get());
        }
        else {
            setMotorPower(intakePower.get());
        }
        intakeState = IntakeState.Intaking;
    }

    public void eject() {
        setMotorPower(ejectPower.get());
        intakeState = IntakeState.Ejecting;
    }

    public void stop() {
        setMotorPower(0);
        intakeState = IntakeState.Stopped;
    }

    @Override
    public void periodic() {

        // checks if the intake system has a game piece
        motorSpeed.set(collectorMotor.getVelocity());
        if (motorSpeed.get() < hasGamePieceThreshold.get() && intakeState == IntakeState.Intaking) {
            if (startOfHasPiece == 0) {
                startOfHasPiece = XTimer.getFPGATimestamp();
            }
            if (startOfHasPiece > 0) {
                hasPieceTime = XTimer.getFPGATimestamp() - startOfHasPiece;
            }
        }
        else {
            startOfHasPiece = 0;
        }
        boolean hasPiece = (motorSpeed.get() < hasGamePieceThreshold.get() && intakeState == IntakeState.Intaking && hasPieceTime > 0.2);
        hasGamePiece.set(hasPiece);

        // rumbles controller if game piece is collected
        double intensity = 0.5;
        if (collectorState == CollectorState.Retracted) {
            intensity = 0.1;
        }

        if (hasPiece) {
            oi.operatorGamepad.getRumbleManager().rumbleGamepad(intensity, 0.1);
        }
        else {
            oi.operatorGamepad.getRumbleManager().stopGamepadRumble();
        }
    }

    public boolean hasGamePiece() {
        return hasGamePiece.get();
    }
}
