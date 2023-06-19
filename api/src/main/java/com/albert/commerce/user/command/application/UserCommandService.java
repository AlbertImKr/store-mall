package com.albert.commerce.user.command.application;


import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.command.domain.UserCommandDAO;
import com.albert.commerce.user.command.domain.UserId;
import com.albert.commerce.user.query.application.UserInfoResponse;
import com.albert.commerce.user.query.application.UserProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserCommandService {

    private final UserCommandDAO userCommandDAO;
    private final SequenceGenerator sequenceGenerator;

    public void init(String email) {
        if (userCommandDAO.existsByEmail(email)) {
            return;
        }
        User user = User.createByEmail(email, UserId.from(sequenceGenerator.generate()));
        userCommandDAO.save(user);
    }

    public UserInfoResponse updateUserInfo(String email, UserProfileRequest userProfileRequest) {
        return UserInfoResponse.from(userCommandDAO.updateUserInfo(email, userProfileRequest)
                .orElseThrow(UserNotFoundException::new));
    }
}
