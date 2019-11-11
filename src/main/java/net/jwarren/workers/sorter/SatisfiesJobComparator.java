package net.jwarren.workers.sorter;

import net.jwarren.workers.misc.IncomparableException;
import net.jwarren.workers.model.Certificate;
import net.jwarren.workers.model.Job;
import net.jwarren.workers.model.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * This basic comparator recommends Jobs which its Worker satisfies (has a driver's license if required, has all certificates)
 * If its Worker is missing information, there will be no ordering
 * If one of the Jobs is missing information, it will not be recommended over another job
 */
public class SatisfiesJobComparator implements Comparator<Job> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SatisfiesJobComparator.class);

    private final Worker worker;

    public SatisfiesJobComparator(Worker forWorker) {
        this.worker = forWorker;
    }

    /**
     * Compares two jobs and assigns a higher value to jobs for which this comparator's worker meets ALL the requirements
     * A job which is missing requirements (i.e. null value) will be counted as if the worker doesn't meet the requirements
     * Note that higher values are preferable
     * @param job The first job
     * @param job2 The second job
     * @return Returns 1 if job is preferred to job2, 0 if no preference, -1 if job2 is preferred to job
     */
    @Override
    public int compare(Job job, Job job2) {
        LOGGER.debug("Comparing:\n{}\n{}\n{}", worker, job, job2);

        // Quick termination
        if (worker.getCertificates() == null || worker.getHasDriversLicense() == null) {
            LOGGER.info("Worker {} is missing certificates or license, cannot compare", worker);
            return 0;
        }

        boolean workerSatisfiesJob = jobRequirementsDefinedAndWorkerSatisfies(worker, job);
        boolean workerSatisfiesJob2 = jobRequirementsDefinedAndWorkerSatisfies(worker, job2);

        // Prioritise jobs that have complete information
        LOGGER.debug("Result (worker satisfies job1,job2): {}, {}\n", workerSatisfiesJob, workerSatisfiesJob2);
        if (workerSatisfiesJob) {
            return workerSatisfiesJob2 ? 0 : 1;
        } else {
            return workerSatisfiesJob2 ? -1 : 0;
        }
    }

    private static boolean workerHasAllCertificates(Worker worker, Job job) throws IncomparableException {
        throwIfNull(job.getRequiredCertificates(), job + " is missing getRequiredCertificates");

        Set<Certificate> workerCerts = new HashSet<>(worker.getCertificates());
        Set<Certificate> jobCerts = new HashSet<>(job.getRequiredCertificates());

        return workerCerts.containsAll(jobCerts);
    }

    private static boolean workerNotBlockedByDriverLicense(Worker worker, Job job) throws IncomparableException {
        throwIfNull(job.getDriversLicenseRequired(), job + " is missing getDriversLicenseRequired");

        return !job.getDriversLicenseRequired() || worker.getHasDriversLicense();
    }

    private static boolean workerSatisfiesJobRequirements(Worker worker, Job job) throws IncomparableException {
        return workerHasAllCertificates(worker, job) && workerNotBlockedByDriverLicense(worker, job);
    }

    public static boolean jobRequirementsDefinedAndWorkerSatisfies(Worker worker, Job job) {
        try {
            return workerSatisfiesJobRequirements(worker, job);
        } catch (IncomparableException e) {
            LOGGER.warn("Considering job's missing requirements as not fulfilled:", e);
            return false;
        }
    }

    private static void throwIfNull(Object item, String reason) throws IncomparableException {
        if (item == null) {
            throw new IncomparableException(reason);
        }
    }
}
