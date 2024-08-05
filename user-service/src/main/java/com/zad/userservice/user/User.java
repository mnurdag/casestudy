package com.zad.userservice.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NaturalId;

@Data
@Table(name = "user", schema = "casestudy")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @NaturalId
    String username;
    String name;
    String surname;
    int age;

    public UserResponse toResponse() {
        return UserResponse.builder()
                .username(username)
                .name(name)
                .surname(surname)
                .age(age)
                .build();
    }
}
