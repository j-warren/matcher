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

public class SatisfiesJobComparatorTest {

    private static SatisfiesJobComparator comparator;

    private static List<Certificate> workerCertificates;
    private static List<Certificate> job1Certificates;
    private static List<Certificate> job2Certificates;


    @BeforeEach
    public void setup() {
        workerCertificates = new ArrayList<>();

        Worker worker = new WorkerBuilder()
                .setAge(0)
                .setGuid("guid")
                .setJobSearchAddress(new Address(Unit.KM, 30, Double.valueOf("0.00"), Double.valueOf("0.00")))
                .setTransportation(Transportation.PUBLIC_TRANSPORT)
                .setUserId(1)
                .setHasDriversLicense(false)
                .setCertificates(workerCertificates)
                .createWorker();

        comparator = new SatisfiesJobComparator(worker);

        job1Certificates = new ArrayList<>();
        job2Certificates = new ArrayList<>();
    }

    @Test
    public void testCompareWorkerHasCertificatesForOneJob() {
        List<Job> jobs = new ArrayList<>();

        workerCertificates.add(new Certificate("ca"));
        workerCertificates.add(new Certificate("cb"));
        workerCertificates.add(new Certificate("cc"));

        job1Certificates.add(new Certificate("ca"));
        job1Certificates.add(new Certificate("cb"));
        job1Certificates.add(new Certificate("cc"));
        Job job1 = makeJob(job1Certificates, false, "a1");

        job2Certificates.add(new Certificate("cb"));
        job2Certificates.add(new Certificate("cd"));
        Job job2 = makeJob(job2Certificates, false, "a2");

        assertTrue("Expected to recommend the job for which the Worker has all certifications", comparator.compare(job1, job2) > 0);
    }

    @Test
    public void testCompareWorkerHasNoLicense() {

        Job job1 = makeJob(job1Certificates, false, "a3");
        Job job2 = makeJob(job2Certificates, true, "a4");

        assertTrue("Expected to recommend the job for which the Worker needs no license", comparator.compare(job1, job2) > 0);
    }

    @Test
    public void testComparePreferJobGuaranteedToBeSatisfied() {

        Job job1 = makeJob(null, false, "a5");
        Job job2 = makeJob(job2Certificates, false, "a6");

        assertTrue("Expected to recommend the job for which certificate and license requirements are known", comparator.compare(job1, job2) < 0);
    }

    @Test
    public void testCorrectSortingForList() {
        List<Job> jobs = new ArrayList<>();

        Job job1 = makeJob(null, false, "a7");
        Job job2 = makeJob(job1Certificates, true, "a8");
        Job job3 = makeJob(job2Certificates, false, "a9");
        Job job4 = makeJob(Collections.singletonList(new Certificate("a")), false, "a10");
        addAll(jobs, job1, job2, job3, job4);

        List<Job> results = jobs.stream()
                .sorted(comparator)
                .collect(Collectors.toList())
                .subList(0, 3);

        assertFalse("Job3 should be ordered after job1,job2,job4 because it has all values and doesn't require a license", results.contains(job3));
    }

}
