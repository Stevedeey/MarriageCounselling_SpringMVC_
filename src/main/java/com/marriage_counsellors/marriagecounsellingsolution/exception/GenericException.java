package com.marriage_counsellors.marriagecounsellingsolution.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public class GenericException {
    private final String message;
    private final ZonedDateTime timeStamp;

    public GenericException(String message,  ZonedDateTime timeStamp) {
        this.message = message;
        this.timeStamp = timeStamp;
    }
}
