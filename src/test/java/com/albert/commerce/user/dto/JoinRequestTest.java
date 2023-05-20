package com.albert.commerce.user.dto;

import com.albert.commerce.user.EncryptionAlgorithm;
import com.albert.commerce.user.Role;
import com.albert.commerce.user.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class JoinRequestTest {

    public static final String RIGHT_EMAIL = "jack@email.com";
    public static final String RIGHT_8_PASSWORD = "kkkkk!1S";
    public static final String DIFFERENT_PASSWORD = "kkkkkk!1S";
    public static final String RIGHT_3_NICKNAME = "kim";

    @DisplayName("JoinRequest info를 가지고 새로운 ID를 가진 유저를 만든다")
    @Test
    void toUser() {
        // given
        JoinRequest joinRequest = new JoinRequest(RIGHT_EMAIL, RIGHT_3_NICKNAME, RIGHT_8_PASSWORD, RIGHT_8_PASSWORD);
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

    @DisplayName("두 비밀번호 일치하면 true 아니면 false를 return")
    @Test
    void isSamePassword() {
        // given
        JoinRequest samePasswordJoinRequest = new JoinRequest(RIGHT_EMAIL, RIGHT_3_NICKNAME, RIGHT_8_PASSWORD,
                RIGHT_8_PASSWORD);
        JoinRequest differentPasswordJoinRequest = new JoinRequest(RIGHT_EMAIL, RIGHT_3_NICKNAME, RIGHT_8_PASSWORD,
                DIFFERENT_PASSWORD);

        // when,then
        assertThat(samePasswordJoinRequest.isSamePassword()).isTrue();
        assertThat(differentPasswordJoinRequest.isSamePassword()).isFalse();
    }
}
