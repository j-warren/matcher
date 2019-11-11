package net.jwarren.workers.sorter;

import net.jwarren.workers.misc.IncomparableException;
import net.jwarren.workers.model.*;
import org.apache.lucene.util.SloppyMath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.jwarren.workers.misc.CheckUtil.throwIfNull;

/**
 * Confirms that a job satisfies a worker
 */
public class SatisfiesWorker implements SatisfactionCheck {

    private static final Logger LOGGER = LoggerFactory.getLogger(SatisfiesWorker.class);

    private static boolean jobSatisfiesWorkerRequirements(Worker worker, Job job) throws IncomparableException {
        Address address = worker.getJobSearchAddress();
        throwIfNull(address, worker + " has no address specified");
        throwIfNull(address.getLatitude(), worker + " has no address latitude specified");
        throwIfNull(address.getLongitude(), worker + " has no address longitude specified");
        throwIfNull(address.getMaxJobDistance(), worker + " has no max job distance specified");
        throwIfNull(address.getUnit(), worker + " has no distance unit specified");
        throwIfNull(job.getLocation(), job + " job has no location");
        throwIfNull(job.getLocation().getLatitude(), job + " has no latitude");
        throwIfNull(job.getLocation().getLongitude(), job + " has no longitude");

        double calcDistanceKm = distanceMeters(address.getLocation(), job.getLocation());
        if (Double.isNaN(calcDistanceKm)) {
            LOGGER.warn("Couldn't calculate distance between Worker {} ({}) and Job {} ({})", worker.getUserId(), worker.getJobSearchAddress().getLocation(), job.getJobId(), job.getLocation());
            return false;
        }
        calcDistanceKm /= 1000f;

        double maxJobDistanceKm = address.getMaxJobDistance();
        if (address.getUnit().equals(Unit.MI)) {
            maxJobDistanceKm *= 1.609344f;
        }

        LOGGER.debug("Is workerDistToJob <= maxWorkerDist? {} <= {} == {}", calcDistanceKm, maxJobDistanceKm, calcDistanceKm <= maxJobDistanceKm);
        return calcDistanceKm <= maxJobDistanceKm;
    }

    /**
     * Return the Haversine distance between two Locations, in metres
     * @see https://lucene.apache.org/core/8_3_0/core/org/apache/lucene/util/SloppyMath.html
     * @param location1 First location
     * @param location2 Second Location
     * @return Distance (in metres)
     */
    private static double distanceMeters(Location location1, Location location2) {
        return SloppyMath.haversinMeters(location1.getLatitude(), location1.getLongitude(), location2.getLatitude(), location2.getLongitude());
    }

    @Override
    public Boolean jobSatisfiesAllChecks(Worker worker, Job job) {
        try {
            return jobSatisfiesWorkerRequirements(worker, job);
        } catch (IncomparableException e) {
            LOGGER.warn("Considering job's missing requirements as not fulfilled:", e);
            return false;
        }
    }

}
