package com.zad.casestudy.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendRequest {
    @NotNull
    @Size(min = 3, max = 25, message = "senderUserName length must be between 3 and 25!")
    String senderUserName;
    @NotNull
    @Size(min = 3, max = 25, message = "senderCurrencyCode must be 3 characters long!")
    String senderCurrencyCode;
    @NotNull
    @Size(min = 3, max = 25, message = "receiverUserName length must be between 3 and 25!")
    String receiverUserName;
    @NotNull
    @Size(min = 3, max = 25, message = "receiverCurrencyCode must be 3 characters long!")
    String receiverCurrencyCode;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "amount must be positive!")
    BigDecimal amount;
    @JsonIgnore
    BigDecimal convertedAmount;
    @JsonIgnore
    LocalDateTime time;

    public AccountUpdateRequest toSenderAccountUpdateRequest() {
        return AccountUpdateRequest.builder()
                .userName(senderUserName)
                .currencyCode(senderCurrencyCode)
                .amount(amount)
                .build();
    }

    public AccountUpdateRequest toReceiverAccountUpdateRequest() {
        return AccountUpdateRequest.builder()
                .userName(receiverUserName)
                .currencyCode(receiverCurrencyCode)
                .amount(amount)
                .build();
    }

}
