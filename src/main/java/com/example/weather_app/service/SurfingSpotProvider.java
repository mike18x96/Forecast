package com.example.weather_app.service;

import com.example.weather_app.model.Forecast;
import com.example.weather_app.model.Location;
import com.example.weather_app.model.SurfingSpot;
import com.example.weather_app.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SurfingSpotProvider implements SpotProvider {

    private final ForecastProvider forecastProvider;

    @Override
    public Optional<SurfingSpot> findBestSpot(LocalDate date) {

        validate(date);

        return getFilteredForecastsBy(date)
                .map(bestForecast -> new SurfingSpot(bestForecast.getLocation(),
                        bestForecast.getTemperature(), bestForecast.getWindSpeed()));
    }

    private void validate(LocalDate date) {
        long period = DateUtils.dateDifferenceFromNowInDays(date);
        if (period < 0) {

        } else if (period > 16) {

        }

    }

    private Optional<Forecast> getFilteredForecastsBy(LocalDate date) {
        return Stream.of(Location.values())
                .map(location -> forecastProvider.getForecast(location, date))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(WeatherConditionAnalyzer::isForecastGoodForSurfing)
                .max(new WeatherConditionComparator());
    }

    private static class WeatherConditionAnalyzer {
        private static final double MIN_WIND_SPEED = 5;
        private static final double MAX_WIND_SPEED = 18;
        private static final double MIN_TEMPERATURE = 5;
        private static final double MAX_TEMPERATURE = 35;

        //range temperature and wind - inclusive / exclusive?
        private static boolean isForecastGoodForSurfing(Forecast forecast) {
            boolean tempOk = forecast.getWindSpeed() >= MIN_WIND_SPEED && forecast.getWindSpeed() <= MAX_WIND_SPEED;
            boolean windSpeedOk = forecast.getTemperature() >= MIN_TEMPERATURE && forecast.getTemperature() <= MAX_TEMPERATURE;
            return tempOk && windSpeedOk;
        }
    }


    private static class WeatherConditionComparator implements Comparator<Forecast> {
        @Override
        public int compare(Forecast forecastA, Forecast forecastB) {
            return Double.compare(calculateWeatherConditionFormula(forecastA), calculateWeatherConditionFormula(forecastB));
        }

        private double calculateWeatherConditionFormula(Forecast forecast) {
            return (forecast.getWindSpeed() * 3) + forecast.getTemperature();
        }
    }

}
/*
    if wind speed IN range <5; 18> m/s \
                                         Location OK -> add to forecastList
    if temperature IN range <5; 35>m/s /


    The best location selection criteria are:
    If the wind speed is not within <5; 18> (m/s) and the temperature is not in the range <5; 35> (°C),
    the location is not suitable for windsurfing. However, if they are in these ranges,

    then the best location is determined by the highest value calculated from the following formula:
    v * 3 + temp
    where v is the wind speed in m/s on a given day, and temp is an average forecasted temperature for a given day in Celsius,
    respectively - you can obtain these parameters from the “data” key in Weatherbit API’s response.
    If none of the locations meets the above criteria, the application does not return any.
        */
