package com.zad.transactionservice.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrencyApiResponse implements Serializable {

    String result;
    String errorType;
    String baseCode;
    JsonNode conversionRates;

    @JsonProperty("error-type")
    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    @JsonProperty("base_code")
    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }

    @JsonProperty("conversion_rates")
    public void setConversionRates(JsonNode conversionRates) {
        this.conversionRates = conversionRates;
    }

    public boolean isFailed() {
        return !result.toLowerCase().equals("success");
    }

    public Currency toCurrency() {
        return Currency.builder()
                .baseCode(baseCode)
                .conversionRates(conversionRates)
                .build();
    }

}
