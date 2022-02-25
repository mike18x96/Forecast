package com.example.weather_app.service;

import com.example.weather_app.model.Forecast;
import com.example.weather_app.model.Location;
import com.example.weather_app.model.SurfingSpot;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

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
    }

}