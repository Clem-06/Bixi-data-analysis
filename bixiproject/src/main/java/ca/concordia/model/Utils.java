package ca.concordia.model;

import java.util.HashSet;
import java.util.Set;

public final class Utils {

    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;

    private Utils() {
        // prevent instantiation
    }


    public static short dayOfYear(int secondsSinceEpoch) {
        int yearStart = 1735689600; // Jan 1 2025 00:00:00 UTC in seconds
        return (short)((secondsSinceEpoch - yearStart) / SECONDS_PER_DAY);
    }

    public static byte month(int secondsSinceEpoch) {
        int doy = dayOfYear(secondsSinceEpoch) + 1; // convert to 1–365

        int[] monthLengths = {31,28,31,30,31,30,31,31,30,31,30,31};

        int month = 1;
        for (int length : monthLengths) {
            if (doy <= length) break;
            doy -= length;
            month++;
        }
        return (byte) month;
    }

    public static byte hour(int secondsSinceEpoch) {
        return (byte) ((secondsSinceEpoch / SECONDS_PER_HOUR) % 24);
    }
}