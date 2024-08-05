package com.zad.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiException extends RuntimeException {
    String message;
    String detailedMessage;

    public String getCompleteMessage() {
        return message + " " + detailedMessage;
    }

}
