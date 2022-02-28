package com.example.weather_app.service;

import com.example.weather_app.model.Forecast;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static com.example.weather_app.model.Location.JASTARNIA;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WeatherbitForecastProviderTest {

    @Autowired
    private WeatherbitForecastProvider weatherbitForecastProvider;

    @Test
    void getForecast_goodDateAndLocation_ReturnsForecast() {
        //given
        LocalDate date = LocalDate.of(2022, 2, 28);

        //when
        Optional<Forecast> forecast = weatherbitForecastProvider.getForecast(JASTARNIA, date);

        //then
        assertThat(forecast).isNotEmpty();
        assertThat(forecast.get().getLocation()).isNotNull();
        assertThat(forecast.get().getTemperature()).isNotNull();
        assertThat(forecast.get().getWindSpeed()).isNotNull();
    }


//    @Test
//    void test(){
//
//        SurfingSpotProvider surfingSpotProvider = new SurfingSpotProvider();
////        new SurfingSpotProvider().findBestSpot(2022-03-01);
//
//
//    }
}