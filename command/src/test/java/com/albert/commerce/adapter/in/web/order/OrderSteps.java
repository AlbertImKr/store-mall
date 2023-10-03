package com.albert.commerce.adapter.in.web.order;

import static com.albert.commerce.adapter.in.web.product.ProductSteps.상품을_등록한다;
import static org.assertj.core.api.Assertions.assertThat;

import com.albert.commerce.application.service.exception.error.ErrorMessage;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;

public class OrderSteps {

    public static void 성공적으로_주문한_결과를_검증한다(ExtractableResponse<MockMvcResponse> response) {
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(201),
                () -> assertThat(response.jsonPath().getString("orderId")).isNotNull()
        );
    }

    public static String 주문을_하고_주문아이디를_반환한다(List<String> productsId, String storeId) {
        return 주문을_한다(productsId, storeId).jsonPath().getString("orderId");
    }

    public static ExtractableResponse<MockMvcResponse> 주문을_한다(
            List<String> productsId, String storeId) {
        HashMap<String, Integer> productsIdAndQuantity = getProductsIdAndQuantity(productsId);
        Map<String, Object> body = new HashMap<>();
        body.put("productsIdAndQuantity", productsIdAndQuantity);
        body.put("storeId", storeId);
        return RestAssuredMockMvc
                .given().log().all()
                .body(body).contentType(MediaType.APPLICATION_JSON)
                .when().post("/orders")
                .then().log().all()
                .extract();
    }


    public static void 성공적으로_주문_취소_결과_검증한다(ExtractableResponse<MockMvcResponse> response) {
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(204)
        );
    }

    public static void 주문이_존재하지_않은_결과_검증한다(ExtractableResponse<MockMvcResponse> response) {
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(400),
                () -> assertThat(response.jsonPath().getString("error-message")).isEqualTo(
                        ErrorMessage.ORDER_NOT_FOUND_ERROR.getMessage()
                )
        );
    }

    public static ExtractableResponse<MockMvcResponse> 주문을_취소한다(String orderId) {
        Map<String, String> body = new HashMap<>();
        body.put("description", "주문을 취소합니다.");
        return RestAssuredMockMvc
                .given().log().all()
                .body(body).contentType(MediaType.APPLICATION_JSON)
                .when().delete("/orders/{orderId}", orderId)
                .then().log().all()
                .extract();
    }

    public static List<String> 여러_상품을_등록한다(int 개수) {
        List<String> productsId = new ArrayList<>();
        for (int i = 0; i < 개수; i++) {
            String productId = 상품을_등록한다().jsonPath().getString("productId");
            productsId.add(productId);
        }
        return productsId;
    }

    public static HashMap<String, Integer> getProductsIdAndQuantity(List<String> productIds) {
        HashMap<String, Integer> productsIdAndQuantity = new HashMap<>();
        for (int i = 0; i < productIds.size(); i++) {
            productsIdAndQuantity.put(productIds.get(i), i + 1);
        }
        return productsIdAndQuantity;
    }
}
