package com.zad.userservice.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping(path = "user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid CreateUserRequest request) {
        return ResponseEntity.ok(userService.save(request));
    }

    @GetMapping(path = "/list")
    public ResponseEntity<List<UserResponse>> getUsers(
            @Valid
            @NotEmpty
            List<String> usernameList) {
        return ResponseEntity.ok(userService.getUsers(usernameList));
    }

    @GetMapping
    public ResponseEntity<UserResponse> getUser(@Valid @NotBlank String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }

}
