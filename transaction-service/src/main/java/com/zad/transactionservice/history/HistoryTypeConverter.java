package com.zad.transactionservice.history;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class HistoryTypeConverter implements AttributeConverter<HistoryType, String> {

    @Override
    public String convertToDatabaseColumn(HistoryType historyType) {
        return historyType.name();
    }

    @Override
    public HistoryType convertToEntityAttribute(String s) {
        return HistoryType.valueOf(s);
    }
}
