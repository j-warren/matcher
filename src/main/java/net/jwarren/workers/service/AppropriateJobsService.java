package net.jwarren.workers.service;

import net.jwarren.workers.misc.WorkerNotFoundException;
import net.jwarren.workers.model.Job;
import net.jwarren.workers.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppropriateJobsService {

    private SwipeService swipeService;

    @Autowired
    public AppropriateJobsService(SwipeService swipeService) {
        this.swipeService = swipeService;
    }

    public List<Job> findAppropriateJobs(Long workerId) throws WorkerNotFoundException {
        throw new WorkerNotFoundException(workerId); // TODO: findAppropriateJobs
    }

    public List<Job> findAllJobs() {
        return swipeService.findAllJobs();
    }

    public List<Worker> findAllWorkers() {
        return swipeService.findAllWorkers();
    }
}
