package com.albert.commerce.adapter.in.web.user;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class UserSteps {

    public static ExtractableResponse<MockMvcResponse> 유저_정보를_수정한다() {
        Map<String, String> body = new HashMap<>();
        body.put("nickname", "albert");
        body.put("dateOfBirth", "2000-09-09");
        body.put("phoneNumber", "01022222222");
        body.put("address", "인천광역시 남동구 논현동");
        return RestAssuredMockMvc
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE).body(body)
                .when().put("/users/profile")
                .then().log().all()
                .extract();
    }
}
