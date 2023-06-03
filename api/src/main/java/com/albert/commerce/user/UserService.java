package com.albert.commerce.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void init(String email) {
        if (userRepository.existsByEmail(email)) {
            return;
        }
        User user = User.builder().email(email).build();
        userRepository.save(user);
    }
}
