package net.jwarren.workers.service;

import net.jwarren.workers.misc.UpstreamException;
import net.jwarren.workers.misc.WorkerNotFoundException;
import net.jwarren.workers.model.Job;
import net.jwarren.workers.model.Worker;
import net.jwarren.workers.sorter.JobPreferenceComparator;
import net.jwarren.workers.sorter.JobPreferencePay;
import net.jwarren.workers.sorter.SatisfiesJob;
import net.jwarren.workers.sorter.SatisfiesWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppropriateJobsService {

    private SwipeService swipeService;

    private static final int DEFAULT_MAXIMUM_RESULTS = 3;

    @Autowired
    public AppropriateJobsService(SwipeService swipeService) {
        this.swipeService = swipeService;
    }

    public List<Job> findAppropriateJobs(Integer workerId) throws WorkerNotFoundException, UpstreamException {
        return findAppropriateJobs(workerId, DEFAULT_MAXIMUM_RESULTS);
    }

    public List<Job> findAppropriateJobs(Integer workerId, int maxResults) throws WorkerNotFoundException, UpstreamException {
        // These API results are not cached as we want the latest value each time
        List<Job> jobs = findAllJobs();
        Worker worker = findWorkerById(workerId);

        // This should only occur if the Swipe API returns no result
        if (jobs == null) {
            return Collections.emptyList();
        }

        return jobs.stream()
                .filter(job -> new SatisfiesJob().jobSatisfiesAllChecks(worker, job) &&
                        new SatisfiesWorker().jobSatisfiesAllChecks(worker, job))
                .sorted(new JobPreferenceComparator(worker, new JobPreferencePay(), false))
                .limit(maxResults)
                .collect(Collectors.toList());
    }

    public List<Job> findAllJobs() throws UpstreamException {
        return swipeService.findAllJobs();
    }

    public List<Worker> findAllWorkers() throws UpstreamException {
        return swipeService.findAllWorkers();
    }

    public Worker findWorkerById(Integer workerId) throws WorkerNotFoundException, UpstreamException {
        List<Worker> workers = findAllWorkers();

        if (workers == null) {
            throw new WorkerNotFoundException(workerId);
        }

        Worker chosenWorker = workers
                .stream()
                .filter(w -> workerId.equals(w.getUserId()))
                .findFirst().orElse(null);

        if (chosenWorker == null) {
            throw new WorkerNotFoundException(workerId);
        }

        return chosenWorker;
    }
}
