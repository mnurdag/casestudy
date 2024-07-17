package com.zad.casestudy.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotNull
    @Size(min = 3, max = 25, message = "userName length must be between 3 and 25!")
    String userName;
    @NotNull
    String name;
    @NotNull
    String surname;
    @NotNull
    Integer age;
}
