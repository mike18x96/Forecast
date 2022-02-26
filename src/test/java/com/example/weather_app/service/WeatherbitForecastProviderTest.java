package com.example.weather_app.service;

import com.example.weather_app.model.Forecast;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static com.example.weather_app.model.Location.JASTARNIA;
import static org.assertj.core.api.Assertions.assertThat;

class WeatherbitForecastProviderTest {


    @Test
    void getForecast_goodDateAndLocation_ReturnsForecast() throws ParseException {
        //given

        WeatherbitForecastProvider weatherbitForecastProvider = new WeatherbitForecastProvider();

        //when
        Optional<Forecast> forecast = weatherbitForecastProvider.getForecast(JASTARNIA, getDate("2022-02-25"));

        //then
        assertThat(forecast).isNotNull();
    }


    @SneakyThrows
    private Date getDate(String stringDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(stringDate);
    }
}