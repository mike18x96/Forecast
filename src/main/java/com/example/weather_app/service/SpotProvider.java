package com.example.weather_app.service;

import com.example.weather_app.model.SurfingSpot;

import java.time.LocalDate;
import java.util.Optional;

public interface SpotProvider {

    Optional<SurfingSpot> findBestSpot(LocalDate date);
}
