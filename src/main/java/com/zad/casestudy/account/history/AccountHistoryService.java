package com.zad.casestudy.account.history;

import com.zad.casestudy.common.KafkaConstants;
import com.zad.casestudy.common.ObjectMapperUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(rollbackFor = Exception.class)
public class AccountHistoryService {

    AccountHistoryRepository accountHistoryRepository;
    ObjectMapperUtil objectMapperUtil;

    @KafkaListener(groupId = "gid", topics = KafkaConstants.TOPIC_ACCOUNT_HISTORY)
    public void save(@Payload String data) {
        HistoryRecord request = objectMapperUtil.readValue(data, HistoryRecord.class);
        accountHistoryRepository.save(AccountHistory.builder()
                .username(request.getUserName())
                .currencyCode(request.getCurrencyCode())
                .operationType(request.getOperationType())
                .balance(request.getAmount())
                .operationTime(LocalDateTime.now())
                .build());
    }

}
