package com.zad.transactionservice.transaction;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendRequest {
    @NotNull
    @Size(min = 3, max = 25, message = "senderUsername length must be between 3 and 25!")
    String senderUsername;
    @NotNull
    @Size(min = 3, max = 25, message = "senderCurrencyCode must be 3 characters long!")
    String senderCurrencyCode;
    @NotNull
    @Size(min = 3, max = 25, message = "receiverUsername length must be between 3 and 25!")
    String receiverUsername;
    @NotNull
    @Size(min = 3, max = 25, message = "receiverCurrencyCode must be 3 characters long!")
    String receiverCurrencyCode;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "amount must be positive!")
    BigDecimal amount;
    BigDecimal convertedAmount;

}
