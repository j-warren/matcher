package net.jwarren.workers.service;

import net.jwarren.workers.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.jwarren.workers.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppropriateJobsServiceTest {

    @Mock
    SwipeService swipeService;

    @InjectMocks
    AppropriateJobsService appropriateJobsService;

    private static final Location sydneyAustralia = new Location(-33.872, 151.206);
    private static final Location burwoodAustralia = new Location(-33.877, 151.103);
    private static final Location beijingChina = new Location(39.916, 116.416);

    @Test
    public void testFindAppropriateJobs() {
        Job job1 = makeJobWithLocationCertsLicense(null, false, sydneyAustralia, "1");
        Job job2 = makeJobWithLocationCertsLicense(new ArrayList<>(), true, burwoodAustralia, "2");
        Job job3 = makeJobWithLocationCertsLicense(new ArrayList<>(), false, beijingChina, "3");
        Job job4 = makeJobWithLocationCertsLicense(new ArrayList<>(), false, burwoodAustralia, "4");
        List<Job> jobs = new ArrayList<>();
        addAll(jobs, job1, job2, job3, job4);
        Worker worker = new WorkerBuilder()
                .setCertificates(new ArrayList<>())
                .setHasDriversLicense(false)
                .setUserId(1)
                .setJobSearchAddress(new Address(Unit.KM, 30, sydneyAustralia.getLongitude(), sydneyAustralia.getLatitude()))
                .createWorker();

        when(swipeService.findAllWorkers()).thenReturn(Collections.singletonList(worker));
        when(swipeService.findAllJobs()).thenReturn(jobs);

        List<Job> appropriateJobs = appropriateJobsService.findAppropriateJobs(1, 3);
        assertFalse(appropriateJobs.contains(job1), "Job 1 is missing requiredCertificates definition");
        assertFalse(appropriateJobs.contains(job2), "Job 2 requires a driver's license");
        assertFalse(appropriateJobs.contains(job3), "Job 3 is too far away");
        assertTrue(appropriateJobs.contains(job4), "Job 4 has everything defined (requiredCertificates, " +
                "location, driversLicenseRequired), doesn't require a license, doesn't have certs the worker doesn't " +
                "have, and isn't too far away");
    }
}
