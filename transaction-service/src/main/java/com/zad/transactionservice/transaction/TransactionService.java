package com.zad.transactionservice.transaction;

import com.zad.transactionservice.currency.Currency;
import com.zad.transactionservice.currency.CurrencyService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@Transactional(rollbackFor = Exception.class)
public class TransactionService {

    CurrencyService currencyService;

    public void setReceivedAmountIfNecessary(SendRequest request) {
        if (!request.getReceiverCurrencyCode().equals(request.getSenderCurrencyCode())) {
            Currency currency = currencyService.getCurrency(request.getSenderCurrencyCode());
            BigDecimal receivedAmount = currency.getConversionRate(
                    request.getReceiverCurrencyCode()).multiply(request.getAmount());
            request.setConvertedAmount(receivedAmount);
        } else {
            request.setConvertedAmount(request.getAmount());
        }
    }

}
