package com.example.weather_app.webclient.dto;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class WeatherbitDto {

    private ArrayList<WeatherbitDataDto> data;
    private String city_name;
}
