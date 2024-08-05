package com.zad.accountservice.transaction.service;

import com.zad.accountservice.account.Account;
import com.zad.accountservice.account.AccountRepository;
import com.zad.accountservice.common.KafkaSender;
import com.zad.accountservice.common.ObjectMapperUtil;
import com.zad.accountservice.history.HistoryType;
import com.zad.accountservice.transaction.message.SendMessage;
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
public class SendService extends AccountUpdateService {

    WithdrawService withdrawService;
    DepositService depositService;
    AccountRepository accountRepository;
    ObjectMapperUtil objectMapperUtil;
    KafkaSender kafkaSender;

    public void consume(String data) {
        SendMessage request = objectMapperUtil.readValue(data, SendMessage.class);
        Account sender = accountRepository.findByUsernameAndCurrencyCode(
                request.getSenderUsername(), request.getSenderCurrencyCode());
        Account receiver = accountRepository.findByUsernameAndCurrencyCode(
                request.getReceiverUsername(), request.getReceiverCurrencyCode());
        withdrawService.withdraw(sender, request.getAmount());
        receiver.deposit(request.getConvertedAmount());
        sendHistoryMessage(request.toSenderAccountUpdateRequest(), HistoryType.SEND);
        sendHistoryMessage(request.toReceiverAccountUpdateRequest(), HistoryType.RECEIVE);
    }

}
