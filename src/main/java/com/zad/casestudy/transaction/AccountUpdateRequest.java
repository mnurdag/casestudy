package com.zad.casestudy.transaction;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountUpdateRequest {
    @NotNull
    @Size(min = 3, max = 25, message = "userName length must be between 3 and 25!")
    String userName;
    @NotNull
    @Size(min = 3, max = 3, message = "currencyCode must be 3 characters long!")
    String currencyCode;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "amount must be positive!")
    BigDecimal amount;
}
