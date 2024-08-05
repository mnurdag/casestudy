package com.zad.transactionservice.account;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateAccountRequest {
    String username;
    String currencyCode;
    BigDecimal balance = BigDecimal.ZERO;
}
