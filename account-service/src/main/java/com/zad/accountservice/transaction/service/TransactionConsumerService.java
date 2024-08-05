package com.zad.accountservice.transaction.service;

import com.zad.accountservice.common.KafkaConstants;
import com.zad.accountservice.transaction.AccountUpdateServiceProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@Transactional(rollbackFor = Exception.class)
public class TransactionConsumerService {

    AccountUpdateServiceProvider serviceProvider;

    @KafkaListener(groupId = "gid", topics = KafkaConstants.TOPIC_TRANSACTION)
    public void consume(ConsumerRecord<String, String> data) {
        String messageKey = data.key();
        String messageContent = data.value();
        serviceProvider.getServiceByMessageKey(messageKey).consume(messageContent);
    }

}
