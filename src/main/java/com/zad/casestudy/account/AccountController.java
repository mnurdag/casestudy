package com.zad.casestudy.account;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Account", description = "All account operations")
@RestController
@Validated
@RequestMapping(path = "account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {

    AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid CreateAccountRequest request) {
        return ResponseEntity.ok(accountService.save(request));
    }

    @Operation(summary = "Returns the account so that you can retrieve the current account balance.",
            description = "You should pass username and currency code as path variables in order."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account returned"),
            @ApiResponse(responseCode = "404", description = "Account doesn't exist", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @GetMapping(path = "/{username}/{currencyCode}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable @Size(min = 3, max = 25, message = "userName length must be between 3 and 25!")
                                   String username,
                                   @PathVariable @Size(min = 3, max = 3, message = "currencyCode must be 3 characters long!")
                                   String currencyCode) {
        return ResponseEntity.ok(accountService.get(username, currencyCode));
    }

}
