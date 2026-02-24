package ca.concordia.model;

public class BixiTrip {

    public BixiTrip() {}

    public BixiTrip(String startStationName, String startStationArrondissement, float startStationLatitude, float startStationLongitude, String endStationName,
                    String endStationArrondissement, float endStationLatitude, float endStationLongitude, Long startTimeMS, Long endTimeMS, float duration) {

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

    private Long startTimeMS; //toString to seperate the values?
    private Long endTimeMS;

    private float duration; //Float? return endTime(just the hour,min,sec) - start Time()

    public void display(){
        System.out.println(startStationName+" - "+ startStationArrondissement +" - "+ startStationLatitude +" - "+ startStationLongitude
                +" - "+ endStationName +" - "+ endStationArrondissement +" - "+ endStationLatitude +" - "+ endStationLongitude +" - "+ startTimeMS +" - "+
                endTimeMS +" - "+ duration);
    }
}
