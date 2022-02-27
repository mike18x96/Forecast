package com.example.weather_app.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {

    private static final String URL = "/forecast";


    @Autowired
    private MockMvc mockMvc;

    @Test
    void getSurfingSpot_goodDate_returnsBestSpotOrNothing() throws Exception {
        //given
        String DATE = "?date=2022-03-05";
        //when
        mockMvc.perform(get(URL + DATE))
                .andDo(print())
                //then
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("badDate")
    void getSurfingSpot_badDate_throwsExceptionAndReturns422(String date) throws Exception {
        //given

        //when
        mockMvc.perform(get(URL + date))
                .andDo(print())
                //then
                .andExpect(status().isUnprocessableEntity());
    }

    public static Stream<Arguments> badDate() {
        return Stream.of(
                Arguments.of("?date=2022-02-27"),
                Arguments.of("?date=2022-13-27"),
                Arguments.of("?date=2022-02-33"));
    }

}