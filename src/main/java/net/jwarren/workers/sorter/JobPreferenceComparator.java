package net.jwarren.workers.sorter;

import lombok.Data;
import net.jwarren.workers.model.Job;
import net.jwarren.workers.model.Worker;

import java.util.Comparator;

/**
 * Compare jobs based on their pay
 */
@Data
public class JobPreferenceComparator implements Comparator<Job> {

    private final Worker worker;
    private final boolean directionAscending;
    private final JobPreference jobPreference;

    /**
     * @param worker The worker for which jobs are being compared
     * @param directionAscending If true, higher scoring Jobs will have a higher sort index
     */
    public JobPreferenceComparator(Worker worker, JobPreference jobPreference, boolean directionAscending) {
        this.worker = worker;
        this.jobPreference = jobPreference;
        this.directionAscending = directionAscending;
    }

    @Override
    public int compare(Job job, Job t1) {
        return (jobPreference.jobScore(worker, job) - jobPreference.jobScore(worker, t1)) * (directionAscending ? 1 : -1);
    }

    @Override
    public Comparator<Job> reversed() {
        return new JobPreferenceComparator(worker, jobPreference, !directionAscending);
    }
}
