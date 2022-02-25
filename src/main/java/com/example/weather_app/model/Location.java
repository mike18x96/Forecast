package com.example.weather_app.model;

public enum Location {
    JASTARNIA(54.72, 18.61);

    // dodać
    /*BRIDGETOWN (BARBADOS)
FORTALEZA (BRAZIL)
PISSOURI (CYPRUS)
LE MORNE (MAURITIUS)*/
    // wartości wziąć z tej strony: https://www.latlong.net/

    private final double latitude;
    private final double longitude;

    private Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
