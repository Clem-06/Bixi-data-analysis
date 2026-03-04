package ca.concordia.view;

import ca.concordia.controller.BixiController;
import ca.concordia.controller.IBixiController;
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
    }
}