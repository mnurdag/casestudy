package com.zad.transactionservice.history;

import com.zad.transactionservice.common.KafkaConstants;
import com.zad.transactionservice.common.ObjectMapperUtil;
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
public class HistoryService {

    HistoryRepository historyRepository;
    ObjectMapperUtil objectMapperUtil;

    @KafkaListener(groupId = "gid", topics = KafkaConstants.TOPIC_HISTORY)
    public void save(@Payload String data) {
        HistoryMessage request = objectMapperUtil.readValue(data, HistoryMessage.class);
        historyRepository.save(History.builder()
                .username(request.getUsername())
                .currencyCode(request.getCurrencyCode())
                .historyType(request.getHistoryType())
                .balance(request.getAmount())
                .operationTime(LocalDateTime.now())
                .build());
    }

}
