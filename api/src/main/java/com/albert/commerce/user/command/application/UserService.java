package com.albert.commerce.user.command.application;


import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.command.application.dto.UserInfoResponse;
import com.albert.commerce.user.command.application.dto.UserProfileRequest;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.command.domain.UserRepository;
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
    public UserInfoResponse updateUserInfo(String email, UserProfileRequest userProfileRequest) {
        return UserInfoResponse.from(userRepository.updateUserInfo(email, userProfileRequest)
                .orElseThrow(UserNotFoundException::new));
    }
}
