package competition.subsystems.collector.commands;

import javax.inject.Inject;

import competition.subsystems.collector.CollectorSubsystem;
import xbot.common.command.BaseCommand;

public class IntakeCollectorCommand extends BaseCommand {

    CollectorSubsystem collector;

    @Inject
    public IntakeCollectorCommand(CollectorSubsystem collector) {
        this.collector = collector;

        addRequirements(collector);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        collector.intake();
    }
    
}
