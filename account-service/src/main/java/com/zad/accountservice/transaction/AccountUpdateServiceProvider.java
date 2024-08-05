package com.zad.accountservice.transaction;

import com.zad.accountservice.common.MessageKey;
import com.zad.accountservice.transaction.service.AccountUpdateService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountUpdateServiceProvider {

    Map<String, AccountUpdateService> serviceMap;

    public AccountUpdateService getServiceByMessageKey(String key) {
        MessageKey messageKey = MessageKey.valueOf(key);
        String serviceClassName = messageKey.getServiceClass().getSimpleName();
        String beanName = Character.toLowerCase(serviceClassName.charAt(0)) + serviceClassName.substring(1);
        return serviceMap.get(beanName);
    }

}
