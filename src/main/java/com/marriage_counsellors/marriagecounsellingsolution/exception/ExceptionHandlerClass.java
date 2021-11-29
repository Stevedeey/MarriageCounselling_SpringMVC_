package com.marriage_counsellors.marriagecounsellingsolution.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ExceptionHandlerClass {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public GenericException handleApiResourceNotFoundException(ResourceNotFoundException e){
        GenericException apiException = new GenericException(
                e.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return apiException;
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ErrorMessage handleApiResourceNotFoundExceptionn(ResourceNotFoundException e){

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(e.getMessage());
        return errorMessage;
    }
}
