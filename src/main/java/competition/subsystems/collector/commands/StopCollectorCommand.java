package competition.subsystems.collector.commands;

import javax.inject.Inject;

import competition.subsystems.collector.CollectorSubsystem;
import xbot.common.command.BaseCommand;

public class StopCollectorCommand extends BaseCommand {
    CollectorSubsystem collector;

    @Inject
    public StopCollectorCommand(CollectorSubsystem collector) {
        this.collector = collector; 

        addRequirements(collector);
    }

    @Override
    public void initialize() {
    }
    
    @Override
    public void execute() {
        collector.stop();
    }
    
}
