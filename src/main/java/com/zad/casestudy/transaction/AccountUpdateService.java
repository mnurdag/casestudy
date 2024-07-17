package com.zad.casestudy.transaction;

import com.zad.casestudy.account.history.HistoryRecord;
import com.zad.casestudy.account.history.OperationType;
import com.zad.casestudy.common.KafkaConstants;
import com.zad.casestudy.common.KafkaSender;

import java.time.LocalDateTime;

public abstract class AccountUpdateService {

    public abstract KafkaSender getKafkaSender();

    void sendHistoryMessage(AccountUpdateRequest request, OperationType operationType) {
        getKafkaSender().sendMessage(KafkaConstants.TOPIC_ACCOUNT_HISTORY,
                HistoryRecord.builder()
                        .userName(request.getUserName())
                        .currencyCode(request.getCurrencyCode())
                        .amount(request.getAmount())
                        .operationType(operationType)
                        .time(LocalDateTime.now())
                        .build());
    }

}
