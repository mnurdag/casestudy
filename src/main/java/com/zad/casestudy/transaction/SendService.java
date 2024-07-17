package com.zad.casestudy.transaction;

import com.zad.casestudy.account.Account;
import com.zad.casestudy.account.AccountRepository;
import com.zad.casestudy.account.history.OperationType;
import com.zad.casestudy.common.KafkaConstants;
import com.zad.casestudy.common.KafkaSender;
import com.zad.casestudy.common.ObjectMapperUtil;
import com.zad.casestudy.currency.Currency;
import com.zad.casestudy.currency.CurrencyService;
import com.zad.casestudy.user.UserRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@Transactional(rollbackFor = Exception.class)
public class SendService extends AccountUpdateService {

    CurrencyService currencyService;
    WithdrawService withdrawService;
    AccountRepository accountRepository;
    UserRepository userRepository;
    ObjectMapperUtil objectMapperUtil;
    KafkaSender kafkaSender;

    @KafkaListener(groupId = "gid", topics = KafkaConstants.TOPIC_SEND)
    public void send(@Payload String data) {
        SendRequest request = objectMapperUtil.readValue(data, SendRequest.class);
        userRepository.assertExistsByUsername(request.getSenderUserName());
        userRepository.assertExistsByUsername(request.getReceiverUserName());
        Account sender = accountRepository.findByUsernameAndCurrencyCode(
                request.getSenderUserName(), request.getSenderCurrencyCode());
        Account receiver = accountRepository.findByUsernameAndCurrencyCode(
                request.getReceiverUserName(), request.getReceiverCurrencyCode());
        BigDecimal receivedAmount = this.getReceivedAmount(request, sender);
        withdrawService.withdraw(sender, request.getAmount());
        receiver.deposit(receivedAmount).toResponse();
        sendHistoryMessage(request.toSenderAccountUpdateRequest(), OperationType.SEND);
        sendHistoryMessage(request.toReceiverAccountUpdateRequest(), OperationType.RECEIVE);
    }

    private BigDecimal getReceivedAmount(SendRequest request, Account sender) {
        BigDecimal receivedAmount = request.getAmount();
        if (!request.getReceiverCurrencyCode().equals(sender.getCurrencyCode())) {
            Currency currency = currencyService.getCurrency(request.getSenderCurrencyCode());
            receivedAmount = currency.getConversionRate(request.getReceiverCurrencyCode()).multiply(request.getAmount());
        }
        return receivedAmount;
    }

}
