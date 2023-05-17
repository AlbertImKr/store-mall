package com.albert.commerce.user;

import com.albert.commerce.user.dto.JoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User save(JoinRequest joinRequest) {
        return userRepository.save(joinRequest.toUser());
    }
}
