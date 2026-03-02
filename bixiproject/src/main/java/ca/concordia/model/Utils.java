package ca.concordia.model;

public final class Utils {

    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;

    private Utils() {
        // prevent instantiation
    }


    public static short dayOfYear(int secondsSinceEpoch) {

        int yearStart = 1735689600; // Jan 1 2025 00:00:00 UTC
        int diff = secondsSinceEpoch - yearStart;

        if (diff < 0) {
            return -1; // before 2025
        }

        int day = diff / SECONDS_PER_DAY + 1; // make it 1-indexed

        if (day < 1 || day > 365) {
            return -1; // outside 2025 range
        }

        return (short) day;
    }

    public static byte hour(int secondsSinceEpoch) {
        return (byte) ((secondsSinceEpoch / SECONDS_PER_HOUR) % 24);
    }
}