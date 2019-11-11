package net.jwarren.workers.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static net.jwarren.workers.misc.StringUtil.parseCashAmount;

@Data
public class Job {
    private Boolean driversLicenseRequired;
    private List<Certificate> requiredCertificates;
    private Location location;
    private Float billRate;
    private Integer workersRequired;
    private LocalDateTime startDate;
    private String about;
    private String jobTitle;
    private String company;
    private String guid;
    private Integer jobId;

    private static final Logger LOGGER = LoggerFactory.getLogger(Job.class);

    /**
     * Create a Job from a map
     * @param properties Properties Map of field names and values
     */
    @JsonCreator
    public Job(Map<String, Object> properties) {

        List<String> certificates = (List<String>) properties.getOrDefault("requiredCertificates", null);
        this.requiredCertificates = certificates != null ? certificates.stream().map(Certificate::new).collect(Collectors.toList()) : null;

        Map<String, String> locations = (Map<String, String>) properties.getOrDefault("location", null);
        this.location = locations != null ? new Location(locations) : null;

        String billRateString = (String) properties.getOrDefault("billRate", null);
        this.billRate = parseCashAmount(billRateString);

        this.driversLicenseRequired = (Boolean) properties.getOrDefault("driverLicenseRequired", null);
        this.workersRequired = (Integer) properties.getOrDefault("workersRequired", null);

        this.startDate = null;
        String startDateTime = (String) properties.getOrDefault("startDate", null);
        try {
            this.startDate = LocalDateTime.parse(startDateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } catch (DateTimeParseException exception) {
            LOGGER.warn("Failed to parse startDateTime (will set to null) {}", startDateTime, exception);
        }

        this.about = (String) properties.getOrDefault("about", null);
        this.jobTitle = (String) properties.getOrDefault("jobTitle", null);
        this.company = (String) properties.getOrDefault("company", null);
        this.guid = (String) properties.getOrDefault("guid", null);
        this.jobId = (Integer) properties.getOrDefault("jobId", null);
    }

    /**
     * @param o Other object to compare
     * @return true iff parameter o has the same guid and userId
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return guid.equals(job.guid) &&
                jobId.equals(job.jobId);
    }

    /**
     * @return Hashcode (calculated from guid and jobId)
     */
    @Override
    public int hashCode() {
        return Objects.hash(guid, jobId);
    }
}
