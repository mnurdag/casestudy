package com.zad.casestudy.account.history;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class OperationTypeConverter implements AttributeConverter<OperationType, String> {

    @Override
    public String convertToDatabaseColumn(OperationType operationType) {
        return operationType.name();
    }

    @Override
    public OperationType convertToEntityAttribute(String s) {
        return OperationType.valueOf(s);
    }
}
