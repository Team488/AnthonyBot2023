package competition.subsystems.collector;

import javax.inject.Inject;
import javax.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANSparkMax;
import xbot.common.controls.actuators.XSolenoid;
import xbot.common.injection.electrical_contract.DeviceInfo;

@Singleton
public class CollectorSubsystem extends BaseSubsystem {
    public XSolenoid collectorSolenoid;
    public XCANSparkMax collectorMotor;
    public double intakePower;
    public double ejectPower;
    

    public enum CollectorState {
        Retracted,
        Extended
    }

    @Inject
    public CollectorSubsystem(XSolenoid.XSolenoidFactory xSolenoidFactory, XCANSparkMax.XCANSparkMaxFactory sparkMaxFactory) {
        this.collectorSolenoid = xSolenoidFactory.create(2);
        this.collectorMotor = sparkMaxFactory.create(new DeviceInfo(25, true), getPrefix(), "CollectorMotor");

        intakePower = 0.1;
        ejectPower = -0.1;
    }

    private void changeCollector(CollectorState state) {
        if (state == CollectorState.Extended) {
            collectorSolenoid.setOn(true);
        }
        else if (state == CollectorState.Retracted) {
            collectorSolenoid.setOn(false);
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
        setMotorPower(intakePower);
    }

    public void eject() {
        setMotorPower(ejectPower);
    }

    public void stop() {
        setMotorPower(0);
    }


}
