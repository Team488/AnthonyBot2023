package competition.subsystems.collector.commands;

import javax.inject.Inject;

import competition.subsystems.collector.CollectorSubsystem;
import xbot.common.command.BaseCommand;

public class RetractCollectorCommand extends BaseCommand {
    CollectorSubsystem collector;

    @Inject
    public RetractCollectorCommand(CollectorSubsystem collector) {
        this.collector = collector;
    }

    @Override
    public void initialize() {
        collector.retract();
    }

    @Override
    public void execute() {

        
    }

    public boolean isFinished() {
        return true;
    }
    
}
