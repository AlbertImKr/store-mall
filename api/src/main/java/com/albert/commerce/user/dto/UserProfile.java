package com.albert.commerce.user.dto;

import com.albert.commerce.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfile {

    private String nickname;
    private String email;

    public UserProfile() {
    }

    public UserProfile(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }

    public static UserProfile from(User user) {
        return new UserProfile(user.getNickname(), user.getEmail());
    }
}
