package com.zad.accountservice.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ObjectMapperUtil {

    ObjectMapper objectMapper;

    public <T> T readValue(String text, Class<T> clazz) {
        try {
            return objectMapper.readValue(text, clazz);
        } catch (JsonProcessingException e) {
            log.info("Can't convert text to object!!!");
            throw new RuntimeException(e);
        }
    }

    public <T> T treeToValue(JsonNode jsonNode, Class<T> clazz) {
        try {
            return objectMapper.treeToValue(jsonNode, clazz);
        } catch (JsonProcessingException e) {
            log.info("Can't convert json to object!!!");
            throw new RuntimeException(e);
        }
    }

}
