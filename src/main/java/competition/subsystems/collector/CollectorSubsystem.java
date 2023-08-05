package competition.subsystems.collector;

import javax.inject.Inject;
import javax.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSolenoid;

@Singleton
public class CollectorSubsystem extends BaseSubsystem {
    public XSolenoid collectorSolenoid;
    

    public enum CollectorState {
        Retracted,
        Extended
    }

    @Inject
    public CollectorSubsystem(XSolenoid.XSolenoidFactory xSolenoidFactory) {
        this.collectorSolenoid = xSolenoidFactory.create(2);
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


}
