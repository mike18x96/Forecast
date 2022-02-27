package com.example.weather_app.webclient;

import com.example.weather_app.model.Forecast;
import com.example.weather_app.model.Location;
import com.example.weather_app.webclient.dto.WeatherbitDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherClient {

    private static final String WEATHER_URL = "http://api.weatherbit.io/v2.0/forecast/";

    @Value("${API_KEY}")
    private String API_KEY;
    private RestTemplate restTemplate = new RestTemplate();

    public Forecast getForecast(Location location, long day) {
        double lat= location.getLatitude();
        double lon= location.getLongitude();
        WeatherbitDto weatherbitDto = callGetMethod("daily?lat={lat}&lon={lon}&days={day}&key={apiKey}",
                WeatherbitDto.class,
                lat, lon, day, API_KEY);
        return Forecast.builder()
                .temperature(weatherbitDto.getData().get(0).getTemp())
                .windSpeed(weatherbitDto.getData().get(0).getWind_spd())
                .location(location)
                .build();
    }

    private <T> T callGetMethod(String url, Class<T> responseType, Object... objects) {
        return restTemplate.getForObject(WEATHER_URL + url,
                responseType, objects);
    }

}
