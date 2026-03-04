package ca.concordia.controller;

import ca.concordia.Main;
import ca.concordia.model.*;
import ca.concordia.model.linkedList.List;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;


import static ca.concordia.model.Utils.*;


public class BixiController implements IBixiController {
    public static int objectCounter = 0;
    private static final int MAX_DURATION_MIN = 24 * 60 * 7; //Huge duration, unlikely to be a proper trip, but the data includes outliers (likely stolen bikes)
    private static final int[] MONTH_LENGTHS = {
            31, // Jan
            28, // Feb
            31, // Mar
            30, // Apr
            31, // May
            30, // Jun
            31, // Jul
            31, // Aug
            30, // Sep
            31, // Oct
            30, // Nov
            31  // Dec
    };
    //make tables and fill up with empty lists    --- COULD WE MAKE LISTS  only IF NEEDED?
    public static List[] dateTable = new List[366];
    public static List[] startStatTable = new List[1304];
    public static List[] endStatTable = new List[1304];
    public static List[] durationTable = new List[MAX_DURATION_MIN + 1];
    public static Arrondissement[] arrondissementTable = new Arrondissement[31];

    private int maxDurationMinSeen = 0;
    private final int[] nonEmptyIndices = new int[4137];
    private int nonEmptyCount = 0;

    private static int monthStartDayIndex(int month) {
        int start = 0;
        for (int i = 0; i < month - 1; i++) {
            start += MONTH_LENGTHS[i];
        }
        return start;
    }

    private static int monthNumDays(int month) {
        return MONTH_LENGTHS[month - 1];
    }

    //make station and arrondissement dictionaries
    public static myDictionary stationDict = new myDictionary("src/main/java/stations.txt");
    public static myDictionary arronDict = new myDictionary("src/main/java/arrondissement.txt");

    static {
        for (int i = 0; i < dateTable.length; i++) {
            dateTable[i] = new List<>();
        }
        for (int i = 0; i < startStatTable.length; i++) {
            startStatTable[i] = new List<>();
        }
        for (int i = 0; i < endStatTable.length; i++) {
            endStatTable[i] = new List<>();
        }
        for (int i = 0; i < arrondissementTable.length; i++) {
            arrondissementTable[i] = new Arrondissement(0, arronDict.getWord(i));
        }
    }

    @Override
    public void loadFile(String filePath) {
//        System.out.println(stationDict.getSize());
//        System.out.println(arronDict.getSize());

        //String testpath = "src/main/java/Big_bixi.csv"; //changed tiny to 150 lines
        //filePath = testpath; //remove for final

        // Implementation to load the file
        System.out.println("Loading file from: " + filePath);
        System.out.println("\n                <=========================> \n");
        Path path = Path.of(filePath);
//      src/main/java/SIZE_bixi.csv
        //timer
        long startTime = System.nanoTime();

        try (BufferedReader br = Files.newBufferedReader(Path.of(filePath))) {
            String line;


            br.readLine();      // Skip header

            while ((line = br.readLine()) != null) {
                try {
                    parseLine(line);
                } catch (Exception ignored) {
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.nanoTime();

        System.out.println("Loading time: " + (endTime - startTime) / 1_000_000_000 + " s");
        sortNonEmptyIndices();
        System.out.println("LOADED ALL ITEMS IN FILE ----------------------------------------------");
        System.out.println("Total number of trips: " + objectCounter);
        System.out.println("Unique stations: " + stationDict.getSize());

        System.out.println("TEST FUNCTIONS ----------------------------------------------");


    }


    private void parseLine(String data) {

//      String[] fields = data.split(","); //APARENTLY THIS IS SLOW, MAKE CUSTOM IsF NEED QUICKER LOADING
        String[] fields = parseLineToArray(data); //custom split

        if (fields.length == 10) {

            for (String f : fields) {
                if (f == null || f.isEmpty()) {
                    return;
                }
            }
            //condition for valid trip -- DO WE NEED OTHER CHECKS - one is def broken
            try {
                int startSec = (int) (Long.parseLong(fields[8]) / 1000);
                int endSec = (int) (Long.parseLong(fields[9]) / 1000);
                int duration = (endSec - startSec) / 60;

                //Use dictionaries to convert stations and arrondissements to ints:
                int startStation = stationDict.getId(fields[0]);
                int endStation = stationDict.getId(fields[4]);
                int startArr = arronDict.getId(fields[1]);
                int endArr = arronDict.getId(fields[5]);

                //checking for bad date:
                short startDay = dayOfYear(startSec);
                short endDay = dayOfYear(endSec);

                if (startDay == -1 || endDay == -1) return;
                if (startDay == -1 || endDay == -1) {
                    return;
                }


                BixiTrip toAdd = new BixiTrip(startStation, startArr, endStation, endArr, startSec, endSec,
                        startDay, hour(startSec), duration);
                //add to tables needed

                dateTablePush(toAdd);
                startStatTablePush(toAdd);
                endStatTablePush(toAdd);
                durationTablePush(toAdd);
                arrondissementTablePush(toAdd);

                //loading progress logic:
                objectCounter++;
                if (objectCounter % 100_000 == 0) {
                    System.out.println("Load progress: " + objectCounter / 100_000);
                }

            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number format");
            }
        }
    }


    void dateTablePush(BixiTrip newTrip) {
        dateTable[newTrip.getDayOfYear()].push(newTrip);
    }

    void startStatTablePush(BixiTrip newTrip) {
        startStatTable[newTrip.getStartStationName()].push(newTrip);
    }

    void endStatTablePush(BixiTrip newTrip) {
        endStatTable[newTrip.getEndStationName()].push(newTrip);
    }

    void durationTablePush(BixiTrip newTrip) {
        int d = newTrip.getDuration(); // minutes

        if (d > maxDurationMinSeen)
            maxDurationMinSeen = d;

        if (durationTable[d] == null) {
            durationTable[d] = new List<>();
            nonEmptyIndices[nonEmptyCount++] = d;
        }

        durationTable[d].push(newTrip);
    }

    private void arrondissementTablePush(BixiTrip toAdd) {
        arrondissementTable[toAdd.getStartStationArrondissement()].increment();
    }

    private void sortNonEmptyIndices() {
        for (int i = 1; i < nonEmptyCount; i++) {
            int key = nonEmptyIndices[i];
            int j = i - 1;

            while (j >= 0 && nonEmptyIndices[j] > key) {
                nonEmptyIndices[j + 1] = nonEmptyIndices[j];
                j--;
            }

            nonEmptyIndices[j + 1] = key;
        }
    }

    //requirement functions:
    @Override
    public Iterable<BixiTrip> getTripsByStation(String stationName, String mode) {
        int startID = stationDict.getId(stationName);
        int endID = stationDict.getId(stationName);

        if (mode.equals("start") || mode.equals("Start")) {
            return startStatTable[startID];
        }
        if (mode.equals("end") || mode.equals("End")) {
            return endStatTable[endID];
        }
        if (mode.equals("both") || mode.equals("Both")) {
            List<BixiTrip> wholeList = new List<>();
            wholeList.append(startStatTable[startID]);
            wholeList.append(endStatTable[endID]);
            return wholeList;
        } else {
            throw new IllegalArgumentException("Incorrect mode for getTripsBystation - start, end, both");
        }
    }

    @Override
    public Iterable<BixiTrip> getTripsByMonth(String dateIn) {
        String[] date = dateIn.split("-");
        if (date.length != 2) {
            throw new IllegalArgumentException("Invalid month/year input");
        }
        if (!date[0].equals("2025")) {
            throw new IllegalArgumentException("Invalid year input");
        }
        int month = Integer.parseInt(date[1]);
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month input");
        }
        List<BixiTrip> monthTrips = new List<>();

        int startOfMonth = 1;
        int lengthOfMonth = MONTH_LENGTHS[month - 1];

        for (int i = 0; i < month - 1; i++) {
            startOfMonth += MONTH_LENGTHS[i];
        }
        for (int i = startOfMonth; i < startOfMonth + lengthOfMonth; i++) {
            monthTrips.append(dateTable[i]);
        }
        return monthTrips;
    }

    @Override
    public Iterable<BixiTrip> getTripsByDuration(float minDuration) {
        int threshold = (int) Math.floor(minDuration);
        int startBucket = threshold + 1;

        List<BixiTrip> result = new List<>();

        for (int i = nonEmptyCount - 1; i >= 0; i--) {
            int d = nonEmptyIndices[i];

            if (d < startBucket)
                break;

            List<BixiTrip> bucket = durationTable[d];

            for (BixiTrip t : bucket)
                result.pushBack(t);
        }

        return result;
    }


    @Override
    public Iterable<Arrondissement> getTopArrondissements(int k) { //the arrondissement.txt file is sorted thanks to previous outputs
        List<Arrondissement> arrList = new List<>();
        if (k <= 0 || k > 31) {
            throw new IllegalArgumentException("Invalid input for getting top arrondissements");
        }
        for (int i = 0; i < k; i++) {
            arrList.pushBack(arrondissementTable[i]);
        }

        return arrList;
    }


    @Override
    public RushHour getRushHourOfMonth(int month) {
        if (month < 1 || month > 12) throw new IllegalArgumentException("Invalid month input");

        int startDay = monthStartDayIndex(month);
        int daysInMonth = monthNumDays(month);

        int[] hourCounts = new int[24];

        for (int day = startDay; day < startDay + daysInMonth; day++) {
            List<BixiTrip> dayTrips = dateTable[day];

            for (BixiTrip t : dayTrips) {
                int h = t.getHour();

                if (h >= 0 && h < 24) hourCounts[h]++;
            }
        }

        int bestHour = 0;
        int bestTrips = hourCounts[0];

        for (int i = 1; i < 24; i++) {
            if (hourCounts[i] > bestTrips) {
                bestTrips = hourCounts[i];
                bestHour = i;
            }
        }

        return new RushHour(bestHour, bestTrips);
    }

    @Override
    public void compareTwoMonths(int m1, int m2, int k) {

        if (m1 > 12 || m1 < 1 || m2 > 12 || m2 < 1) {
            throw new IllegalArgumentException("Invalid month input for compareMonths");
        }
        if (k <= 0 || k > 31) {
            throw new IllegalArgumentException("Invalid input for compareMonths");
        }
        compareMonth(m1, k);
        compareMonth(m2, k);
    }

    public void compareMonth(int month, int k) {
        int totalTrips = 0;

        int startDay = monthStartDayIndex(month);
        int daysInMonth = monthNumDays(month);

        int[] startStationCount = new int[stationDict.getSize()];
        int[] endStationCount = new int[stationDict.getSize()];

        List<BixiTrip> monthTrips = new List<>();

        // gather trips for the month
        for (int day = startDay; day < startDay + daysInMonth; day++) {
            List<BixiTrip> dayTrips = dateTable[day - 1]; // convert to 0-based
            monthTrips.append(dayTrips);
        }

        // count total trips, start and end stations
        for (BixiTrip t : monthTrips) {
            totalTrips++;
            startStationCount[t.getStartStationName()]++;
            endStationCount[t.getEndStationName()]++;
        }

        // Top-K start/end stations
        List<Arrondissement> topStart = getTopStations(startStationCount, k);
        List<Arrondissement> topEnd   = getTopStations(endStationCount, k);

        // Rush hour
        RushHour rh = getRushHourOfMonth(month);

        // Print results for this month
        System.out.println("\n--- Month " + month + " ---");
        System.out.println("Total trips: " + totalTrips);

        System.out.println("Top " + k + " start stations:");
        for (Arrondissement a : topStart)
            System.out.println(a.getName() + " - " + a.getSize());

        System.out.println("Top " + k + " end stations:");
        for (Arrondissement a : topEnd)
            System.out.println(a.getName() + " - " + a.getSize());

        System.out.println("Rush hour: " + rh.getHour() + "h, average trips per day: " + rh.getTripCount());
    }

    public List<Arrondissement> getTopStations(int[] counts, int k) {
        List<Arrondissement> topStationsList = new List<>();
        int n = counts.length;

        // Create an array of indices to track which station each count belongs to
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) indices[i] = i;

        // Sort indices so that the top K stations are first
        indices = truncatedBubbleSort(counts, indices, k);

        // Build topStationsList using the sorted indices
        for (int i = 0; i < k && i < n; i++) {
            int id = indices[i];
            if (counts[id] == 0) break; // stop if station has 0 trips
            topStationsList.pushBack(new Arrondissement(counts[id], stationDict.getWord(id)));
        }

        return topStationsList;
    }

    private Integer[] truncatedBubbleSort(int[] counts, Integer[] indices, int K) {
        int n = counts.length;

        // Bubble the top K largest counts to top
        for (int i = 0; i < K; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (counts[indices[j]] < counts[indices[j + 1]]) {
                    int tmp = indices[j];
                    indices[j] = indices[j + 1];
                    indices[j + 1] = tmp;
                }
            }
        }

        return Arrays.copyOfRange(indices, 0, K); // return only top K indices
    }

}
