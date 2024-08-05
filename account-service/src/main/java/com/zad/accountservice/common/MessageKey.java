package com.zad.accountservice.common;

import com.zad.accountservice.transaction.service.AccountUpdateService;
import com.zad.accountservice.transaction.service.DepositService;
import com.zad.accountservice.transaction.service.SendService;
import com.zad.accountservice.transaction.service.WithdrawService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum MessageKey {

    WITHDRAW(WithdrawService.class),
    DEPOSIT(DepositService.class),
    SEND(SendService.class),
    HISTORY(null);

    Class<? extends AccountUpdateService> serviceClass;

}
