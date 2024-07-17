package com.zad.casestudy.transaction;

import com.zad.casestudy.account.Account;
import com.zad.casestudy.account.AccountRepository;
import com.zad.casestudy.account.AccountResponse;
import com.zad.casestudy.account.history.HistoryRecord;
import com.zad.casestudy.account.history.OperationType;
import com.zad.casestudy.common.KafkaConstants;
import com.zad.casestudy.common.KafkaSender;
import com.zad.casestudy.common.MessageService;
import com.zad.casestudy.common.ObjectMapperUtil;
import com.zad.casestudy.exception.ApiException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@Transactional(rollbackFor = Exception.class)
public class WithdrawService extends AccountUpdateService {

    AccountRepository accountRepository;
    MessageService messageService;
    ObjectMapperUtil objectMapperUtil;
    KafkaSender kafkaSender;

    @KafkaListener(groupId = "gid", topics = KafkaConstants.TOPIC_WITHDRAW)
    public void withdraw(@Payload String data) {
        AccountUpdateRequest request = objectMapperUtil.readValue(data, AccountUpdateRequest.class);
        Account account = accountRepository.findByUsernameAndCurrencyCode(
                request.getUserName(), request.getCurrencyCode());
        this.withdraw(account, request.getAmount());
        sendHistoryMessage(request, OperationType.WITHDRAW);
    }

    public AccountResponse withdraw(Account account, BigDecimal amount) {
        if(account.hasProvision(amount)) {
            return account.withdraw(amount).toResponse();
        } else {
            throw new ApiException("Withdrawal error!",
                    messageService.getMessage("withdrawal.balance.cannot.be.negative",
                            amount, account.getCurrencyCode(), account.getUsername(), account.getCurrencyCode()));
        }
    }

}
