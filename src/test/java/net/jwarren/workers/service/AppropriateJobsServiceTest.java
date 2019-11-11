package net.jwarren.workers.service;

import net.jwarren.workers.model.Job;
import net.jwarren.workers.model.Worker;
import net.jwarren.workers.model.WorkerBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.jwarren.workers.TestUtil.addAll;
import static net.jwarren.workers.TestUtil.makeJob;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppropriateJobsServiceTest {

    @Mock
    SwipeService swipeService;

    @InjectMocks
    AppropriateJobsService appropriateJobsService;

    @Test
    public void testFindAppropriateJobs() {
        Job job1 = makeJob(new ArrayList<>(), false, "1");
        Job job2 = makeJob(new ArrayList<>(), true, "2");
        Job job3 = makeJob(new ArrayList<>(), false, "3");
        Job job4 = makeJob(new ArrayList<>(), false, "4");
        List<Job> jobs = new ArrayList<>();
        addAll(jobs, job1, job2, job3, job4);
        Worker worker = new WorkerBuilder().setCertificates(new ArrayList<>()).setHasDriversLicense(false).setUserId(1)
                .createWorker();

        when(swipeService.findAllWorkers()).thenReturn(Collections.singletonList(worker));
        when(swipeService.findAllJobs()).thenReturn(jobs);

        List<Job> appropriateJobs = appropriateJobsService.findAppropriateJobs(1, 3);
        assertTrue(appropriateJobs.contains(job1));
        assertFalse(appropriateJobs.contains(job2));
        assertTrue(appropriateJobs.contains(job3));
    }
}
