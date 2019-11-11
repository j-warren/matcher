package net.jwarren.workers.sorter;

import net.jwarren.workers.model.Job;
import net.jwarren.workers.model.Worker;

public interface SatisfactionCheck {
    Boolean jobSatisfiesAllChecks(Worker worker, Job job);
}
