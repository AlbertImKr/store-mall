package com.albert.commerce.user.dto;

import com.albert.commerce.user.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JoinRequestTest {

    public static final String TEST_NICKANME = "Jack";
    public static final String TEST_PASSWORD = "testPassword";
    public static final String TEST_EMAIL = "jack@email.com";

    @DisplayName("JoinRequest info를 가지고 새로운 ID를 가진 유저를 만든다")
    @Test
    void toUser() {
        // given
        JoinRequest joinRequest = new JoinRequest(TEST_NICKANME, TEST_PASSWORD, TEST_EMAIL);

        // when
        User user = joinRequest.toUser();

        // then
        SoftAssertions.assertSoftly(
                softAssertions -> {
                    softAssertions.assertThat(user.getPassword()).isEqualTo(joinRequest.getPassword());
                    softAssertions.assertThat(user.getNickname()).isEqualTo(joinRequest.getNickname());
                    softAssertions.assertThat(user.getEmail()).isEqualTo(joinRequest.getEmail());
                    softAssertions.assertThat(user.getId()).isNotNull();
                }
        );

    }
}
