package com.example.weather_app.service;

import com.example.weather_app.model.Forecast;
import com.example.weather_app.model.Location;
import com.example.weather_app.model.SurfingSpot;

import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;


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


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.example.weather_app.model.Location.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SurfingSpotProviderTest {

    @Mock
    private ForecastProvider forecastProvider;

    @InjectMocks
    private SurfingSpotProvider surfingSpotProvider;



    @ParameterizedTest
    @MethodSource("provideLocationsWithNoGoodWeatherConditions")
    void findBestSpot_noneWeatherConditionInRightRange_returnsEmptyOptional(List<Forecast> forecastList) {
        //given
        forecastList.forEach(forecast -> when(forecastProvider.getForecast(eq(forecast.getLocation()), any(Date.class)))
                .thenReturn(forecast));
        //when
        Optional<SurfingSpot> bestSpot = surfingSpotProvider.findBestSpot(new Date());
        //then
        assertTrue(bestSpot.isEmpty());
    }

    public static Stream<Arguments> provideLocationsWithNoGoodWeatherConditions() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                createForecast(BRIDGETOWN, 4, 15),
                                createForecast(JASTARNIA, 3, 23),
                                createForecast(BRIDGETOWN, 2, 35),
                                createForecast(FORTALEZA, -23, 12),
                                createForecast(PISSOURI, -16, 17),
                                createForecast(LE_MORNE, 0, 5))
                ),
                Arguments.of(
                        List.of(
                                createForecast(BRIDGETOWN, 5, -5),
                                createForecast(JASTARNIA, 7, -23),
                                createForecast(BRIDGETOWN, 18, 39),
                                createForecast(FORTALEZA, 13, 45),
                                createForecast(PISSOURI, 10, 4),
                                createForecast(LE_MORNE, 6, 36))
                ),
                Arguments.of(
                        List.of(
                                createForecast(BRIDGETOWN, 4, -5),
                                createForecast(JASTARNIA, -7, -23),
                                createForecast(BRIDGETOWN, 20, 39),
                                createForecast(FORTALEZA, 50, 45),
                                createForecast(PISSOURI, 19, 4),
                                createForecast(LE_MORNE, 0, 36))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideLocationsWithGoodWeatherConditionsAndBestSurfingSpots")
    void findBestSpot_weatherConditionInRightRange_returnsOptionalOfBestSurfingSpot(List<Forecast> forecastList,
                                                                                    SurfingSpot expectedSurfingSpot) {
        //given
        forecastList.forEach(forecast -> when(forecastProvider.getForecast(eq(forecast.getLocation()), any(Date.class)))
                .thenReturn(forecast));
        //when
        Optional<SurfingSpot> bestSpot = surfingSpotProvider.findBestSpot(new Date());
        //then
        assertEquals(bestSpot.get(), expectedSurfingSpot);
    }

    public static Stream<Arguments> provideLocationsWithGoodWeatherConditionsAndBestSurfingSpots() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                createForecast(BRIDGETOWN, 5, 15),
                                createForecast(JASTARNIA, 7, 23),
                                createForecast(BRIDGETOWN, 12, 35),
                                createForecast(FORTALEZA, 17, 12),
                                createForecast(PISSOURI, 13, 17),
                                createForecast(LE_MORNE, 18, 5)),
                        new SurfingSpot(BRIDGETOWN, 18, 15)
                ),
                Arguments.of(
                        List.of(
                                createForecast(BRIDGETOWN, 5, 5),
                                createForecast(JASTARNIA, 7, 23),
                                createForecast(BRIDGETOWN, 18, 6),
                                createForecast(FORTALEZA, 13, 3),
                                createForecast(PISSOURI, 10, 20),
                                createForecast(LE_MORNE, 18, 15)),
                        new SurfingSpot(LE_MORNE, 18, 15)
                )
        );
    }

    private static Forecast createForecast(Location location, double windSpeed, double temperature) {
        Forecast forecast = new Forecast();
        forecast.setLocation(location);
        forecast.setWindSpeed(windSpeed);
        forecast.setTemperature(temperature);
        return forecast;

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