package net.jwarren.workers.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum Unit {
    KM, MI;

    private static Logger LOGGER = LoggerFactory.getLogger(Unit.class);

    /**
     * Accepts a String and attempts to return the first case insensitively matching Unit (or null, if no match was found)
     * @param value String representation of a Unit
     * @return Unit or null
     */
    public static Unit toUnitCaseInsensitiveOrNull(String value) {
        Unit result = Arrays.stream(Unit.values())
                .filter(t -> t.name().equalsIgnoreCase(value))
                .findFirst().orElse(null);
        if (result == null) {
            LOGGER.warn("Failed to parse Unit value: {} (setting to null)", value);
        }
        return result;
    }
}
