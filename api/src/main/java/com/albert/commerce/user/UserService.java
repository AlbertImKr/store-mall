package com.albert.commerce.user;


import com.albert.commerce.user.dto.UserProfile;
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
        User user = User.builder().email(email).build();
        userRepository.save(user);
    }

    public UserProfile findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("존재하지 않은 이메일입니다."));
        return UserProfile.from(user);
    }
}
