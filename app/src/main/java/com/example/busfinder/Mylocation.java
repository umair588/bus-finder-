package com.example.busfinder;

public class Mylocation {

    private double latitude;
    private double longitude;

    public Mylocation(double longitude,double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public Mylocation(){}

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
