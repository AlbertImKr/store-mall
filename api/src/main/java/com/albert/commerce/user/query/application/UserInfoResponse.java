package com.albert.commerce.user.query.application;

import com.albert.commerce.user.command.domain.Role;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.command.domain.UserId;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Builder
public class UserInfoResponse extends RepresentationModel<UserInfoResponse> {

    private UserId id;
    private String nickname;
    private String email;
    private Role role;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String address;
    private boolean isActive;

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
