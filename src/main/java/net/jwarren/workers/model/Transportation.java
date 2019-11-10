package net.jwarren.workers.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum Transportation {
    CAR("CAR"), PUBLIC_TRANSPORT("PUBLIC TRANSPORT");

    private static final Logger LOGGER = LoggerFactory.getLogger(Transportation.class);
    private String name;

    Transportation(String expectedValue) {
        this.name = expectedValue;
    }

    /**
     * Accepts a String and attempts to return the first case insensitively matching Transportation (or null, if no match was found)
     * @param value String representation of a Transportation
     * @return Transportation or null
     */
    public static Transportation toTransportationCaseInsensitiveOrNull(String value) {
        Transportation result = Arrays.stream(Transportation.values())
                .filter(t -> t.name != null && t.name.equalsIgnoreCase(value))
                .findFirst().orElse(null);
        if (result == null) {
            LOGGER.warn("Failed to parse Transportation value: {} (setting to null)", value);
        }
        return result;
    }
}
