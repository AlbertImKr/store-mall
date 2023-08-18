package com.albert.commerce.command.application.service;


import com.albert.commerce.command.application.port.out.UserRepository;
import com.albert.commerce.command.domain.user.User;
import com.albert.commerce.command.domain.user.UserId;
import com.albert.commerce.common.exception.UserNotFoundException;
import com.albert.commerce.shared.messaging.application.UserUpdateCommand;
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
    public void updateUserInfo(UserUpdateCommand userUpdateCommand) {
        User user = userRepository.findByEmail(userUpdateCommand.getUserEmail())
                .orElseThrow(UserNotFoundException::new);
        user.update(
                userUpdateCommand.getAddress(),
                userUpdateCommand.getNickname(),
                userUpdateCommand.getDateOfBirth(),
                userUpdateCommand.getPhoneNumber()
        );
    }

    @Transactional(readOnly = true)
    public UserId findIdByEmail(String userEmail) {
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
