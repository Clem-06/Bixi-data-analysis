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

    public int badDateCounter = 0;

    //make tables and fill up with empty lists    --- COULD WE MAKE LISTS  only IF NEEDED?
    public static List[] dateTable = new List[366];
    public static List[] startStatTable = new List[1304];
    public static List[] endStatTable = new List[1304];
    public static List[] durationTable= new List[1304000];

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
        for (int i = 0; i < durationTable.length; i++) {
            durationTable[i] = new List<>();
        }
    }

    //make station and arrondissement dictionaries
    public static myDictionary stationDict = new myDictionary("src/main/java/stations.txt");
    public static myDictionary arronDict = new myDictionary("src/main/java/arrondissement.txt");

    @Override
    public void loadFile(String filePath) {
        System.out.println(stationDict.getSize());
        System.out.println(arronDict.getSize());

        String testpath = "src/main/java/small_bixi.csv"; //changed tiny to 150 lines
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

        System.out.println("LOADED ALL ITEMS IN FILE ----------------------------------------------");
        System.out.println("Total number of trips: " + objectCounter);
        System.out.println("Unique stations: " + stationDict.getSize());

        System.out.println("TEST FUNCTIONS ----------------------------------------------");
        String testStation = "Sewell / Roy";
        String mode = "both";
            System.out.println("\nTrips for station " + testStation + " in mode " + mode + ":");
            Iterable<BixiTrip> trips = getTripsByStation(testStation, mode);

            int count = 0;
            for (BixiTrip trip : trips) {
                trip.display();
                count++;
            }
            System.out.println("Total trips displayed: " + count);
            }

    private void parseLine(String data) {
        //loading progress logic:
        objectCounter++;
        if (objectCounter % 100_000 == 0) {
            System.out.println("Load progress: " + objectCounter / 100_000);
        }

        String[] fields = data.split(","); //APARENTLY THIS IS SLOW, MAKE CUSTOM IsF NEED QUICKER LOADING

        if (fields.length == 10) {//condition for valid trip -- DO WE NEED OTHER CHECKS - one is def broken
            try {
                int startSec = (int) (Long.parseLong(fields[8]) / 1000);
                int endSec = (int) (Long.parseLong(fields[9]) / 1000);
                int duration = endSec - startSec;

                //Use dictionaries to convert stations and arrondissements to ints:
                int startStation = stationDict.getId(fields[0]);
                int endStation = stationDict.getId(fields[4]);
                int startArr = arronDict.getId(fields[1]);
                int endArr = arronDict.getId(fields[5]);


                BixiTrip toAdd = new BixiTrip(startStation, startArr, endStation, endArr, startSec, endSec,
                        dayOfYear(startSec), month(startSec), hour(startSec), duration);
                //add to tables needed
                dateTablePush(toAdd);
                startStatTablePush(toAdd);
                endStatTablePush(toAdd);
                durationTablePush(toAdd);

            } catch (NumberFormatException e) {
                System.out.println("Invalid number format");
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
        durationTable[newTrip.getDuration()].push(newTrip);
    }



    //requirement functions:
    @Override
    public Iterable<BixiTrip> getTripsByStation(String stationName, String mode) {
        int startID = stationDict.getId(stationName);
        int endID = stationDict.getId(stationName);

        if(mode.equals("start")||mode.equals("Start")){
            return startStatTable[startID];
        }if(mode.equals("end")||mode.equals("End")){
            return endStatTable[endID];
        }if(mode.equals("both")||mode.equals("Both")){
            List<BixiTrip> wholeList = new List<>();
            wholeList.append(startStatTable[startID]);
            wholeList.append(endStatTable[endID]);
            return wholeList;
        }else {
            throw new IllegalArgumentException("Incorrect mode for getTripsBystation - start, end, both");
        }
    }

    @Override
    public Iterable<BixiTrip> getTripsByMonth(String month) {


        return null;
    }

    @Override
    public Iterable<BixiTrip> getTripsByDuration(float minDuration) {
        return null;
    }

    @Override
    public Iterable<BixiTrip> getTripsByStartTime(String startTime, String finalTime) {
        return null;
    }

    @Override
    public Iterable<Arrondissement> getTopArrondissements(int k) {
        return null;
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
