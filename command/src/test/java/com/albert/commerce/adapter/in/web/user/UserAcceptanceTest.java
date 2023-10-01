package com.albert.commerce.adapter.in.web.user;


import static com.albert.commerce.adapter.in.web.AcceptanceFixture.TEST_EMAIL;
import static com.albert.commerce.adapter.in.web.user.UserSteps.유저_정보_응답_검증;
import static com.albert.commerce.adapter.in.web.user.UserSteps.유저_정보를_수정한다;

import com.albert.commerce.adapter.in.web.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

class UserAcceptanceTest extends AcceptanceTest {

    @WithMockUser(username = TEST_EMAIL)
    @DisplayName("유저 정보 수정 요청을 하면 유저가 정보가 수정 된다")
    @Test
    void shouldUpdateUserInfoWhenUpdateRequestIsMade() {
        // when
        var response = 유저_정보를_수정한다();

        // then
        유저_정보_응답_검증(response);
    }
}
