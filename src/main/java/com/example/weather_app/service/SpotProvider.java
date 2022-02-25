package com.example.weather_app.service;

import com.example.weather_app.model.Location;
import com.example.weather_app.model.SurfingSpot;

import java.util.Date;

public interface SpotProvider {

    SurfingSpot findBestSpot(Date date);
}
