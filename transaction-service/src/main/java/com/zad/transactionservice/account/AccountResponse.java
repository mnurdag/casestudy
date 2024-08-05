package com.zad.transactionservice.account;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponse {
    String username;
    String currencyCode;
    BigDecimal balance;

    public AccountResponse deposit(BigDecimal amount) {
        balance = balance.add(amount);
        return this;
    }

    public AccountResponse withdraw(BigDecimal amount) {
        balance = balance.subtract(amount);
        return this;
    }

    public boolean hasProvision(BigDecimal amount) {
        return balance.subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
    }

}
