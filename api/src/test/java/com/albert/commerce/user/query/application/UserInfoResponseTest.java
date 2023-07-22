package com.albert.commerce.user.query.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.albert.commerce.user.command.application.dto.UserInfoResponse;
import com.albert.commerce.user.command.domain.Role;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.command.domain.UserId;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserInfoResponseTest {

    @DisplayName("User에서 info가져와 UserInfoResponse를 생성한다")
    @Test
    void from() {
        String testNickName = "testNickName";
        String testEmail = "testEmail";
        LocalDate testDateOfBirth = LocalDate.now();
        Role testRole = Role.USER;
        String testPhoneNumber = "100-0000-0000";
        String testAddress = "testAddress";
        boolean testIsActive = false;
        UserId testId = UserId.from("testId");
        User user = User.builder()
                .userId(testId)
                .nickname(testNickName)
                .email(testEmail)
                .role(testRole)
                .dateOfBirth(testDateOfBirth)
                .phoneNumber(testPhoneNumber)
                .address(testAddress)
                .build();

        UserInfoResponse userInfoResponse = UserInfoResponse.from(user);

        assertThat(userInfoResponse.getId()).isEqualTo(testId);
        assertThat(userInfoResponse.getNickname()).isEqualTo(testNickName);
        assertThat(userInfoResponse.getEmail()).isEqualTo(testEmail);
        assertThat(userInfoResponse.getRole()).isEqualTo(testRole);
        assertThat(userInfoResponse.getDateOfBirth()).isEqualTo(testDateOfBirth);
        assertThat(userInfoResponse.getPhoneNumber()).isEqualTo(testPhoneNumber);
        assertThat(userInfoResponse.getAddress()).isEqualTo(testAddress);
        assertThat(userInfoResponse.isActive()).isEqualTo(testIsActive);
    }
}
