package com.example.weather_app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SurfingSpot {
    private final Location location;
    private final double temperature;
    private final double windSpeed;
}
