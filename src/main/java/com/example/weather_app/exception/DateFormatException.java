package com.example.weather_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class DateFormatException extends RuntimeException{
    public DateFormatException(String msg) {
        super(msg);
    }
}
