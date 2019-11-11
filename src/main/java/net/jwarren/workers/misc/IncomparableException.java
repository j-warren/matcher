package net.jwarren.workers.misc;

public class IncomparableException extends Exception {
    public IncomparableException(String internalReason) {
        super(internalReason);
    }
}
