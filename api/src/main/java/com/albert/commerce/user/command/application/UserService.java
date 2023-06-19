package com.albert.commerce.user.command.application;


import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.command.domain.UserId;
import com.albert.commerce.user.command.domain.UserRepository;
import com.albert.commerce.user.query.application.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SequenceGenerator sequenceGenerator;

    public void init(String email) {
        if (userRepository.existsByEmail(email)) {
            return;
        }
        User user = User.createByEmail(email, UserId.from(sequenceGenerator.generate()));
        userRepository.save(user);
    }

    public UserInfoResponse updateUserInfo(String email, UserProfileRequest userProfileRequest) {
        return UserInfoResponse.from(userRepository.updateUserInfo(email, userProfileRequest)
                .orElseThrow(UserNotFoundException::new));
    }
}
