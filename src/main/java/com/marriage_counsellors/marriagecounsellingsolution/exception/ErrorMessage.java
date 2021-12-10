package com.marriage_counsellors.marriagecounsellingsolution.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
public class ErrorMessage extends RuntimeException{
    static final long serialVersionUID = -7034897190745766939L;
    private String message;

    public ErrorMessage(String message) {
        super(message);
    }

    public ErrorMessage(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorMessage(Throwable cause) {
        super(cause);
    }

    public ErrorMessage(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }




}
