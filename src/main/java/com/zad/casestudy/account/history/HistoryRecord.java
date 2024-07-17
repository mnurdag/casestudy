package com.zad.casestudy.account.history;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryRecord {
    String userName;
    String currencyCode;
    BigDecimal amount;
    OperationType operationType;
    LocalDateTime time;
}
