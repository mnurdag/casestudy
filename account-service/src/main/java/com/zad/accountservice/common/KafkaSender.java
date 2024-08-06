package com.zad.accountservice.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zad.accountservice.exception.KafkaException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaSender {

    ObjectMapper objectMapper;
    KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, MessageKey key, Object messageObject) {
        try {
            String message = objectMapper.writeValueAsString(messageObject);
            sendMessage(topic, key, message);
        } catch (JsonProcessingException e) {
            log.info("Can't write object as text!!! Message: {}", e.getMessage());
        } catch (Exception e) {
            String message = "Error while sending Kafka message!!! Message: ";
            String detailedMessage = e.getMessage();
            log.info(message + "{}", detailedMessage);
            throw new KafkaException(message, detailedMessage);
        }
    }

    public void sendMessage(String topic, MessageKey key, String message) {
        log.info("Sending : {}", message);
        kafkaTemplate.send(topic, key.toString(), message);
    }

}