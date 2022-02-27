package com.example.weather_app.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
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

    @Autowired
    private MockMvc mockMvc;

    private final String url = "/forecast";

    @Test
    void getSurfingSpot_goodDate_returnsBestSpotOrNothing() throws Exception {
        //given
        String date = "2022-03-05";
        //when
        mockMvc.perform(get(url)
                        .param("date", date))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"02-27-2022", "2022-13-27", "2020-01-12", "2022.12.27"})
    void getSurfingSpot_badDate_throwsExceptionAndReturns422(String date) throws Exception {

        mockMvc.perform(get(url)
                        .param("date", date))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }


}