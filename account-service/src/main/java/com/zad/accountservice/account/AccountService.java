package com.zad.accountservice.account;

import com.zad.accountservice.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(rollbackFor = Exception.class)
public class AccountService {

    UserService userService;
    AccountRepository accountRepository;

    public AccountResponse save(AccountRequest request) {
        userService.assertExistsByUsername(request.getUsername());
        Account account = Account.builder()
                .username(request.getUsername())
                .currencyCode(request.getCurrencyCode())
                .balance(request.getBalance())
                .build();
        accountRepository.save(account);
        return AccountResponse.builder()
                .username(account.getUsername())
                .currencyCode(account.getCurrencyCode())
                .balance(account.getBalance())
                .build();
    }

    public AccountResponse update(AccountRequest request) {
        userService.assertExistsByUsername(request.getUsername());
        Account account = accountRepository.findByUsernameAndCurrencyCode(request.getUsername(), request.getCurrencyCode());
        account.setBalance(request.getBalance());
        return account.toResponse();
    }

    public AccountResponse get(GetAccountRequest request) {
        return accountRepository.findByUsernameAndCurrencyCode(request.getUsername(), request.getCurrencyCode()).toResponse();
    }

    public List<AccountResponse> getAccountList(List<GetAccountRequest> requestList) {
        List<Account> accountList = accountRepository.getAccountList(requestList);
        return accountList.stream().map(Account::toResponse).collect(Collectors.toList());
    }

}
