package com.example.weather_app.utils;

import com.example.weather_app.exception.DateFormatException;
import lombok.SneakyThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtils {

    public static LocalDate getValidDate(String stringDate) {
        try {
            return LocalDate.parse(stringDate);
        } catch (DateTimeParseException ex) {
            throw new DateFormatException(ex.getMessage());
        }
    }

    public static void validateDate(LocalDate date) {

    }

    public static long dateDifferenceFromNowInDays(LocalDate date) {
            LocalDate now = LocalDate.now();
            return ChronoUnit.DAYS.between(now, date);
    }

}
