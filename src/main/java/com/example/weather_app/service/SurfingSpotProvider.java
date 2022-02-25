package com.example.weather_app.service;

import com.example.weather_app.model.Forecast;
import com.example.weather_app.model.Location;
import com.example.weather_app.model.SurfingSpot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SurfingSpotProvider implements SpotProvider {

    private final ForecastProvider forecastProvider;

    @Override
    public Optional<SurfingSpot> findBestSpot(Date date) {

        List<Forecast> forecastList = new ArrayList<>();

        for (Location location : Location.values()) {
            forecastList.add(forecastProvider.getForecast(location, date));
        }

        Optional<Location> location = getTheBestSurfingLocation(forecastList);
        if (location.isPresent()) {
            Forecast bestForecast = forecastProvider.getForecast(location.get(), date);
            return Optional.of(
                    new SurfingSpot(location.get(), bestForecast.getTemperature(), bestForecast.getWindSpeed()));
        }
        return Optional.empty();
    }


    private Optional<Location> getTheBestSurfingLocation(List<Forecast> forecastList) {
        // implement the logic
        return Optional.empty();
    }
}
/*
    if wind speed OUT OF range <5; 18> m/s \
                                             Location BAD
    if temperature OUT OF range <5; 18>m/s /


    if wind speed IN range <5; 18> m/s \
                                         Location OK -> add to forecastList
    if temperature IN range <5; 18>m/s /



         sprawdzić pogodę dla wszyskich lokalizacji, dodać je do listy i wybrać z niej najlepszą wg wytycznych:

    The best location selection criteria are:
    If the wind speed is not within <5; 18> (m/s) and the temperature is not in the range <5; 35> (°C),
    the location is not suitable for windsurfing. However, if they are in these ranges,

    then the best location is determined by the highest value calculated from the following formula:
    v * 3 + temp
    where v is the wind speed in m/s on a given day, and temp is an average forecasted temperature for a given day in Celsius,
    respectively - you can obtain these parameters from the “data” key in Weatherbit API’s response.
    If none of the locations meets the above criteria, the application does not return any.
        */
