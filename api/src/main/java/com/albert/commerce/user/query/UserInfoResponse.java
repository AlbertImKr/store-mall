package com.albert.commerce.user.query;

import com.albert.commerce.user.command.domain.Role;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.command.domain.UserId;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record UserInfoResponse(UserId id, String nickname, String email, Role role,
                               LocalDate dateOfBirth, String phoneNumber, String address,
                               boolean isActive) {

    public static UserInfoResponse from(User user) {
        return UserInfoResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .role(user.getRole())
                .dateOfBirth(user.getDateOfBirth())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .isActive(user.isActive())
                .build();
    }
}
