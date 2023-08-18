package com.albert.commerce.command.adapter.in.web.dto;

import com.albert.commerce.command.domain.user.Role;
import com.albert.commerce.command.domain.user.User;
import com.albert.commerce.command.domain.user.UserId;
import com.albert.commerce.command.units.BusinessLinks;
import java.time.LocalDate;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Builder
public class UserInfoResponse extends RepresentationModel<UserInfoResponse> {

    private UserId userId;
    private String nickname;
    private String email;
    private Role role;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String address;
    private boolean isActive;

    public static UserInfoResponse from(User user) {
        return UserInfoResponse.builder()
                .userId(user.getUserId())
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInfoResponse that)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return isActive() == that.isActive() && Objects.equals(getUserId(), that.getUserId()) && Objects.equals(
                getNickname(), that.getNickname()) && Objects.equals(getEmail(), that.getEmail())
                && getRole() == that.getRole() && Objects.equals(getDateOfBirth(), that.getDateOfBirth())
                && Objects.equals(getPhoneNumber(), that.getPhoneNumber()) && Objects.equals(
                getAddress(), that.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUserId(), getNickname(), getEmail(), getRole(), getDateOfBirth(),
                getPhoneNumber(), getAddress(), isActive());
    }
}
