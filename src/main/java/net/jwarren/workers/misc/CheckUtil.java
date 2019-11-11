package net.jwarren.workers.misc;

public class CheckUtil {
    public static void throwIfNull(Object item, String reason) throws IncomparableException {
        if (item == null) {
            throw new IncomparableException(reason);
        }
    }
}
