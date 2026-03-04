package ca.concordia.view;

import ca.concordia.controller.BixiController;
import ca.concordia.controller.IBixiController;
import ca.concordia.model.Arrondissement;
import ca.concordia.model.BixiTrip;
import ca.concordia.model.RushHour;

import java.util.Scanner;

public class BixiView {

    private IBixiController controller;

    /**
     * Constructor for BixiView.
     * Initializes the controller
     */
    public BixiView(){
        controller = new BixiController();
    }

    /**
     * Starts the Bixi data viewer application.
     */
    public void start() {
        String message = "Welcome to the Bixi Data Viewer!";
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the path to the Bixi data file: ");
        String filePath = scanner.nextLine();
        controller.loadFile(filePath);
        //TODO - Complete

//        System.out.print("Enter minimum duration X (minutes): ");
//        float x = Float.parseFloat(scanner.nextLine());
//
//        Iterable<BixiTrip> trips = controller.getTripsByDuration(x);
//
//        for (BixiTrip t : trips)
//            t.display();
//
//        System.out.print("Enter month (1..12): ");
//        int month = Integer.parseInt(scanner.nextLine());
//
//        RushHour rh = controller.getRushHourOfMonth(month);
//
//        System.out.println("Rush hour: " + rh.getHour());
//        System.out.println("Trips during rush hour: " + rh.getTripCount());


        while (true) {

            System.out.println("\n==================================================");
            System.out.println("                BIXI DATA VIEWER");
            System.out.println("==================================================");
            System.out.println("1  - Requirement 1  : Trips by Station");
            System.out.println("2  - Requirement 2  : Trips by Month");
            System.out.println("3  - Requirement 3  : Trips by Duration");
            System.out.println("5  - Requirement 5  : Top K Arrondissements");
            System.out.println("7  - Requirement 7  : Rush Hour of a Month");
            System.out.println("8  - Requirement 8  : Compare Two Months");
            System.out.println("0  - Exit");
            System.out.println("==================================================");

            System.out.print("Select option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    System.out.println("\n==================================================");
                    System.out.println("REQUIREMENT 1 - LIST TRIPS BY STATION");
                    System.out.println("==================================================");

                    System.out.print("Enter station name: ");
                    String station = scanner.nextLine();

                    System.out.print("Enter mode (start / end / both): ");
                    String mode = scanner.nextLine();

                    Iterable<BixiTrip> stationTrips =
                            controller.getTripsByStation(station, mode);

                    for (BixiTrip t : stationTrips)
                        t.display();
                    break;

                case 2:
                    System.out.println("\n==================================================");
                    System.out.println("REQUIREMENT 2 - LIST TRIPS BY MONTH");
                    System.out.println("==================================================");

                    System.out.print("Enter month (1..12): ");
                    int month = Integer.parseInt(scanner.nextLine());

                    String formattedMonth =
                            month < 10 ? "0" + month : String.valueOf(month);

                    Iterable<BixiTrip> monthTrips =
                            controller.getTripsByMonth("2025-" + formattedMonth);

                    for (BixiTrip t : monthTrips)
                        t.display();
                    break;

                case 3:
                    System.out.println("\n==================================================");
                    System.out.println("REQUIREMENT 3 - LIST TRIPS BY MINIMUM DURATION");
                    System.out.println("==================================================");

                    System.out.print("Enter minimum duration X (minutes): ");
                    float x = Float.parseFloat(scanner.nextLine());

                    Iterable<BixiTrip> durationTrips =
                            controller.getTripsByDuration(x);

                    for (BixiTrip t : durationTrips)
                        t.display();
                    break;

                case 5:
                    System.out.println("\n==================================================");
                    System.out.println("REQUIREMENT 5 - TOP K ARRONDISSEMENTS");
                    System.out.println("==================================================");

                    System.out.print("Enter K: ");
                    int kArr = Integer.parseInt(scanner.nextLine());

                    Iterable<Arrondissement> arrs =
                            controller.getTopArrondissements(kArr);

                    for (Arrondissement a : arrs)
                        System.out.println(a.getName() +
                                " - Trips: " + a.getSize());
                    break;

                case 7:
                    System.out.println("\n==================================================");
                    System.out.println("REQUIREMENT 7 - RUSH HOUR OF A MONTH");
                    System.out.println("==================================================");

                    System.out.print("Enter month (1..12): ");
                    int rushMonth = Integer.parseInt(scanner.nextLine());

                    RushHour rh =
                            controller.getRushHourOfMonth(rushMonth);

                    System.out.println("Rush hour: " + rh.getHour());
                    System.out.println("Trips during rush hour: " +
                            rh.getTripCount());
                    break;

                case 8:
                    System.out.println("\n==================================================");
                    System.out.println("REQUIREMENT 8 - COMPARE TWO MONTHS");
                    System.out.println("==================================================");

                    System.out.print("Enter first month (1..12): ");
                    int m1 = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter second month (1..12): ");
                    int m2 = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter K (top stations): ");
                    int k = Integer.parseInt(scanner.nextLine());

                    controller.compareTwoMonths(m1, m2, k);
                    break;

                case 0:
                    System.out.println("Exiting program...");
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}