package com.zad.transactionservice.common;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseResponse {

    boolean success;
    String message;
    String errorMessage;
    Object data;

    public static BaseResponse ok() {
        return BaseResponse.builder()
                .success(true)
                .build();
    }

    public static BaseResponse ok(Object data) {
        return BaseResponse.builder()
                .success(true)
                .data(data)
                .build();
    }

    public static BaseResponse error(String message) {
        return BaseResponse.builder()
                .success(false)
                .errorMessage(message)
                .build();
    }

}
