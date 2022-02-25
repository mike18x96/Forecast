package com.example.weather_app.service;

import com.example.weather_app.model.Forecast;
import com.example.weather_app.model.Location;
import com.example.weather_app.model.SurfingSpot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;
import java.util.stream.Stream;

import static com.example.weather_app.model.Location.JASTARNIA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.mockito.Mockito.*;

class SurfingSpotProviderTest {

    @Mock
    private ForecastProvider forecastProvider;

    @InjectMocks
    private SurfingSpotProvider surfingSpotProvider;

    @ParameterizedTest
    @MethodSource("goodConditionsData")
    void findBestSpot_goodDateAndConditions_ReturnsBestSurfingSpot(String temperatureJastarnia, String windSpeedJastarnia, String temperatureForRest, String windSpeedForRest) throws ParseException {
        //given
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(new Date());
        Date date = format.parse("2022-02-25");
        Forecast forecastJastarnia = new Forecast(temperatureJastarnia, windSpeedJastarnia);
        Forecast forecastForRest = new Forecast(temperatureForRest, windSpeedForRest);
        SurfingSpot bestSurfingSpot = new SurfingSpot(JASTARNIA, temperatureJastarnia, windSpeedJastarnia);
        when(forecastProvider.getForecast(JASTARNIA, date)).thenReturn(forecastJastarnia);
        when(forecastProvider.getForecast(any(Location.class), date)).thenReturn(forecastForRest);
        //when
        SurfingSpot bestSpot = surfingSpotProvider.findBestSpot(date);
        //then
        assertThat(bestSpot).isEqualTo(bestSurfingSpot);
        verify(forecastProvider, times(5)).getForecast(any(Location.class), any(Date.class));
        verifyNoMoreInteractions(forecastProvider);
    }

    private static Stream<Arguments> goodConditionsData() {
        return Stream.of(
                of("35", "18.0", 5, "5", "5.0"),
                of("5", "5.0", 5, "4", "4.9"));
    }

    @ParameterizedTest
    @MethodSource("badConditionsData")
    void findBestSpot_goodDateBadConditionsForSurfing_DoesNotReturnAny(String temperatureJastarnia, String windSpeedJastarnia, String temperatureForRest, String windSpeedForRest) throws ParseException {
        //given
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(new Date());
        Date date = format.parse("2022-02-25");
        Forecast forecastJastarnia = new Forecast(temperatureJastarnia, windSpeedJastarnia);
        Forecast forecastForRest = new Forecast(temperatureForRest, windSpeedForRest);
        SurfingSpot bestSurfingSpot = new SurfingSpot(null, null, null);
        when(forecastProvider.getForecast(JASTARNIA, date)).thenReturn(forecastJastarnia);
        when(forecastProvider.getForecast(any(Location.class), date)).thenReturn(forecastForRest);
        //when
        SurfingSpot bestSpot = surfingSpotProvider.findBestSpot(date);
        //then
        assertThat(bestSpot).isEqualTo(bestSurfingSpot);
        verify(forecastProvider, times(5)).getForecast(any(Location.class), any(Date.class));
        verifyNoMoreInteractions(forecastProvider);
    }

    private static Stream<Arguments> badConditionsData() {
        return Stream.of(
                of("36", "8.4", 5, "4", "5.9"),
                of("25", "18.1", 5, "16", "4.9"));
    }

    @Test
    void findBestSpot_badDate_DoesNotReturnAny() throws ParseException, DateTimeException {
        //given
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(new Date());
        Date date = format.parse("2025-13-35");
        assertThatThrownBy(() -> surfingSpotProvider.findBestSpot(date))
                //then
                .isInstanceOf(DateTimeException.class)
                .hasMessageContaining("Incorrect date!");
        verify(forecastProvider, never()).getForecast(any(Location.class), any(Date.class));
    }
}