package com.albert.commerce.adapter.in.web.store;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.AbstractIntegerAssert;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;

public class StoreSteps {

    public static ExtractableResponse<MockMvcResponse> my_스토어를_등록한다() {
        Map<String, String> body = new HashMap<>();
        body.put("storeName", "GoodTime");
        body.put("ownerName", "Albert");
        body.put("address", "서울시 강남구 신사동");
        body.put("phoneNumber", "01012345678");
        body.put("email", "goodtime@email.com");
        return RestAssuredMockMvc
                .given().log().all()
                .body(body).contentType(MediaType.APPLICATION_JSON)
                .post("/stores")
                .then().log().all()
                .extract();
    }

    public static void my_스토어_등록_결과_검증한다(ExtractableResponse<MockMvcResponse> response) {
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(201),
                () -> assertThat(response.jsonPath().getString("storeId")).isNotNull()
        );
    }

    public static void my_스토어_등록_실패_검증(ExtractableResponse<MockMvcResponse> response) {
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(400),
                () -> assertThat(response.jsonPath().getString("error-message")).isEqualTo("스토어 이미 존재합니다.")
        );
    }

    public static MockMvcResponse my_스토어를_업로드_한다() {
        Map<String, Object> body = new HashMap<>();
        body.put("storeName", "goodLife");
        body.put("address", "서울시 강남구 대치4동");
        body.put("phoneNumber", "111-2222-3333");
        body.put("email", "goodlife@email.com");
        body.put("ownerName", "Jack");

        return RestAssuredMockMvc.given().log().all()
                .body(body).contentType(MediaType.APPLICATION_JSON)
                .when().put("/stores/my")
                .then().log().all()
                .extract().response();
    }

    public static void my_스토어_업로드_결과_검증(MockMvcResponse response) {
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(204)
        );
    }


    public static void my_스토어_존재하지_않은_결과_검증(MockMvcResponse response) {
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(400),
                () -> assertThat(response.jsonPath().getString("error-message")).isEqualTo("스토어가 존재 하지 않습니다.")
        );
    }

    public static AbstractIntegerAssert<?> my_스토어_삭제_결과_검증(ExtractableResponse<MockMvcResponse> response) {
        return assertThat(response.statusCode()).isEqualTo(204);
    }

    public static ExtractableResponse<MockMvcResponse> my_스토어를_삭제한다() {
        return RestAssuredMockMvc.given().log().all()
                .when().delete("/stores/my")
                .then().log().all()
                .extract();
    }
}
