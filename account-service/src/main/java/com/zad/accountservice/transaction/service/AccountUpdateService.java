package com.zad.accountservice.transaction.service;

import com.zad.accountservice.common.KafkaConstants;
import com.zad.accountservice.common.KafkaSender;
import com.zad.accountservice.common.MessageKey;
import com.zad.accountservice.history.HistoryMessage;
import com.zad.accountservice.history.HistoryType;
import com.zad.accountservice.transaction.message.AccountUpdateMessage;

import java.time.LocalDateTime;

public abstract class AccountUpdateService {

    public abstract KafkaSender getKafkaSender();

    public abstract void consume(String data);

    void sendHistoryMessage(AccountUpdateMessage request, HistoryType historyType) {
        getKafkaSender().sendMessage(KafkaConstants.TOPIC_HISTORY, MessageKey.HISTORY,
                HistoryMessage.builder()
                        .username(request.getUsername())
                        .currencyCode(request.getCurrencyCode())
                        .amount(request.getAmount())
                        .historyType(historyType)
                        .time(LocalDateTime.now())
                        .build());
    }

}
