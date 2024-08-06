package com.zad.accountservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KafkaException extends RuntimeException {
    String message;
    String detailedMessage;

    public String getCompleteMessage() {
        return "Kafka >>> " + message + " " + detailedMessage;
    }

}
