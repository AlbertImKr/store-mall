package com.albert.commerce.user.application;


import com.albert.commerce.common.model.SequenceGenerator;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.command.domain.UserId;
import com.albert.commerce.user.command.domain.UserRepository;
import com.albert.commerce.user.query.UserDao;
import com.albert.commerce.user.query.UserInfoResponse;
import com.albert.commerce.user.query.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDao userDao;
    private final SequenceGenerator sequenceGenerator;

    public void init(String email) {
        if (userDao.existsByEmail(email)) {
            return;
        }
        User user = User.createByEmail(email, UserId.from(sequenceGenerator.generate()));
        userRepository.save(user);
    }

    public UserInfoResponse findByEmail(String email) {
        return UserInfoResponse.from(userDao.findUserProfileByEmail(email)
                .orElseThrow(UserNotFoundException::new));
    }

    public UserInfoResponse updateUserInfo(String email, UserProfileRequest userProfileRequest) {
        return UserInfoResponse.from(userDao.updateUserInfo(email, userProfileRequest)
                .orElseThrow(UserNotFoundException::new));
    }
}
