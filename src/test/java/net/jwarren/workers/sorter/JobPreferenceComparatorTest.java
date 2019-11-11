package net.jwarren.workers.sorter;

import net.jwarren.workers.TestUtil;
import net.jwarren.workers.model.Job;
import net.jwarren.workers.model.Worker;
import net.jwarren.workers.model.WorkerBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static net.jwarren.workers.TestUtil.makeJobWithPayAndLocation;

public class JobPreferenceComparatorTest {

    private static final Job j1 = makeJobWithPayAndLocation(100f, TestUtil.getBEIJING(), "jb1");
    private static final Job j2 = makeJobWithPayAndLocation(14.5f, TestUtil.getBEIJING(), "jb2");
    private static final Job j3 = makeJobWithPayAndLocation(101f, TestUtil.getBEIJING(), "jb3");
    private static final Job j4 = makeJobWithPayAndLocation(0f, TestUtil.getBEIJING(), "jb4");
    private static final Job j5 = makeJobWithPayAndLocation(null, TestUtil.getBEIJING(), "jb5");

    private JobPreferenceComparator jobPreferenceComparator;
    private List<Job> jobs;

    @BeforeEach
    public void setupEach() {
        Worker worker = new WorkerBuilder().createWorker();
        jobPreferenceComparator = new JobPreferenceComparator(worker, new JobPreferencePay(), false);

        jobs = new ArrayList<>();
        TestUtil.addAll(jobs, j1, j2, j3, j4, j5);
    }

    @Test
    public void testCompare() {
        List<Job> sortedResult = jobs.stream().sorted(jobPreferenceComparator).collect(Collectors.toList());

        assertEquals("j3 has the highest billrate and should be first", sortedResult.get(0), j3);
        assertEquals("j1 has the next highest billrate", sortedResult.get(1), j1);
        assertEquals("j2 has the next highest billrate", sortedResult.get(2), j2);
    }

    @Test
    public void testReversed() {
        List<Job> sortedResult = jobs.stream().sorted(jobPreferenceComparator.reversed()).collect(Collectors.toList());

        assertEquals("j3 has the highest billrate and should be last", sortedResult.get(4), j3);
        assertEquals("j1 has the next highest billrate", sortedResult.get(3), j1);
        assertEquals("j2 has the next highest billrate", sortedResult.get(2), j2);
    }
}
