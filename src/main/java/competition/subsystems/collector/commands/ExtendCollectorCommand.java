package competition.subsystems.collector.commands;

import javax.inject.Inject;

import competition.subsystems.collector.CollectorSubsystem;
import xbot.common.command.BaseCommand;

public class ExtendCollectorCommand extends BaseCommand {
    CollectorSubsystem collector;

    @Inject
    public ExtendCollectorCommand(CollectorSubsystem collector) {
        this.collector = collector;
    }

    @Override
    public void initialize() {
        collector.extend();
    }

    @Override
    public void execute() {

    }

    public boolean isFinished() {
        return true;
    }
    
}
