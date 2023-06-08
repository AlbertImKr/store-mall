package com.albert.commerce.user.command.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @DisplayName("Builder로 유저 생성하면 id도 부여 받는다")
    @Test
    void createByBuilder() {
        User user = User.builder()
                .nickname("test")
                .email("test@email.com")
                .role(Role.USER)
                .build();

        assertThat(user.getId()).isNotNull();
    }
}
