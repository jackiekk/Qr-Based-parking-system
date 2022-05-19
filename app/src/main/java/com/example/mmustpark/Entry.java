package com.example.mmustpark;

public class Entry {

    String Occupation;
    String ParkingArea;
    String Plateno;
    String Timein;

    public Entry(String occupation, String parkingArea, String plateno, String timein) {
        Occupation = occupation;
        ParkingArea = parkingArea;
        Plateno = plateno;
        Timein = timein;
    }

    public String getOccupation() {
        return Occupation;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }

    public String getParkingArea() {
        return ParkingArea;
    }

    public void setParkingArea(String parkingArea) {
        ParkingArea = parkingArea;
    }

    public String getPlateno() {
        return Plateno;
    }

    public void setPlateno(String plateno) {
        Plateno = plateno;
    }

    public String getTimein() {
        return Timein;
    }

    public void setTimein(String timein) {
        Timein = timein;
    }
}
