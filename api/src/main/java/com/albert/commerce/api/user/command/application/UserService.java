package com.albert.commerce.api.user.command.application;


import com.albert.commerce.api.user.UserNotFoundException;
import com.albert.commerce.api.user.command.application.dto.UserProfileRequest;
import com.albert.commerce.api.user.command.domain.User;
import com.albert.commerce.api.user.command.domain.UserId;
import com.albert.commerce.api.user.command.domain.UserRepository;
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


    public UserId findIdByEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);
        return user.getUserId();
    }
}
