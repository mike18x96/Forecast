package com.example.weather_app.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SurfingSpot {
    private final Location location;
    private final String temperature;
    private final String windSpeed;
}
