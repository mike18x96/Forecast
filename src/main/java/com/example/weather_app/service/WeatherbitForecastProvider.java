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

    @Value("${WEATHERBIT_URL:http://api.weatherbit.io/v2.0/forecast/}")
    private String WEATHERBIT_URL;

    @Value("${API_KEY:56c3df3771794dfab56b72bdd23e7772}")
    private String API_KEY;

    private final RestTemplate restTemplate = new RestTemplate();


    @Override
    public Optional<Forecast> getForecast(Location location, LocalDate date) {

        long requiredDay = DateUtils.dateDifferenceFromNowInDays(date);
        return Optional.ofNullable(getForecast(location, requiredDay));

    }

    public Forecast getForecast(Location location, long day) {
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        String url = getUrl();

        WeatherbitDto weatherbitDto = restTemplate.getForObject(url, WeatherbitDto.class, lat, lon, day, API_KEY);

        return Forecast.builder()
                .temperature(weatherbitDto.getData().get(0).getTemp())
                .windSpeed(weatherbitDto.getData().get(0).getWind_spd())
                .location(location)
                .build();
    }

    private String getUrl() {
        String queryParams = "daily?lat={lat}&lon={lon}&days={day}&key={apiKey}";
        return WEATHERBIT_URL + queryParams;
    }

}
