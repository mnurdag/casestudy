package com.zad.casestudy.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(name = "CreateAccountRequest", description = "Account creation modal")
public class CreateAccountRequest {
    @NotNull
    @Size(min = 3, max = 25, message = "userName length must be between 3 and 25!")
    @Schema(name = "username", description = "username", example = "jacksparrow")
    String userName;
    @NotNull
    @Size(min = 3, max = 25, message = "userName length must be between 3 and 25!")
    @Schema(name = "currencyCode", description = "currencyCode", example = "USD")
    String currencyCode;
    @Schema(name = "initialBalance", description = "initialBalance", example = "150.25")
    BigDecimal initialBalance = BigDecimal.ZERO;
}
