package com.zad.casestudy.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(rollbackFor = Exception.class)
public class UserService {

    UserRepository userRepository;

    public UserResponse save(CreateUserRequest request) {
        User user = userRepository.save(User.builder()
                .username(request.getUserName())
                .name(request.getName())
                .surname(request.getSurname())
                .age(request.getAge())
                .build());
        return UserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .build();
    }

}
