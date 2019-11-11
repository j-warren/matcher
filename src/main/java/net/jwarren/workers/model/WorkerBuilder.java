package net.jwarren.workers.model;

import java.util.List;
import java.util.Map;

public class WorkerBuilder {
    private List<Certificate> certificates;
    private Address jobSearchAddress;
    private Transportation transportation;
    private Boolean hasDriversLicense;
    private Integer age;
    private String guid;
    private Integer userId;

    public WorkerBuilder setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
        return this;
    }

    public WorkerBuilder setJobSearchAddress(Address jobSearchAddress) {
        this.jobSearchAddress = jobSearchAddress;
        return this;
    }

    public WorkerBuilder setTransportation(Transportation transportation) {
        this.transportation = transportation;
        return this;
    }

    public WorkerBuilder setHasDriversLicense(Boolean hasDriversLicense) {
        this.hasDriversLicense = hasDriversLicense;
        return this;
    }

    public WorkerBuilder setAge(Integer age) {
        this.age = age;
        return this;
    }

    public WorkerBuilder setGuid(String guid) {
        this.guid = guid;
        return this;
    }

    public WorkerBuilder setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Worker createWorker() {
        return new Worker(certificates, jobSearchAddress, transportation, hasDriversLicense, age, guid, userId);
    }
}