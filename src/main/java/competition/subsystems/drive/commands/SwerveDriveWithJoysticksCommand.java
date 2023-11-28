package competition.subsystems.drive.commands;

import javax.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.PIDManager;
import xbot.common.math.PIDManager.PIDManagerFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class SwerveDriveWithJoysticksCommand extends BaseCommand {

    private DriveSubsystem drive;
    private OperatorInterface oi;
    public PIDManager pid;

    public DoubleProperty steerPowerProperty;
    public DoubleProperty optimizedAngleProperty;
    public DoubleProperty joystickAngleProperty;
    public DoubleProperty optimizedPowerProperty;

    public double joystickAngle;
    public double joystickMagnitude;

    @Inject
    public SwerveDriveWithJoysticksCommand(DriveSubsystem drive, OperatorInterface oi, PIDManagerFactory pManagerFactory, PropertyFactory pFact) {
        this.drive = drive;
        this.oi = oi;
        this.pid = pManagerFactory.create("SteerPid", 0.01, 0, 0);

        this.steerPowerProperty = pFact.createEphemeralProperty("SteerPower", 0);
        this.optimizedAngleProperty = pFact.createEphemeralProperty("OptimizedAngle", 0);
        this.joystickAngleProperty = pFact.createEphemeralProperty("JoystickAngle", 0);
        this.optimizedPowerProperty = pFact.createEphemeralProperty("OptimizedPowerProperty", 0);

        addRequirements(drive);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        joystickAngle = oi.driverGamepad.getLeftVector().getAngle();
        joystickMagnitude = oi.driverGamepad.getLeftVector().getMagnitude();
        drive.drive(joystickAngle, joystickMagnitude);
    }
    
}
