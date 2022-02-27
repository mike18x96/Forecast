package com.example.weather_app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class Forecast {

    private Location location;
    private double windSpeed;
    private double temperature;

}
