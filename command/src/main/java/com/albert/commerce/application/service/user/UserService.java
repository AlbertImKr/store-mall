package com.albert.commerce.application.service.user;

import static com.albert.commerce.domain.units.MessageChannelName.USER_UPLOAD_CHANNEL;

import com.albert.commerce.application.port.out.UserRepository;
import com.albert.commerce.application.service.exception.error.UserNotFoundException;
import com.albert.commerce.application.service.utils.Success;
import com.albert.commerce.domain.user.User;
import com.albert.commerce.domain.user.UserId;
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
    public User createByEmail(String email) {
        UserId userId = getNewUserId();
        var user = User.createByEmail(userId, email, LocalDateTime.now());
        return userRepository.save(user);
    }

    @Transactional
    @ServiceActivator(inputChannel = USER_UPLOAD_CHANNEL)
    public Success upload(UserUploadCommand userUploadCommand) {
        var user = getByEmail(userUploadCommand.getUserEmail());
        upload(userUploadCommand, user);
        return Success.getInstance();
    }

    public UserId getUserIdByEmail(String userEmail) {
        var user = getByEmail(userEmail);
        return user.getUserId();
    }

    public User getByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);
    }

    public boolean exists(String email) {
        return userRepository.existsByEmail(email);
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
