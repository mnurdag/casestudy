package com.zad.accountservice.transaction.service;

import com.zad.accountservice.common.KafkaConstants;
import com.zad.accountservice.exception.ApiException;
import com.zad.accountservice.exception.KafkaException;
import com.zad.accountservice.transaction.AccountUpdateServiceProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@Transactional(rollbackFor = Exception.class)
public class TransactionConsumerService {

    AccountUpdateServiceProvider serviceProvider;

    @RetryableTopic(
            include = {ApiException.class, KafkaException.class},
            attempts = "2",
            kafkaTemplate = "kafkaTemplate",
            dltStrategy = DltStrategy.FAIL_ON_ERROR,
            backoff = @Backoff(delay = 2000, multiplier = 2))
    @KafkaListener(groupId = "gid", topics = KafkaConstants.TOPIC_TRANSACTION)
    public void consume(ConsumerRecord<String, String> data) {
        String messageKey = data.key();
        String messageContent = data.value();
        serviceProvider.getServiceByMessageKey(messageKey).consume(messageContent);
    }

    @DltHandler
    public void handleDltTransaction(ConsumerRecord<String, String> data, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Event on dlt topic={}, key={}, payload={}", topic, data.key(), data.value());
    }

}
