package com.zad.transactionservice.account;

import com.zad.transactionservice.exception.ApiException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(rollbackFor = Exception.class)
public class AccountService {

    WebClient webClient;
    @Value("${account.api.url}")
    String accountApiUrl;

    public Map<AccountKey, AccountResponse> getAccountMap(List<GetAccountRequest> requestList) {
        List<AccountResponse> response;
        try {
            response = webClient
                    .method(HttpMethod.GET)
                    .uri(accountApiUrl + "/account/list")
                    .body(BodyInserters.fromValue(requestList))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<AccountResponse>>() {})
                    .block();
        } catch (Exception e) {
            throw new ApiException("Couldn't fetch accounts!", e.getMessage());
        }
        return response.stream().collect(Collectors.toMap(
                accountResponse -> AccountKey.builder()
                        .username(accountResponse.getUsername())
                        .currencyCode(accountResponse.getCurrencyCode())
                        .build(),
                accountResponse -> accountResponse));
    }

    public AccountResponse getAccount(GetAccountRequest request) {
        AccountResponse response;
        try {
            response = webClient
                    .method(HttpMethod.GET)
                    .uri(accountApiUrl + "/account?" +
                            "username=" + request.getUsername() +
                            "&currencyCode=" + request.getCurrencyCode())
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(AccountResponse.class)
                    .block();
        } catch (Exception e) {
            throw new ApiException("Couldn't fetch account!", e.getMessage());
        }
        return response;
    }

    public AccountResponse updateAccountBalance(UpdateAccountRequest request) {
        AccountResponse response;
        try {
            response = webClient
                    .patch()
                    .uri(accountApiUrl + "/account")
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<AccountResponse>() {})
                    .block();
        } catch (Exception e) {
            throw new ApiException("Couldn't update account!", e.getMessage());
        }
        return response;
    }

}
