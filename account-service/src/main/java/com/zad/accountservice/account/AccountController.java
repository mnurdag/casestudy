package com.zad.accountservice.account;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Account", description = "All account operations")
@RestController
@Validated
@RequestMapping(path = "account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {

    AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid AccountRequest request) {
        return ResponseEntity.ok(accountService.save(request));
    }

    @Operation(summary = "Returns the account so that you can retrieve the current balance.",
            description = "You should send username and currency code pair as request body."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account returned"),
            @ApiResponse(responseCode = "404", description = "Account doesn't exist", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<AccountResponse> getAccount(@Valid GetAccountRequest getAccountRequest) {
        return ResponseEntity.ok(accountService.get(getAccountRequest));
    }

    @Operation(summary = "Returns the accounts so that you can retrieve the current balances.",
            description = "You should send a list of username and currency pair as request body."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accounts returned"),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @GetMapping(path = "/list")
    public ResponseEntity<List<AccountResponse>> getAccounts(@Valid @RequestBody List<GetAccountRequest> requestList) {
        return ResponseEntity.ok(accountService.getAccountList(requestList));
    }

    @Operation(summary = "Updates the account balance, and only the balance.",
            description = "You should send username, currencyCode and balance as request body."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account returned"),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PatchMapping
    public ResponseEntity<AccountResponse> update(@Valid @RequestBody AccountRequest requestList) {
        return ResponseEntity.ok(accountService.update(requestList));
    }

}
