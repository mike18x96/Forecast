package com.example.weather_app.model;

public enum Location {
    JASTARNIA(54.72, 18.61),
    BRIDGETOWN(-33.96, 116.14),
    FORTALEZA(-3.72, -38.54),
    PISSOURI(34.67, 32.70),
    LE_MORNE(-20.47, 57.34);

    private final double latitude;
    private final double longitude;

    Location(double latitude, double longitude) {
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
