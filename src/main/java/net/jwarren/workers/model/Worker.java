package net.jwarren.workers.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class Worker {
    private List<Certificate> certificates;
    private Address jobSearchAddress;
    private Transportation transportation;
    private Boolean hasDriversLicense;
    private Integer age;
    private String guid;
    private Integer userId;
    // TODO: Consider adding rating, isActive, skills, availability, phone, email, name

    /**
     * Create a Worker from a map
     * @param properties Properties Map of field names and values
     */
    @JsonCreator
    public Worker(Map<String, Object> properties) {
        List<String> certificates = (List<String>) properties.getOrDefault("certificates", null);
        this.certificates = certificates == null ? null : certificates.stream().map(Certificate::new).collect(Collectors.toList());

        Map<String, Object> jobSearchAddress = (Map<String, Object>) properties.getOrDefault("jobSearchAddress", null);
        this.jobSearchAddress = new Address(jobSearchAddress);

        String transport = (String) properties.getOrDefault("transportation", null);
        this.transportation = Transportation.toTransportationCaseInsensitiveOrNull(transport);

        this.hasDriversLicense = (Boolean) properties.getOrDefault("hasDriversLicense", null);
        this.age = (Integer) properties.getOrDefault("age", null);
        this.guid = (String) properties.getOrDefault("guid", null);
        this.userId = (Integer) properties.getOrDefault("userId", null);
    }

    /**
     * @param o Other object to compare
     * @return true iff parameter o has the same guid and userId
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return guid.equals(worker.guid) &&
                userId.equals(worker.userId);
    }

    /**
     * @return Hashcode (calculated from guid and jobId)
     */
    @Override
    public int hashCode() {
        return Objects.hash(guid, userId);
    }
}
