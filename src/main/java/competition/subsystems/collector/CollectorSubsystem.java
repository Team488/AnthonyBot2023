package competition.subsystems.collector;

import javax.inject.Inject;
import javax.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANSparkMax;
import xbot.common.controls.actuators.XSolenoid;
import xbot.common.injection.electrical_contract.DeviceInfo;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class CollectorSubsystem extends BaseSubsystem {
    public XSolenoid collectorSolenoid;
    public XCANSparkMax collectorMotor;
    public DoubleProperty intakePower;
    public DoubleProperty ejectPower;
    

    public enum CollectorState {
        Retracted,
        Extended
    }

    @Inject
    public CollectorSubsystem(XSolenoid.XSolenoidFactory xSolenoidFactory, XCANSparkMax.XCANSparkMaxFactory sparkMaxFactory, PropertyFactory pFact) {
        this.collectorSolenoid = xSolenoidFactory.create(2);
        this.collectorMotor = sparkMaxFactory.create(new DeviceInfo(25, true), getPrefix(), "CollectorMotor");

        pFact.setPrefix(this);

        intakePower = pFact.createPersistentProperty("IntakePower", 0.1);
        ejectPower = pFact.createPersistentProperty("EjectPower", -0.1);
        
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
        setMotorPower(intakePower.get());
    }

    public void eject() {
        setMotorPower(ejectPower.get());
    }

    public void stop() {
        setMotorPower(0);
    }


}
