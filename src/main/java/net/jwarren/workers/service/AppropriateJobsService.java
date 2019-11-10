package net.jwarren.workers.service;

import net.jwarren.workers.misc.WorkerNotFoundException;
import net.jwarren.workers.model.Job;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppropriateJobsService {

    public List<Job> findAppropriateJobs(Long workerId) throws WorkerNotFoundException {
        throw new WorkerNotFoundException(workerId); // TODO: findAppropriateJobs
    }
}
