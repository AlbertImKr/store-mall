package com.albert.commerce.user.dto;

import com.albert.commerce.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JoinRequest {
    private final String email;
    private final String nickname;
    private final String password;
    private final String confirmPassword;


    public User toUser() {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
