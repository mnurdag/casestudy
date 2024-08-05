package com.zad.accountservice.user;

import com.zad.accountservice.exception.ApiException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(rollbackFor = Exception.class)
public class UserService {

    WebClient webClient;
    @Value("${user.api.url}")
    String userApiUrl;

    public boolean assertExistsByUsername(String username) {
        UserResponse response;
        try {
            response = webClient.get()
                    .uri(userApiUrl + "/user?username=" + username)
                    .retrieve()
                    .bodyToMono(UserResponse.class)
                    .block();
        } catch (Exception e) {
            throw new ApiException("Couldn't fetch user!", e.getMessage());
        }
        return response != null;
    }

}
