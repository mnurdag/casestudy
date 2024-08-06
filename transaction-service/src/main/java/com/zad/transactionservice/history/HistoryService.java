package com.zad.transactionservice.history;

import com.zad.transactionservice.common.KafkaConstants;
import com.zad.transactionservice.common.ObjectMapperUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
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

    @DltHandler
    public void handleDltTransaction(ConsumerRecord<String, String> data, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Event on dlt topic={}, key={}, payload={}", topic, data.key(), data.value());
    }

}
