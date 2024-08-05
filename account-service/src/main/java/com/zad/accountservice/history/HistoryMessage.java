package com.zad.accountservice.history;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryMessage {
    String username;
    String currencyCode;
    BigDecimal amount;
    HistoryType historyType;
    LocalDateTime time;
}
