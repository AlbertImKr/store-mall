package com.albert.commerce.user.dto;

import com.albert.commerce.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponse {

    private String nickname;
    private String email;

    public UserProfileResponse() {
    }

    public UserProfileResponse(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }

    public static UserProfileResponse from(User user) {
        return new UserProfileResponse(user.getNickname(), user.getEmail());
    }
}
