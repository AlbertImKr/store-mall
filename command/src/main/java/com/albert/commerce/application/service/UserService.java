package com.albert.commerce.application.service;

import com.albert.commerce.application.port.out.UserRepository;
import com.albert.commerce.domain.user.User;
import com.albert.commerce.domain.user.UserId;
import com.albert.commerce.exception.error.UserNotFoundException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
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
        UserId userId = getNewUserId();
        var user = User.createByEmail(userId, email, LocalDateTime.now());
        userRepository.save(user);
    }

    @Transactional
    @ServiceActivator(inputChannel = "UserUploadCommand")
    public boolean upload(UserUploadCommand userUploadCommand) {
        var user = userRepository.findByEmail(userUploadCommand.getUserEmail())
                .orElseThrow(UserNotFoundException::new);
        upload(userUploadCommand, user);
        return true;
    }

    @Transactional(readOnly = true)
    public UserId getUserIdByEmail(String userEmail) {
        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);
        return user.getUserId();
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);
    }

    private UserId getNewUserId() {
        return userRepository.nextId();
    }

    private static void upload(UserUploadCommand userUploadCommand, User user) {
        user.update(
                userUploadCommand.getAddress(),
                userUploadCommand.getNickname(),
                userUploadCommand.getDateOfBirth(),
                userUploadCommand.getPhoneNumber(),
                LocalDateTime.now()
        );
    }
}
