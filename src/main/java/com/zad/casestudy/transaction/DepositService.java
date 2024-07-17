package com.zad.casestudy.transaction;

import com.zad.casestudy.account.Account;
import com.zad.casestudy.account.AccountRepository;
import com.zad.casestudy.account.history.OperationType;
import com.zad.casestudy.common.KafkaConstants;
import com.zad.casestudy.common.KafkaSender;
import com.zad.casestudy.common.ObjectMapperUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@Transactional(rollbackFor = Exception.class)
public class DepositService extends AccountUpdateService {

    AccountRepository accountRepository;
    ObjectMapperUtil objectMapperUtil;
    KafkaSender kafkaSender;

    @KafkaListener(groupId = "gid", topics = KafkaConstants.TOPIC_DEPOSIT)
    public void deposit(@Payload String data) {
        AccountUpdateRequest request = objectMapperUtil.readValue(data, AccountUpdateRequest.class);
        Account account = accountRepository.findByUsernameAndCurrencyCode(
                request.getUserName(), request.getCurrencyCode());
        account.deposit(request.getAmount());
        sendHistoryMessage(request, OperationType.DEPOSIT);
    }

}

