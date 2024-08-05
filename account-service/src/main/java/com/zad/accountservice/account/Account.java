package com.zad.accountservice.account;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NaturalId;

import java.math.BigDecimal;

@Data
@Table(name = "account", schema = "casestudy")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Version
    @Setter(AccessLevel.NONE)
    long version;
    @NaturalId
    String username;
    @NaturalId
    String currencyCode;
    @PositiveOrZero
    BigDecimal balance;

    public Account deposit(BigDecimal amount) {
        balance = balance.add(amount);
        return this;
    }

    public Account withdraw(BigDecimal amount) {
        balance = balance.subtract(amount);
        return this;
    }

    public boolean hasProvision(BigDecimal amount) {
        return balance.subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
    }

    public AccountResponse toResponse() {
        return AccountResponse.builder()
                .username(username)
                .currencyCode(currencyCode)
                .balance(balance)
                .build();
    }

}
