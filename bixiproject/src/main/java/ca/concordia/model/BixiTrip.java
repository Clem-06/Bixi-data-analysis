package ca.concordia.model;

public class BixiTrip {

    public BixiTrip() {} //default constructor

    public BixiTrip(String startStationName, String startStationArrondissement, float startStationLatitude, float startStationLongitude, String endStationName,
                    String endStationArrondissement, float endStationLatitude, float endStationLongitude, int startTimeMS, int endTimeMS,
                    int dayofYear, int month, int hour, int duration) {
        this.startStationName = startStationName;
        this.startStationArrondissement = startStationArrondissement;
        this.startStationLatitude = startStationLatitude;
        this.startStationLongitude = startStationLongitude;
        this.endStationName = endStationName;
        this.endStationArrondissement = endStationArrondissement;
        this.endStationLatitude = endStationLatitude;
        this.endStationLongitude = endStationLongitude;
        this.startTimeMS = startTimeMS;
        this.endTimeMS = endTimeMS;
        this.dayofYear = dayofYear;
        this.month = month;
        this.hour = hour;
        this.duration = duration;
    }

    private String startStationName;
    private String startStationArrondissement;
    private float startStationLatitude; //int?
    private float startStationLongitude;

    private String endStationName;
    private String endStationArrondissement;
    private float  endStationLatitude;
    private float endStationLongitude;

    private int startTimeMS; //toString to seperate the values?
    private int endTimeMS;

    private int dayofYear;  //COmputed when parsed   0-364
    private int month;      //COmputed when parsed   0- 11
    private int hour;       //COmputed when parsed   0- 23

    private int duration; //Float? return endTime(just the hour,min,sec) - start Time()

    public void display(){
        System.out.println(startStationName+" - "+ startStationArrondissement +" - "+ startStationLatitude +" - "+ startStationLongitude
                +" - "+ endStationName +" - "+ endStationArrondissement +" - "+ endStationLatitude +" - "+ endStationLongitude +" - "+ startTimeMS +" - "+
                endTimeMS +" - "+ duration);
    }

    public String getStartStationName() {
        return startStationName;
    }

    public String getStartStationArrondissement() {
        return startStationArrondissement;
    }

    public float getStartStationLatitude() {
        return startStationLatitude;
    }

    public float getStartStationLongitude() {
        return startStationLongitude;
    }

    public String getEndStationName() {
        return endStationName;
    }

    public String getEndStationArrondissement() {
        return endStationArrondissement;
    }

    public float getEndStationLatitude() {
        return endStationLatitude;
    }

    public float getEndStationLongitude() {
        return endStationLongitude;
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
