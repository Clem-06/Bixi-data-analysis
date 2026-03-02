package ca.concordia.model;

import ca.concordia.controller.BixiController;

public class BixiTrip {

    public BixiTrip() {
    }


    public BixiTrip(int startStationName, int startStationArrondissement, int endStationName, int endStationArrondissement,
                    int startTimeMS, int endTimeMS,
                    short dayofYear, byte hour, int duration) {
        this.startStationName = startStationName;
        this.startStationArrondissement = startStationArrondissement;
        this.endStationName = endStationName;
        this.endStationArrondissement = endStationArrondissement;
        this.startTimeMS = startTimeMS;
        this.endTimeMS = endTimeMS;
        this.dayOfYear = dayofYear;
        this.hour = hour;
        this.duration = duration;
    }

    private int startStationName;             //USE STATION DICT
    private int startStationArrondissement;   //USE ARRONDISSEMENT DICT

    private int endStationName;              //USE STATION DICT
    private int endStationArrondissement;    //USE ARRONDISSEMENT DICT

    private int startTimeMS; //toString to separate the values?
    private int endTimeMS;

    private short dayOfYear;  //Computed when parsed   0-364    0 INDEXED BE CAREFULLLL
    private byte hour;       //Computed when parsed   1 - 24

    private int duration; //Float? return endTime(just the hour,min,sec) - start Time()

    public void display() {
        String output = String.format(
                "%-5s (%-65s)  %-5s  -  %-5s (%-65s)  %-5s  -  %-12d  %-12d  %-8d",
                startStationName,
                BixiController.stationDict.getWord(startStationName),
                startStationArrondissement,
                endStationName,
                BixiController.stationDict.getWord(endStationName),
                endStationArrondissement,
                startTimeMS,
                endTimeMS,
                duration
        );
        System.out.println(output);
    }

    public int getDayOfYear() {
        int intDay = dayOfYear;
        return intDay;
    }

    public int getStartStationName() {
        return startStationName;
    }

    public int getStartStationArrondissement() {
        return startStationArrondissement;
    }

    public int getEndStationName() {
        return endStationName;
    }

    public int getEndStationArrondissement() {
        return endStationArrondissement;
    }

    public int getStartTimeMS() {
        return startTimeMS;
    }

    public int getEndTimeMS() {
        return endTimeMS;
    }

    public byte getHour() {
        return hour;
    }

    public int getDuration() {
        return duration;
    }
}

