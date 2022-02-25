package com.example.weather_app.service;

import com.example.weather_app.model.Forecast;
import com.example.weather_app.model.Location;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class WeatherbitForecastProvider implements ForecastProvider{

    @Override
    public Forecast getForecast(Location location, Date date) {

        // zaimplementować logikę
        return null;
    }
}
