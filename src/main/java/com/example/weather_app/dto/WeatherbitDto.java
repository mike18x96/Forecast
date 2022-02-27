package com.example.weather_app.dto;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class WeatherbitDto {

    private ArrayList<WeatherDataDto> data;
    private String city_name;
}
