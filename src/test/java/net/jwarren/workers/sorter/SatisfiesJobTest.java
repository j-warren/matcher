package net.jwarren.workers.sorter;

import net.jwarren.workers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static net.jwarren.workers.TestUtil.addAll;
import static net.jwarren.workers.TestUtil.makeJob;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class SatisfiesJobTest {

    private static SatisfiesJob comparator;

    private static Worker worker;
    private static List<Certificate> job1Certificates;
    private static List<Certificate> job2Certificates;

    @BeforeEach
    public void setup() {
        List<Certificate> workerCertificates = new ArrayList<>();

        worker = new WorkerBuilder()
                .setAge(0)
                .setGuid("guid")
                .setJobSearchAddress(new Address(Unit.KM, 30, Double.valueOf("0.00"), Double.valueOf("0.00")))
                .setTransportation(Transportation.PUBLIC_TRANSPORT)
                .setUserId(1)
                .setHasDriversLicense(false)
                .setCertificates(workerCertificates)
                .createWorker();

        job1Certificates = new ArrayList<>();
        job2Certificates = new ArrayList<>();
    }

    @Test
    public void testJobSatisfiesAllChecks() {
        List<Job> jobs = new ArrayList<>();

        Job job1 = makeJob(null, false, "a7");
        Job job2 = makeJob(job1Certificates, true, "a8");
        Job job3 = makeJob(job2Certificates, false, "a9");
        Job job4 = makeJob(Collections.singletonList(new Certificate("a")), false, "a10");
        addAll(jobs, job1, job2, job3, job4);

        List<Job> results = jobs.stream()
                .filter(job -> new SatisfiesJob().jobSatisfiesAllChecks(worker, job))
                .collect(Collectors.toList());

        assertTrue("Job3 should be included because it has all values and doesn't require a license", results.contains(job3));
    }

}
