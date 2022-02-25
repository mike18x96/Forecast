package com.example.weather_app.controller;

import com.example.weather_app.model.SurfingSpot;
import com.example.weather_app.service.SpotProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class Contoller {

    private final SpotProvider spotProvider;

    @GetMapping("forecast")
    public SurfingSpot getSurfingSpot(@RequestParam(value = "date") Date date) {
        return spotProvider.findBestSpot(date).orElseThrow(() -> new IllegalArgumentException("There is no spot"));
    }

}
