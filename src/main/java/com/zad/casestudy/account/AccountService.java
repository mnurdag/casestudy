package com.zad.casestudy.account;

import com.zad.casestudy.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(rollbackFor = Exception.class)
public class AccountService {

    UserRepository userRepository;
    AccountRepository accountRepository;

    public AccountResponse save(CreateAccountRequest request) {
        userRepository.assertExistsByUsername(request.getUserName());
        Account account = accountRepository.save(Account.builder()
                .username(request.getUserName())
                .currencyCode(request.getCurrencyCode())
                .balance(request.getInitialBalance())
                .build());
        return AccountResponse.builder()
                .username(account.getUsername())
                .currencyCode(account.getCurrencyCode())
                .balance(account.getBalance())
                .build();
    }

    public AccountResponse get(String username, String currencyCode) {
        return accountRepository.findByUsernameAndCurrencyCode(username, currencyCode).toResponse();
    }

}
