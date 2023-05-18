package com.albert.commerce.user.dto;

import com.albert.commerce.user.EncryptionAlgorithm;
import com.albert.commerce.user.Role;
import com.albert.commerce.user.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class JoinRequestTest {

    public static final String TEST_NICKNAME = "Jack";
    public static final String TEST_PASSWORD = "testPassword";
    public static final String TEST_EMAIL = "jack@email.com";


    @DisplayName("JoinRequest info를 가지고 새로운 ID를 가진 유저를 만든다")
    @Test
    void toUser() {
        // given
        JoinRequest joinRequest = new JoinRequest(TEST_NICKNAME, TEST_PASSWORD, TEST_EMAIL, TEST_EMAIL);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        // when
        User user = joinRequest.toUser(bCryptPasswordEncoder, EncryptionAlgorithm.BCRYPT, Role.USER);

        // then
        SoftAssertions.assertSoftly(
                softAssertions -> {
                    softAssertions.assertThat(bCryptPasswordEncoder.matches(joinRequest.getPassword(),
                            user.getPassword())).isTrue();
                    softAssertions.assertThat(user.getNickname()).isEqualTo(joinRequest.getNickname());
                    softAssertions.assertThat(user.getEmail()).isEqualTo(joinRequest.getEmail());
                    softAssertions.assertThat(user.getId()).isNotNull();
                }
        );

    }
}
