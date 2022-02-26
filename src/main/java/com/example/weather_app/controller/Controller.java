package com.example.weather_app.controller;

import com.example.weather_app.exception.ResourceNotFoundException;
import com.example.weather_app.model.SurfingSpot;
import com.example.weather_app.service.SpotProvider;
import com.example.weather_app.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final SpotProvider spotProvider;

    @GetMapping("forecast")
    public SurfingSpot getSurfingSpot(@RequestParam(value = "date") String date) {

        return spotProvider.findBestSpot(DateUtils.getDate(date))
                .orElseThrow(() -> new ResourceNotFoundException("No suitable spots at given date"));
    }

}
