package com.example.weather_app.utils;

import com.example.weather_app.exception.DateFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class DateUtils {

    public static LocalDate parseToLocalDate(String stringDate) {
        try {
            return LocalDate.parse(stringDate);
        } catch (DateTimeParseException ex) {
            throw new DateFormatException(ex.getMessage());
        }
    }

    public static long dateDifferenceFromNowInDays(LocalDate date) {
            LocalDate now = LocalDate.now();
            return ChronoUnit.DAYS.between(now, date);
    }

}
