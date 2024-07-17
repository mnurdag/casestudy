package com.zad.casestudy.user;

import com.zad.casestudy.exception.ApiException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getByUsername(String username);

    default void assertExistsByUsername(String username) {
        getByUsername(username).orElseThrow(() ->
                new ApiException("Error!", "User with username: " + username + " doesn't exist!"));
    }

}
