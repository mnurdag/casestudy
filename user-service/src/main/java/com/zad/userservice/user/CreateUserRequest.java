package com.zad.userservice.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotNull
    @Size(min = 3, max = 25, message = "username length must be between 3 and 25!")
    @Pattern(regexp="^[A-Za-z]*$",message = "username must contain letters only.")
    String username;
    @NotNull
    String name;
    @NotNull
    String surname;
    @NotNull
    Integer age;
}
