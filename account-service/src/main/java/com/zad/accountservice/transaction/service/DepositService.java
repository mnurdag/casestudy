package com.zad.accountservice.transaction.service;

import com.zad.accountservice.account.Account;
import com.zad.accountservice.account.AccountRepository;
import com.zad.accountservice.common.KafkaSender;
import com.zad.accountservice.common.ObjectMapperUtil;
import com.zad.accountservice.history.HistoryType;
import com.zad.accountservice.transaction.message.AccountUpdateMessage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@Transactional(rollbackFor = Exception.class)
public class DepositService extends AccountUpdateService {

    AccountRepository accountRepository;
    ObjectMapperUtil objectMapperUtil;
    KafkaSender kafkaSender;

    @Override
    public void consume(String data) {
        AccountUpdateMessage request = objectMapperUtil.readValue(data, AccountUpdateMessage.class);
        Account account = accountRepository.findByUsernameAndCurrencyCode(request.getUsername(), request.getCurrencyCode());
        account.deposit(request.getAmount());
        sendHistoryMessage(request, HistoryType.DEPOSIT);
    }

}

