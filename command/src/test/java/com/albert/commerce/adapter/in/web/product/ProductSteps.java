package com.albert.commerce.adapter.in.web.product;

import static org.assertj.core.api.Assertions.assertThat;

import com.albert.commerce.application.service.exception.error.ErrorMessage;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ProductSteps {

    public static ExtractableResponse<MockMvcResponse> 상품을_등록한다() {
        Map<String, Object> body = new HashMap<>();
        body.put("productName", "productName");
        body.put("price", 1000);
        body.put("description", "description");
        body.put("brand", "brand");
        body.put("category", "category");
        var response = RestAssuredMockMvc.given().log().all()
                .body(body).contentType(MediaType.APPLICATION_JSON)
                .when().post("/products")
                .then().log().all()
                .extract();
        return response;
    }

    public static void 상품_등록_성공_결과_검증한다(ExtractableResponse<MockMvcResponse> response) {
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(201),
                () -> assertThat(response.body().jsonPath().getString("productId")).isNotNull()
        );
    }

    public static void 상품_업로드_결과를_검증한다(ExtractableResponse<MockMvcResponse> response) {
        assertThat(response.statusCode()).isEqualTo(204);
    }

    public static ExtractableResponse<MockMvcResponse> 상품을_업로드한다(String productId) {
        Map<String, Object> body = new HashMap<>();
        body.put("productName", "updatedProduct");
        body.put("price", 2000);
        body.put("description", "updatedDescription");
        body.put("brand", "updatedBrand");
        body.put("category", "updatedCategory");
        var response = RestAssuredMockMvc.given().log().all()
                .body(body).contentType(MediaType.APPLICATION_JSON)
                .put("/products/" + productId)
                .then().log().all()
                .extract();
        return response;
    }

    public static void 스토어가_존재_하지_않는_예외_결과_검증한다(ExtractableResponse<MockMvcResponse> response) {
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("error-message"))
                        .isEqualTo(
                                ErrorMessage.STORE_NOT_FOUND_ERROR.getMessage()
                        )
        );
    }

    public static void 존재하지_않는_상품_예외_결과_검증한다(ExtractableResponse<MockMvcResponse> response) {
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("error-message"))
                        .isEqualTo(
                                ErrorMessage.PRODUCT_NOT_FOUND_ERROR.getMessage()
                        )
        );
    }

    public static void 상품_삭제_성공_결과를_검증한다(ExtractableResponse<MockMvcResponse> response) {
        assertThat(response.statusCode()).isEqualTo(204);
    }

    public static ExtractableResponse<MockMvcResponse> 상품을_삭제한다(String productId) {
        return RestAssuredMockMvc.given().log().all()
                .when().delete("/products/" + productId)
                .then().log().all()
                .extract();
    }

}
