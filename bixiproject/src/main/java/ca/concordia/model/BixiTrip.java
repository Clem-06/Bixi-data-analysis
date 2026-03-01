package ca.concordia.model;

public class BixiTrip {

    public BixiTrip() {}



    public BixiTrip(String startStationName, String startStationArrondissement, String endStationName,
                    String endStationArrondissement, int startTimeMS, int endTimeMS,
                    int dayofYear, int month, int hour, int duration) {
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

    private String startStationName;             //USE STATION DICT
    private String startStationArrondissement;   //USE ARRONDISEMENT DICT

    private String endStationName;              //USE STATION DICT
    private String endStationArrondissement;    //USE ARRONDISEMENT DICT

    private int startTimeMS; //toString to seperate the values?
    private int endTimeMS;

    private int dayofYear;  //COmputed when parsed   0-364
    private int month;      //COmputed when parsed   0- 11
    private int hour;       //COmputed when parsed   0- 23

    private int duration; //Float? return endTime(just the hour,min,sec) - start Time()

    public void display(){
        System.out.println(startStationName+" - "+ startStationArrondissement +" - "+ endStationName +" - "+ endStationArrondissement +" - "+ startTimeMS +" - "+
                endTimeMS +" - "+ duration);
    }

    public String getStartStationName() {
        return startStationName;
    }

    public String getStartStationArrondissement() {
        return startStationArrondissement;
    }

    public String getEndStationName() {
        return endStationName;
    }

    public String getEndStationArrondissement() {
        return endStationArrondissement;
    }


    public int getStartTimeMS() {
        return startTimeMS;
    }

    public int getEndTimeMS() {
        return endTimeMS;
    }

    public int getDuration() {
        return duration;
    }
    public int getDayofYear() {
        return dayofYear;
    }

    public int getMonth() {
        return month;
    }

    public int getHour() {
        return hour;
    }

}
