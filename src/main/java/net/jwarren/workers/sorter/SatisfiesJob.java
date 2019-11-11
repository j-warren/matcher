package net.jwarren.workers.sorter;

import net.jwarren.workers.misc.IncomparableException;
import net.jwarren.workers.model.Certificate;
import net.jwarren.workers.model.Job;
import net.jwarren.workers.model.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Recommends Jobs which its Worker satisfies (has a driver's license if required, has all certificates)
 * If its Worker is missing information, there will be no ordering
 * If one of the Jobs is missing information, it will not be recommended over another job
 */
public class SatisfiesJob implements SatisfactionCheck {

    private static final Logger LOGGER = LoggerFactory.getLogger(SatisfiesJob.class);

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

    @Override
    public Boolean jobSatisfiesAllChecks(Worker worker, Job job) {
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
