package com.zad.casestudy.currency;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Currency implements Serializable {

    String baseCode;
    JsonNode conversionRates;

    public BigDecimal getConversionRate(String currencyCode) {
        return new BigDecimal(conversionRates.get(currencyCode).asText());
    }

}
