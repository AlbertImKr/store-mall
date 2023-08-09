package com.albert.commerce.api.user.command.application.dto;

import com.albert.commerce.api.common.units.BusinessLinks;
import com.albert.commerce.api.user.command.domain.Role;
import com.albert.commerce.api.user.command.domain.User;
import com.albert.commerce.api.user.command.domain.UserId;
import com.albert.commerce.api.user.query.domain.UserData;
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
                .id(user.getUserId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .role(user.getRole())
                .dateOfBirth(user.getDateOfBirth())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .isActive(user.isActive())
                .build()
                .add(BusinessLinks.USER_INFO_RESPONSE_LINKS);
    }

    public static UserInfoResponse from(UserData user) {
        return UserInfoResponse.builder()
                .id(user.getUserId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .role(user.getRole())
                .dateOfBirth(user.getDateOfBirth())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .isActive(user.isActive())
                .build()
                .add(BusinessLinks.USER_INFO_RESPONSE_LINKS);
    }
}
