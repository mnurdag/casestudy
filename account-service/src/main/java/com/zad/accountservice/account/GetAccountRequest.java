package com.zad.accountservice.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetAccountRequest {
    @NotNull
    @Size(min = 3, max = 25, message = "username length must be between 3 and 25!")
    @Schema(name = "username", description = "username", example = "jacksparrow")
    @Pattern(regexp="^[A-Za-z]*$",message = "username must contain letters only.")
    String username;
    @NotNull
    @Size(min = 3, max = 3, message = "currencyCode length must be between 3!")
    @Schema(name = "currencyCode", description = "currencyCode", example = "USD")
    String currencyCode;
}
