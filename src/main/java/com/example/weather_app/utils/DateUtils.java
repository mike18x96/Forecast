package com.example.weather_app.utils;

import com.example.weather_app.exception.DateFormatException;
import lombok.SneakyThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @SneakyThrows
    public static Date getDate(String stringDate) {
        try {
            return format.parse(stringDate);
        } catch (ParseException ex) {
            throw new DateFormatException(ex.getMessage());
        }

    }

}
