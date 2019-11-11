package net.jwarren.workers;

import net.jwarren.workers.model.Certificate;
import net.jwarren.workers.model.Job;
import net.jwarren.workers.model.JobBuilder;
import net.jwarren.workers.model.Location;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtil {
    private static final JobBuilder staggeredJobBuilder = new JobBuilder()
            .setJobId(0)
            .setJobTitle("Title 0")
            .setAbout("About job 0")
            .setBillRate(15f)
            .setCompany("Company A")
            .setGuid("a1")
            .setStartDate(LocalDateTime.now())
            .setWorkersRequired(1);

    public static Job makeJob(List<Certificate> requiredCerts, boolean driversLicenseRequired, String guid) {
        return staggeredJobBuilder
                .setRequiredCertificates(requiredCerts)
                .setDriversLicenseRequired(driversLicenseRequired)
                .setLocation(new Location(Double.valueOf("0.00"), Double.valueOf("0.00")))
                .setGuid(guid)
                .createJob();
    }

    public static Job makeJobWithLocationCertsLicense(List<Certificate> requiredCerts, boolean driversLicenseRequired, Location location, String guid) {
        return staggeredJobBuilder
                .setRequiredCertificates(requiredCerts)
                .setDriversLicenseRequired(driversLicenseRequired)
                .setLocation(location)
                .setGuid(guid)
                .createJob();
    }

    public static Job makeJobWithLocation(Location location, String guid) {
        return staggeredJobBuilder
                .setRequiredCertificates(new ArrayList<>())
                .setDriversLicenseRequired(false)
                .setLocation(location)
                .setGuid(guid)
                .createJob();
    }

    public static <E> void addAll(Collection<E> collection, E... items) {
        collection.addAll(Arrays.stream(items).collect(Collectors.toList()));
    }
}
