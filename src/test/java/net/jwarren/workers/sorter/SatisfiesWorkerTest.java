package net.jwarren.workers.sorter;

import net.jwarren.workers.TestUtil;
import net.jwarren.workers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static net.jwarren.workers.TestUtil.addAll;
import static net.jwarren.workers.TestUtil.makeJob;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class SatisfiesWorkerTest {

    private static SatisfiesJob comparator;

    private static Worker worker;

    @BeforeEach
    public void setup() {
        worker = new WorkerBuilder()
                .setAge(0)
                .setGuid("guid")
                .setJobSearchAddress(new Address(Unit.KM, 50, (double) -33.8775547f, (double) 151.1037054f))
                .setTransportation(Transportation.PUBLIC_TRANSPORT)
                .setUserId(1)
                .setHasDriversLicense(false)
                .setCertificates(new ArrayList<>())
                .createWorker();
    }

    @Test
    public void testJobSatisfiesAllChecks() {
        List<Job> jobs = new ArrayList<>();

        Job job1 = TestUtil.makeJobWithLocation(new Location((double) 39.9288889f, (double) 116.3883333f), "beijing");
        Job job2 = TestUtil.makeJobWithLocation(new Location((double) -33.8727496f, (double) 151.2061827f), "sydney");
        Job job3 = TestUtil.makeJobWithLocation(new Location(57.15, -2.15), "aberdeen");

        addAll(jobs, job1, job2, job3);

        List<Job> results = jobs.stream()
                .filter(job -> new SatisfiesWorker().jobSatisfiesAllChecks(worker, job))
                .collect(Collectors.toList());

        assertFalse("Job1 should be excluded because it is in China", results.contains(job1));
        assertTrue("Job2 should be included because it is close by", results.contains(job2));
        assertFalse("Job1 should be excluded because it is in Scotland", results.contains(job3));
    }

}
