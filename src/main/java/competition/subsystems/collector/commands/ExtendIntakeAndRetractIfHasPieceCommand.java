package competition.subsystems.collector.commands;

import javax.inject.Inject;

import competition.subsystems.collector.CollectorSubsystem;
import xbot.common.command.BaseCommand;

public class ExtendIntakeAndRetractIfHasPieceCommand extends BaseCommand {

    CollectorSubsystem collector;

    @Inject
    public ExtendIntakeAndRetractIfHasPieceCommand(CollectorSubsystem collector) {
        this.collector = collector;

        addRequirements(collector);
    }

    @Override
    public void initialize() {
        collector.extend();
        collector.intake();
    }

    @Override
    public void execute() {
        if (collector.hasGamePiece()) {
            collector.retract();
            collector.intake();
        }
    }
    
}