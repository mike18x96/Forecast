package com.example.weather_app.service;

import com.example.weather_app.model.Forecast;
import com.example.weather_app.model.Location;

import java.time.LocalDate;
import java.util.Optional;

public interface ForecastProvider {

    Optional<Forecast> getForecast(Location location, LocalDate date);
}
