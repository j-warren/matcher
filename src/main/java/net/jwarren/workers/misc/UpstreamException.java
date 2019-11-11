package net.jwarren.workers.misc;

/**
 * Exception class for handling connectivity issues with upstream data sources
 */
public class UpstreamException extends RuntimeException {
    /**
     * Log the technical details and include a user friendly message which *contains no private information*
     * @param messageForEndUser A user friendly error message of why this error occurred
     */
    public UpstreamException(String messageForEndUser) {
        super(messageForEndUser);
    }
}
