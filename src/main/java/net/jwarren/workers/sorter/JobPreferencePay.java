package net.jwarren.workers.sorter;

import net.jwarren.workers.model.Job;
import net.jwarren.workers.model.Worker;

public class JobPreferencePay implements JobPreference {
    /**
     * Scores a job based on its pay. Assumes that the billPayRate will be less than 21474836.47.
     * @param job The job to evaluate.
     * @return A score value. Higher values are better.
     */
    private static int scoreJobPay(Job job) {
        if (job.getBillRate() == null || job.getBillRate().isNaN()) {
            return 0;
        }
        return job.getBillRate().intValue() * 100; // cents
    }

    @Override
    public int jobScore(Worker worker, Job job) {
        return scoreJobPay(job);
    }
}
