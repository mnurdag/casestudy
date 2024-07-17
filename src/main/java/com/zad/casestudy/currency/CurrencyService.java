package com.zad.casestudy.currency;

import com.zad.casestudy.exception.ApiException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CurrencyService {

    @NonFinal
    @Value("${currency.api.url}")
    String apiUrl;
    WebClient webClient;

    @Cacheable(value = "currency")
    public Currency getCurrency(String code) {
        CurrencyApiResponse response = webClient.get()
                .uri(apiUrl + code)
                .retrieve()
                .bodyToMono(CurrencyApiResponse.class)
                .block();
        if(response.isFailed()) {
            throw new ApiException("Couldn't fetch curreny from API!", response.getErrorType());
        }

        return response.toCurrency();
    }

}
