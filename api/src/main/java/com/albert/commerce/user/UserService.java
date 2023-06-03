package com.albert.commerce.user;


import static com.albert.commerce.user.Role.USER;

import com.albert.commerce.user.dto.UserProfileResponse;
import com.albert.commerce.user.exception.EmailNotFoundException;
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
