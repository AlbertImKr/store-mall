package com.albert.commerce.user.application;


import static com.albert.commerce.user.command.domain.Role.USER;

import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.command.domain.UserRepository;
import com.albert.commerce.user.query.UserDataDao;
import com.albert.commerce.user.query.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDataDao userDataDao;

    public void init(String email) {
        if (userDataDao.existsByEmail(email)) {
            return;
        }
        User user = User.builder()
                .email(email)
                .role(USER)
                .build();
        userRepository.save(user);
    }

    public UserProfileResponse findByEmail(String email) {
        return userDataDao.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("존재하지 않은 이메일입니다."));
    }
}
