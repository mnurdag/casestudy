package com.zad.userservice.user;

import com.zad.userservice.exception.ApiException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(rollbackFor = Exception.class)
public class UserService {

    UserRepository userRepository;

    public UserResponse save(CreateUserRequest request) {
        User user = userRepository.save(User.builder()
                .username(request.getUsername())
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

    public List<UserResponse> getUsers(List<String> usernameList) {
        List<User> userList = userRepository.findByUsernameIn(usernameList);
        return userList.stream().map(user ->
                        UserResponse.builder()
                                .username(user.getUsername())
                                .name(user.getName())
                                .surname(user.getSurname())
                                .age(user.getAge())
                                .build())
                .collect(Collectors.toList());
    }

    public UserResponse getUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ApiException("User doesn't exist! ", "username: " + username));
        return user.toResponse();
    }

}
