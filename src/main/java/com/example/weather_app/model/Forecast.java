package com.example.weather_app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class Forecast {

    private Location location;
    private float windSpeed;
    private float temperature;

}
