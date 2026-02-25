package ca.concordia.model;

public final class Utils {

    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;

    private Utils() {
        // prevent instantiation
    }

    /** Returns zero-based day of year (0–364) for 2025, given seconds since epoch. */
    public static int dayOfYear(int secondsSinceEpoch) {
        int yearStart = 1735689600; // Jan 1 2025 00:00:00 UTC in seconds
        return (secondsSinceEpoch - yearStart) / SECONDS_PER_DAY;
    }

    /** Returns 1–12 month for 2025, given seconds since epoch. */
    public static int month(int secondsSinceEpoch) {
        int doy = dayOfYear(secondsSinceEpoch) + 1; // convert to 1–365

        int[] monthLengths = {31,28,31,30,31,30,31,31,30,31,30,31};

        int month = 1;
        for (int length : monthLengths) {
            if (doy <= length) break;
            doy -= length;
            month++;
        }
        return month;
    }

    /** Returns hour of day (0–23) for 2025, given seconds since epoch. */
    public static int hour(int secondsSinceEpoch) {
        return (secondsSinceEpoch / SECONDS_PER_HOUR) % 24;
    }
}