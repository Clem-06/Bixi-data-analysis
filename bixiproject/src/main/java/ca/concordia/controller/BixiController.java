package ca.concordia.controller;

import ca.concordia.Main;
import ca.concordia.model.*;
import ca.concordia.model.linkedList.List;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Set;

import static ca.concordia.model.Utils.*;


public class BixiController implements IBixiController {

    private static Set<String> words = new HashSet<>();



    public static int[] dataSize = new int[12];

    public static List[] dateTable = new List[365];
    public static int counter = 0;




    static {
        for(int i = 0; i<365; i++){
        dateTable[i] = new List<>();
        }
    }

    @Override
    public void loadFile(String filePath) {
        String testpath = "src/main/java/big_bixi.csv";
        filePath = testpath; //remove for final

        // Implementation to load the file
        System.out.println("Loading file from: " + filePath);
        System.out.println("\n                <=========================> \n");
        Path path = Path.of(filePath);
//      src/main/java/SIZE_bixi.csv
        try (var lines = Files.lines(path)) {
            //         lines.skip(1).forEach(this::parseLine);

            lines.skip(1).forEach(this::UniqueStations);
            String[] wordArray = words.toArray(new String[0]);

            for (int i = 0; i < wordArray.length; i++) {
                System.out.print(wordArray[i] + ", ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

//        for (int i = 0; i < dateTable.length; i++) {
//            System.out.print(dateTable[i].sizeOf()+ ", ");
//        }

//        for (int i = 0; i<dataSize.length;i++) {
//            System.out.println(dataSize[i] + " objects of size " + i);
//        }

    }
    private void UniqueStations(String data){
        String[] fields = data.split(",");
        words.add(fields[1]);
        words.add(fields[5]);
    }

    private void parseLine(String data) {
        String[] fields = data.split(",");
        BixiTrip toAdd;
        dataSize[fields.length]++;


        if (fields.length == 10) {

//            for(int i = 0; i< 10;i++){
//                System.out.println(i+ ":");
//                System.out.println(fields[i]);
//            }

            try {
                int startSec = (int)(Long.parseLong(fields[8]) / 1000);
                int endSec   = (int)(Long.parseLong(fields[9]) / 1000);

                toAdd = new BixiTrip(fields[0], fields[1], fields[4], fields[5], startSec, endSec,
                        dayOfYear(startSec),month(startSec),hour(startSec), 0);//CALCULATE DURATION ALSO IN UTILS

                dayTable(toAdd);

//                System.out.println("NEW OBJECT SUCESS ---------------------------------------");
//                toAdd.display();
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format");
            }
        }
    }

    void dayTable(BixiTrip newTrip){
        dateTable[DateHash(newTrip)].push(newTrip);

    }

    int DateHash(BixiTrip data){
//        int out = data.getDayofYear())%365;
        return ((Integer)data.getDayofYear()).hashCode()%365;
//        return out;
    }


    @Override
    public Iterable<BixiTrip> getTripsByStation(String stationName, String mode) {
        return null;
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
    public Iterable<String> getTopArrondissements(int k) {
        return null;
    }

    @Override
    public Iterable<String> getTopStations(int k, String startDate, String endDate) {
        return null;
    }

    @Override
    public int getRushHourOfMonth(int month) {
        return 0;
    }


}
