package com.albert.commerce.user.dto;

import com.albert.commerce.user.User;
import lombok.Getter;

@Getter
public class JoinRequest {
    private final String nickname;
    private final String password;
    private final String email;

    public JoinRequest(String nickname, String password, String email) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }

    public User toUser() {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
