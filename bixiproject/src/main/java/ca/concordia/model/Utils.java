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

    public static String[] parseLineToArray(String line) {
        int numColumns = 10;
        String[] fields = new String[numColumns];

        int fieldIndex = 0;
        int start = 0;
        int commaIndex;

        while (fieldIndex < numColumns - 1) {
            commaIndex = line.indexOf(',', start);
            if (commaIndex == -1) {
                // missing fields at end of line
                break;
            }
            fields[fieldIndex++] = line.substring(start, commaIndex);
            start = commaIndex + 1;
        }

        // last field
        fields[fieldIndex++] = (start < line.length()) ? line.substring(start) : "";

        // fill remaining missing fields with empty string
        while (fieldIndex < numColumns) {
            fields[fieldIndex++] = "";
        }

        return fields; // always length 10
    }

}