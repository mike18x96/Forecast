package com.example.weather_app.service;

import com.example.weather_app.model.Forecast;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Optional;

import static com.example.weather_app.model.Location.JASTARNIA;
import static org.assertj.core.api.Assertions.assertThat;

class WeatherbitForecastProviderTest {


    @Test
    void getForecast_goodDateAndLocation_ReturnsForecast() throws ParseException {
        //given
        LocalDate date = LocalDate.of(2022, 3, 1);
        WeatherbitForecastProvider weatherbitForecastProvider = new WeatherbitForecastProvider();
        weatherbitForecastProvider.setWEATHERBIT_URL("http://api.weatherbit.io/v2.0/forecast/");
        weatherbitForecastProvider.setAPI_KEY("56c3df3771794dfab56b72bdd23e7772");

        //when
        Optional<Forecast> forecast = weatherbitForecastProvider.getForecast(JASTARNIA, date);

        //then
        assertThat(forecast).isNotEmpty();
        assertThat(forecast.get().getLocation()).isNotNull();
        assertThat(forecast.get().getTemperature()).isNotNull();
        assertThat(forecast.get().getWindSpeed()).isNotNull();
    }

}