package com.zad.userservice.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUsernameIn(List<String> username);

    Optional<User> findByUsername(String username);

}
