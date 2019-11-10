package net.jwarren.workers.misc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);

    /**
     * @param word Input word to modify
     * @return The input word in Titlecase (first letter capitalised)
     */
    public static String toTitleCase(String word) {
        if (word.length() <= 1) {
            return word.toUpperCase();
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    /**
     * @param input String representation of a decimal number, e.g. 1.5567
     * @return Double value (if parseable; otherwise, return null)
     */
    public static Double doubleOrNull(String input) {
        try {
            return Double.valueOf(input);
        } catch (NumberFormatException exception) {
            LOGGER.warn("Failed to parse Location value: {} (setting to null)", input, exception);
            return null;
        }
    }

    /**
     * @param amount String with preceding currency symbol, e.g. $1.55
     * @return Float value without currency symbol (if parseable; otherwise, return null)
     */
    public static Float parseCashAmount(String amount) {
        try {
            if (amount.length() > 1) {
                return Float.parseFloat(amount.substring(1));
            }
        } catch (NumberFormatException exception) {
            LOGGER.warn("Unable to parse amount {}, will set to null", amount);
        }

        return null;
    }
}
