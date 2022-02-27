package com.example.weather_app.service;

import com.example.weather_app.model.Forecast;
import com.example.weather_app.model.Location;
import com.example.weather_app.webclient.WeatherClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class WeatherbitForecastProvider implements ForecastProvider{

    private final WeatherClient weatherClient;

    @Override
    public Optional<Forecast> getForecast(Location location, LocalDate date) {

        LocalDate currentDate = LocalDate.now();
        long requiredDay = DAYS.between(date, currentDate) + 1;
        return Optional.ofNullable(weatherClient.getForecast(location, requiredDay));

    }
}
