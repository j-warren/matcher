package net.jwarren.workers.misc;

public class WorkerNotFoundException extends RuntimeException {
    public WorkerNotFoundException(Integer id) {
        super("Could not retrieve worker with ID " + id);
    }
}
