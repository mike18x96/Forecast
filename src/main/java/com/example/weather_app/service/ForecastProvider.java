package com.example.weather_app.service;

import com.example.weather_app.model.Forecast;
import com.example.weather_app.model.Location;

import java.util.Date;
import java.util.Optional;

public interface ForecastProvider {

    Optional<Forecast> getForecast(Location location, Date date);
}
