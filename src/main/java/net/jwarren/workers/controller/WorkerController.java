package net.jwarren.workers.controller;

import net.jwarren.workers.model.Job;
import net.jwarren.workers.model.Worker;
import net.jwarren.workers.service.AppropriateJobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WorkerController {

    private AppropriateJobsService appropriateJobsFinderService;

    @Autowired
    public WorkerController(AppropriateJobsService appropriateJobsFinderService) {
        this.appropriateJobsFinderService = appropriateJobsFinderService;
    }

    @GetMapping("/jobsfor/{workerId}")
    List<Job> getAppropriateJobs(@PathVariable Integer workerId) {
        return appropriateJobsFinderService.findAppropriateJobs(workerId);
    }

    // TODO: remove test endpoint
    @GetMapping("/workers")
    List<Worker> getWorkers() {
        return appropriateJobsFinderService.findAllWorkers();
    }

    // TODO: remove test endpoint
    @GetMapping("/jobs")
    List<Job> getJobs() {
        return appropriateJobsFinderService.findAllJobs();
    }

}
