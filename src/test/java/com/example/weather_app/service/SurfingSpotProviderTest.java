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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.example.weather_app.model.Location.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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
        LocalDate date = LocalDate.now();
        forecastList.forEach(forecast -> when(forecastProvider.getForecast(eq(forecast.getLocation()), eq(date)))
                .thenReturn(Optional.of(forecast)));

        //when
        Optional<SurfingSpot> bestSpot = surfingSpotProvider.findBestSpot(date);

        //then
        assertTrue(bestSpot.isEmpty());
    }

    private static Stream<Arguments> provideLocationsWithNoGoodWeatherConditions() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new Forecast(JASTARNIA, 3, 23),
                                new Forecast(BRIDGETOWN, 2, 35),
                                new Forecast(FORTALEZA, -23, 12),
                                new Forecast(PISSOURI, -16, 17),
                                new Forecast(LE_MORNE, 0, 5))
                ),
                Arguments.of(
                        List.of(
                                new Forecast(JASTARNIA, 7, -3),
                                new Forecast(BRIDGETOWN, 18, 39),
                                new Forecast(FORTALEZA, 13, 45),
                                new Forecast(PISSOURI, 10, 4),
                                new Forecast(LE_MORNE, 6, 36))
                ),
                Arguments.of(
                        List.of(
                                new Forecast(JASTARNIA, -7, -23),
                                new Forecast(BRIDGETOWN, 20, 39),
                                new Forecast(FORTALEZA, 50, 45),
                                new Forecast(PISSOURI, 19, 4),
                                new Forecast(LE_MORNE, 0, 36))
                ),
                Arguments.of(
                        List.of(
                                new Forecast(JASTARNIA, 18.1, 15),
                                new Forecast(BRIDGETOWN, 4.9, 20),
                                new Forecast(FORTALEZA, 14, 35.1),
                                new Forecast(PISSOURI, 13, 4.9),
                                new Forecast(LE_MORNE, 0, 0))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("forecastWithConditionInRightRangeAndExpectedSpot")
    void findBestSpot_weatherConditionInRightRange_returnsBestSurfingSpot(List<Forecast> forecastList, SurfingSpot expectedSurfingSpot) {
        //given
        LocalDate date = LocalDate.now();
        forecastList.forEach(forecast -> when(forecastProvider.getForecast(eq(forecast.getLocation()), eq(date)))
                .thenReturn(Optional.of(forecast)));

        //when
        Optional<SurfingSpot> bestSpot = surfingSpotProvider.findBestSpot(date);

        //then
        assertThat(bestSpot).isNotEmpty();
        assertThat(bestSpot.get()).isEqualTo(expectedSurfingSpot);
    }

    public static Stream<Arguments> forecastWithConditionInRightRangeAndExpectedSpot() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new Forecast(JASTARNIA, 7, 23),
                                new Forecast(BRIDGETOWN, 18, 15),
                                new Forecast(FORTALEZA, 17, 12),
                                new Forecast(PISSOURI, 13, 17),
                                new Forecast(LE_MORNE, 18, 5)),
                        new SurfingSpot(BRIDGETOWN, 15, 18)
                ),
                Arguments.of(
                        List.of(
                                new Forecast(JASTARNIA, 7, 23),
                                new Forecast(BRIDGETOWN, 18, 6),
                                new Forecast(FORTALEZA, 13, 3),
                                new Forecast(PISSOURI, 10, 20),
                                new Forecast(LE_MORNE, 18, 15)),
                        new SurfingSpot(LE_MORNE, 15, 18)
                ),
                Arguments.of(
                        List.of(
                                new Forecast(JASTARNIA, 18, 35),
                                new Forecast(BRIDGETOWN, 18, 6),
                                new Forecast(FORTALEZA, 13, 3),
                                new Forecast(PISSOURI, 10, 20),
                                new Forecast(LE_MORNE, 18, 15)),
                        new SurfingSpot(JASTARNIA, 35, 18)
                ),
                Arguments.of(
                        List.of(
                                new Forecast(JASTARNIA, 4.9, 4.9),
                                new Forecast(BRIDGETOWN, 4.9, 30),
                                new Forecast(FORTALEZA, 5, 5),
                                new Forecast(PISSOURI, 4.9, 20),
                                new Forecast(LE_MORNE, 17, 4.9)),
                        new SurfingSpot(FORTALEZA, 5, 5)
                )
        );
    }
}