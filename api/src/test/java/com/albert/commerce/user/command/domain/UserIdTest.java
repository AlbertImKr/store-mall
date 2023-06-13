package com.albert.commerce.user.command.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserIdTest {

    @DisplayName("UserId가 생성하면 id도 생성한다")
    @Test
    void create() {
        UserId userId = new UserId();

        assertThat(userId.getId()).isNotNull();
    }
}
