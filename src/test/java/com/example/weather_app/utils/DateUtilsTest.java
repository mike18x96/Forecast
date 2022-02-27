package com.example.weather_app.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DateUtilsTest {

    @Test
    void dateDifferenceInDays_shouldReturnNegativeNumberForDateInPast() {
        LocalDate dateInPast = LocalDate.of(2022, 2, 6);
        long days = DateUtils.dateDifferenceFromNowInDays(dateInPast);
        assertThat(days).isLessThan(0);
    }

    @Test
    void dateDifferenceInDays_shouldReturnPositiveNumberForDateInFuture() {
        LocalDate dateInPast = LocalDate.of(2123, 2, 16);
        long days = DateUtils.dateDifferenceFromNowInDays(dateInPast);
        assertThat(days).isGreaterThan(1);
    }

}