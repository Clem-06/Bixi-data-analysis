package ca.concordia.controller;

import ca.concordia.Main;
import ca.concordia.model.*;
import ca.concordia.model.linkedList.List;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;


import static ca.concordia.model.Utils.*;


public class BixiController implements IBixiController {
    public static int objectCounter = 0;
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
    public static List[] durationTable = new List[1304000];
    public static List[] arrondissementTable = new List[31];

    private int maxDurationMinSeen = 0;
    private final int[] nonEmptyIndices = new int[4137];
    private int nonEmptyCount = 0;

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
            arrondissementTable[i] = new List<>();
        }
    }

    //make station and arrondissement dictionaries
    public static myDictionary stationDict = new myDictionary("src/main/java/stations.txt");
    public static myDictionary arronDict = new myDictionary("src/main/java/arrondissement.txt");

    @Override
    public void loadFile(String filePath) {
        System.out.println(stationDict.getSize());
        System.out.println(arronDict.getSize());

        String testpath = "src/main/java/big_bixi.csv"; //changed tiny to 150 lines
        filePath = testpath; //remove for final

        // Implementation to load the file
        System.out.println("Loading file from: " + filePath);
        System.out.println("\n                <=========================> \n");
        Path path = Path.of(filePath);
//      src/main/java/SIZE_bixi.csv
        try (var lines = Files.lines(path)) {
            lines.skip(1).forEach(this::parseLine);

        } catch (IOException e) {
            e.printStackTrace();
        }

        sortNonEmptyIndices();
        System.out.println("LOADED ALL ITEMS IN FILE ----------------------------------------------");
        System.out.println("Total number of trips: " + objectCounter);
        System.out.println("Unique stations: " + stationDict.getSize());

        System.out.println("TEST FUNCTIONS ----------------------------------------------");

        int k = 3;
        Iterable<Arrondissement> topArrs = getTopArrondissements(k);

        System.out.println("Top " + k + " arrondissements:");
        for (Arrondissement a : topArrs) {
            System.out.println(a.getName() + " - Trips: " + a.getSize());
        }

    }


    private void parseLine(String data) {

        String[] fields = data.split(","); //APARENTLY THIS IS SLOW, MAKE CUSTOM IsF NEED QUICKER LOADING

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
                if (dayOfYear(startSec) == -1 || dayOfYear(endSec) == -1) {
                    return;
                }


                BixiTrip toAdd = new BixiTrip(startStation, startArr, endStation, endArr, startSec, endSec,
                        dayOfYear(startSec), hour(startSec), duration);
                //add to tables needed

//                    dateTablePush(toAdd);
//                    startStatTablePush(toAdd);
//                    endStatTablePush(toAdd);

                //durationTablePush(toAdd);
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
        arrondissementTable[toAdd.getStartStationArrondissement()].push(toAdd);
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
    public Iterable<BixiTrip> getTripsByStartTime(String startTime, String finalTime) {
        return null;
    }

    @Override
    public Iterable<Arrondissement> getTopArrondissements(int k) { //the arrondissement.txt file is sorted thanks to previous outputs
        List<Arrondissement> arrList = new List<>();
        if (k <= 0) {throw new IllegalArgumentException("Invalid input for getting top arrondissements");}
        for (int i = 0; i < k; i++) {
            arrList.push(new Arrondissement(arrondissementTable[k].sizeOf(), arronDict.getWord(k)));
        }


        arrList.push(new Arrondissement(676767676, "BIG BONUS FOR TEST"));

        return arrList;
    }

    @Override
    public Iterable<BixiStation> getTopStations(int k, String startDate, String endDate) {
        return null;
    }

    @Override
    public RushHour getRushHourOfMonth(int month) {
        return null; //place holder
    }


}
