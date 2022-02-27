package com.example.weather_app.service;

import com.example.weather_app.dto.WeatherbitDto;
import com.example.weather_app.model.Forecast;
import com.example.weather_app.model.Location;
import com.example.weather_app.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Setter
public class WeatherbitForecastProvider implements ForecastProvider {

    @Value("${weatherBitUrl}")
    private String weatherBitUrl;

    @Value("${apiKey}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();


    @Override
    public Optional<Forecast> getForecast(Location location, LocalDate date) {

        long requiredDay = DateUtils.dateDifferenceFromNowInDays(date);
        return Optional.ofNullable(getForecast(location, requiredDay));

    }

    private Forecast getForecast(Location location, long day) {
        String url = getUrl();
        double lat = location.getLatitude();
        double lon = location.getLongitude();

        WeatherbitDto weatherbitDto = restTemplate.getForObject(url, WeatherbitDto.class, lat, lon, day, apiKey);

        return Forecast.builder()
                .temperature(weatherbitDto.getData().get(0).getTemp())
                .windSpeed(weatherbitDto.getData().get(0).getWind_spd())
                .location(location)
                .build();
    }

    private String getUrl() {
        String queryParams = "daily?lat={lat}&lon={lon}&days={day}&key={apiKey}";
        return weatherBitUrl + queryParams;
    }

}
