package com.albert.commerce.user.application;


import static com.albert.commerce.user.command.domain.Role.USER;

import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.command.domain.UserRepository;
import com.albert.commerce.user.query.dto.UserProfileResponse;
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
        User user = User.builder()
                .email(email)
                .role(USER)
                .build();
        userRepository.save(user);
    }

    public UserProfileResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("존재하지 않은 이메일입니다."));
        return UserProfileResponse.from(user);
    }
}
