package ca.concordia.controller;

import ca.concordia.model.Arrondissement;
import ca.concordia.model.BixiStation;
import ca.concordia.model.BixiTrip;
import ca.concordia.model.RushHour;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BixiController implements IBixiController {

    public static int[] dataSize = new int[12];


    @Override
    public void loadFile(String filePath) {
        String testpath = "src/main/java/tiny_bixi.csv";
        filePath = testpath;

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
//        for (int i = 0; i<dataSize.length;i++) {
//            System.out.println(dataSize[i] + " objects of size " + i);
//        }

    }


    private void parseLine(String data) {
        // Implementation to parse data
        String[] fields = data.split(",");
        BixiTrip toAdd;
//        dataSize[fields.length]++;


        if (fields.length == 10) {

            for(int i = 0; i< 10;i++){
//                System.out.println(i+ ":");
                System.out.println(fields[i]);
            }

            try {
                toAdd = new BixiTrip(fields[0], fields[1], Float.parseFloat(fields[2]), Float.parseFloat(fields[3]), fields[4], fields[5],
                        Float.parseFloat(fields[6]), Float.parseFloat(fields[7]), Long.parseLong(fields[8]), Long.parseLong(fields[9]), 0);

                System.out.println("NEW OBJECT SUCESS ---------------------------------------");
                toAdd.display();
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format");
            }
            //HASH AND STORE TOADD:

        }
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
