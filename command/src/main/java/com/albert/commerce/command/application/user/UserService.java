package com.albert.commerce.command.application.user;


import com.albert.commerce.command.application.user.dto.UserProfileRequest;
import com.albert.commerce.command.domain.user.User;
import com.albert.commerce.command.domain.user.UserId;
import com.albert.commerce.command.domain.user.UserRepository;
import com.albert.commerce.common.exception.UserNotFoundException;
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
    public void updateUserInfo(String email, UserProfileRequest userProfileRequest) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        user.update(
                userProfileRequest.address(),
                userProfileRequest.nickname(),
                userProfileRequest.dateOfBirth(),
                userProfileRequest.phoneNumber()
        );
    }

    @Transactional(readOnly = true)
    public UserId findIdByEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);
        return user.getUserId();
    }

    public User getUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);
    }
}
