package com.zad.transactionservice.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum MessageKey {

    WITHDRAW,
    DEPOSIT,
    SEND,
    HISTORY;

}
