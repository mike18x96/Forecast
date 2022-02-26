package com.example.weather_app.service;

import com.example.weather_app.model.Forecast;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.weather_app.model.Location.JASTARNIA;
import static org.assertj.core.api.Assertions.assertThat;

class WeatherbitForecastProviderTest {

    @Test
    void getForecast_goodDateAndLocation_ReturnsForecast() throws ParseException {
        //given
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(new Date());
        Date date = format.parse("2022-02-25");
        WeatherbitForecastProvider weatherbitForecastProvider = new WeatherbitForecastProvider();

        //when
        Forecast forecast = weatherbitForecastProvider.getForecast(JASTARNIA, date);
        //then
        assertThat(forecast).isNotNull();
    }
}