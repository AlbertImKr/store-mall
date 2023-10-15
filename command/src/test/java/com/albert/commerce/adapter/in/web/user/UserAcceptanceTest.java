package com.albert.commerce.adapter.in.web.user;


import static com.albert.commerce.adapter.in.web.user.UserSteps.유저_정보_응답_검증;
import static com.albert.commerce.adapter.in.web.user.UserSteps.유저_정보를_수정한다;

import com.albert.commerce.adapter.in.web.AcceptanceTest;
import com.albert.commerce.adapter.in.web.security.WithMockOAuth2User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@WithMockOAuth2User
class UserAcceptanceTest extends AcceptanceTest {


    @DisplayName("유저 정보 수정 요청을 하면 유저가 정보가 수정 된다")
    @Test
    void shouldUpdateUserInfoWhenUpdateRequestIsMade() {
        // when
        var response = 유저_정보를_수정한다();

        // then
        유저_정보_응답_검증(response);
    }
}
