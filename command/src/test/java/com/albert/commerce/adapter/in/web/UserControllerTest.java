package com.albert.commerce.adapter.in.web;

import io.restassured.RestAssured;
import io.restassured.authentication.OAuthSignature;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class UserControllerTest extends AcceptanceTest {

    @DisplayName("유저 정보 수정 요청을 하면 유저가 정보가 수정 된다")
    @Test
    void shouldUpdateUserInfoWhenUpdateRequestIsMade() {
        Map<String, String> body = new HashMap<>();
        body.put("nickname", "albert");
        body.put("dateOfBirth", "2000-09-09");
        body.put("phoneNumber", "01022222222");
        body.put("address", "인천광역시 남동구 논현동");
        RestAssured.given().log().all()
                .auth().oauth2(accessToken, OAuthSignature.HEADER)
                .contentType(MediaType.APPLICATION_JSON_VALUE).body(body)
                .when().put("/users/profile")
                .then().log().all()
                .extract();
    }
}
