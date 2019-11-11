package net.jwarren.workers.sorter;

import net.jwarren.workers.model.Job;
import net.jwarren.workers.model.Worker;

public interface JobPreference {
    int jobScore(Worker worker, Job job);
}
