package com.zad.casestudy.account;

import com.zad.casestudy.exception.ApiException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> getByUsernameAndCurrencyCode(String username, String currencyCode);

    default Account findByUsernameAndCurrencyCode(String username, String currencyCode) {
        return getByUsernameAndCurrencyCode(username, currencyCode).orElseThrow(() ->
                new ApiException("Account doesn't exist! ",
                        String.format("username: %s, currencyCode: %s", username, currencyCode)));
    }

}
