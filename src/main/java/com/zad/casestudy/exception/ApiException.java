package com.zad.casestudy.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
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
