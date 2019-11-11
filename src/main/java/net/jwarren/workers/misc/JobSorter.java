package net.jwarren.workers.misc;

import net.jwarren.workers.model.Certificate;
import net.jwarren.workers.model.Job;
import net.jwarren.workers.model.Worker;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class JobSorter implements Comparator<Job> {

    private final Worker worker;

    public JobSorter(Worker forWorker) {
        this.worker = forWorker;
    }

    @Override
    public int compare(Job job, Job t1) {
        return 0; // TODO implement job sorter
    }

    @Override
    public boolean equals(Object o) {
        return false; // TODO implement job sorter
    }

    public static boolean workerHasAllCertificates(Worker worker, Collection<Certificate> certificates) {
        Set<Certificate> workerCerts = new HashSet<>(worker.getCertificates());
        Set<Certificate> jobCerts = new HashSet<>(certificates);
        return workerCerts.containsAll(jobCerts);
    }
}
