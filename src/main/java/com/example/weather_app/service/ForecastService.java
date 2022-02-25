package com.example.weather_app.service;

import com.example.weather_app.model.SurfingSpot;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ForecastService {


    public SurfingSpot findSpot(Date date) {
        return new SurfingSpot(null, null, null);
    }
}
