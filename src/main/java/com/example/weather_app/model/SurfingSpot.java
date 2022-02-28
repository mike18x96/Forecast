package com.example.weather_app.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class SurfingSpot {
    private final Location location;
    private final double temperature;
    private final double windSpeed;

    public SurfingSpot(Forecast forecast) {
        this.location = forecast.getLocation();
        this.temperature = forecast.getTemperature();
        this.windSpeed = forecast.getWindSpeed();
    }
}
