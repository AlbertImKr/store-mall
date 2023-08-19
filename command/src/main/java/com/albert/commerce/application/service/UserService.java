package com.albert.commerce.application.service;

import com.albert.commerce.application.port.out.UserRepository;
import com.albert.commerce.domain.user.User;
import com.albert.commerce.domain.user.UserId;
import com.albert.commerce.exception.UserNotFoundException;
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
        User user = User.createByEmail(email);
        userRepository.save(user);
    }

    @Transactional
    @ServiceActivator(inputChannel = "UserUpdateCommand")
    public boolean update(UserUpdateCommand userUpdateCommand) {
        User user = userRepository.findByEmail(userUpdateCommand.getUserEmail())
                .orElseThrow(UserNotFoundException::new);
        user.update(
                userUpdateCommand.getAddress(),
                userUpdateCommand.getNickname(),
                userUpdateCommand.getDateOfBirth(),
                userUpdateCommand.getPhoneNumber()
        );
        return true;
    }

    @Transactional(readOnly = true)
    public UserId getUserIdByEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);
        return user.getUserId();
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);
    }
}
