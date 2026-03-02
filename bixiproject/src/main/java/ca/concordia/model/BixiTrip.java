package ca.concordia.model;

public class BixiTrip {

    public BixiTrip() {
    }


    public BixiTrip(int startStationName, int startStationArrondissement, int endStationName, int endStationArrondissement,
                    int startTimeMS, int endTimeMS,
                    short dayofYear, byte month, byte hour, int duration) {
        this.startStationName = startStationName;
        this.startStationArrondissement = startStationArrondissement;
        this.endStationName = endStationName;
        this.endStationArrondissement = endStationArrondissement;
        this.startTimeMS = startTimeMS;
        this.endTimeMS = endTimeMS;
        this.dayofYear = dayofYear;
        this.month = month;
        this.hour = hour;
        this.duration = duration;
    }

    private int startStationName;             //USE STATION DICT
    private int startStationArrondissement;   //USE ARRONDISEMENT DICT

    private int endStationName;              //USE STATION DICT
    private int endStationArrondissement;    //USE ARRONDISEMENT DICT

    private int startTimeMS; //toString to seperate the values?
    private int endTimeMS;

    private short dayofYear;  //COmputed when parsed   0-364
    private byte month;      //COmputed when parsed   0- 11
    private byte hour;       //COmputed when parsed   0- 23

    private int duration; //Float? return endTime(just the hour,min,sec) - start Time()

    public void display() {
        System.out.println(startStationName + " - " + startStationArrondissement + " - " + endStationName + " - " + endStationArrondissement + " - " + startTimeMS + " - " +
                endTimeMS + " - " + duration);
    }


    public int getDayofYear() {
        int intDay = dayofYear;
        return intDay;
    }
}

