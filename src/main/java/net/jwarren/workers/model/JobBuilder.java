package net.jwarren.workers.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class JobBuilder {
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

    public JobBuilder setDriversLicenseRequired(Boolean driversLicenseRequired) {
        this.driversLicenseRequired = driversLicenseRequired;
        return this;
    }

    public JobBuilder setRequiredCertificates(List<Certificate> requiredCertificates) {
        this.requiredCertificates = requiredCertificates;
        return this;
    }

    public JobBuilder setLocation(Location location) {
        this.location = location;
        return this;
    }

    public JobBuilder setBillRate(Float billRate) {
        this.billRate = billRate;
        return this;
    }

    public JobBuilder setWorkersRequired(Integer workersRequired) {
        this.workersRequired = workersRequired;
        return this;
    }

    public JobBuilder setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public JobBuilder setAbout(String about) {
        this.about = about;
        return this;
    }

    public JobBuilder setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public JobBuilder setCompany(String company) {
        this.company = company;
        return this;
    }

    public JobBuilder setGuid(String guid) {
        this.guid = guid;
        return this;
    }

    public JobBuilder setJobId(Integer jobId) {
        this.jobId = jobId;
        return this;
    }

    public Job createJob() {
        return new Job(driversLicenseRequired, requiredCertificates, location, billRate, workersRequired, startDate, about, jobTitle, company, guid, jobId);
    }
}