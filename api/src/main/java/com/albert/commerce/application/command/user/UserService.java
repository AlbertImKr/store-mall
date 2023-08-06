package com.albert.commerce.application.command.user;


import com.albert.commerce.application.command.user.dto.UserProfileRequest;
import com.albert.commerce.common.exception.UserNotFoundException;
import com.albert.commerce.domain.command.user.User;
import com.albert.commerce.domain.command.user.UserId;
import com.albert.commerce.domain.command.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void createByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            return;
        }
        User user = User.createByEmail(email);
        userRepository.save(user);
    }

    @Transactional
    public UserId updateUserInfo(String email, UserProfileRequest userProfileRequest) {
        User user = userRepository.updateUserInfo(email, userProfileRequest)
                .orElseThrow(UserNotFoundException::new);
        return user.getUserId();
    }
}
