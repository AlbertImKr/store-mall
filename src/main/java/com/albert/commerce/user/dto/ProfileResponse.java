package com.albert.commerce.user.dto;

import com.albert.commerce.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileResponse {

    private final String nickname;
    private final String email;

    public static ProfileResponse from(User user) {
        return new ProfileResponse(user.getNickname(), user.getEmail());
    }
}
