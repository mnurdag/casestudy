package com.zad.transactionservice.transaction;

import com.zad.transactionservice.common.BaseResponse;
import com.zad.transactionservice.common.KafkaConstants;
import com.zad.transactionservice.common.KafkaSender;
import com.zad.transactionservice.common.MessageKey;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(path = "transaction")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionController {

    TransactionService transactionService;
    KafkaSender kafkaSender;

    @PatchMapping(path = "/withdraw")
    public ResponseEntity<BaseResponse> withdraw(@Valid AccountUpdateRequest request) {
        kafkaSender.sendMessage(KafkaConstants.TOPIC_TRANSACTION, MessageKey.WITHDRAW, request);
        return ResponseEntity.ok(BaseResponse.builder()
                .success(true)
                .message("Withdraw request has been added to the queue.")
                .build());
    }

    @PatchMapping(path = "/deposit")
    public ResponseEntity<BaseResponse> deposit(@Valid AccountUpdateRequest request) {
        kafkaSender.sendMessage(KafkaConstants.TOPIC_TRANSACTION, MessageKey.DEPOSIT, request);
        return ResponseEntity.ok(BaseResponse.builder()
                .success(true)
                .message("Deposit request has been added to the queue.")
                .build());
    }

    @PostMapping(path = "/send")
    public ResponseEntity<BaseResponse> send(@Valid SendRequest request) {
        transactionService.setReceivedAmountIfNecessary(request);
        kafkaSender.sendMessage(KafkaConstants.TOPIC_TRANSACTION, MessageKey.SEND, request);
        return ResponseEntity.ok(BaseResponse.builder()
                .success(true)
                .message("Send request has been added to the queue.")
                .build());
    }

}
