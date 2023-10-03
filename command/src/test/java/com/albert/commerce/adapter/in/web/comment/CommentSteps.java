package com.albert.commerce.adapter.in.web.comment;

import static org.assertj.core.api.Assertions.assertThat;

import com.albert.commerce.application.service.exception.error.ErrorMessage;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.AbstractIntegerAssert;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;

public class CommentSteps {

    public static ExtractableResponse<MockMvcResponse> 댓글을_포스트한다(String productId,
            String storeId, String parentCommentId) {
        Map<String, String> body = new HashMap<>();
        body.put("productId", productId);
        body.put("storeId", storeId);
        body.put("detail", "댓글 내용");
        body.put("parentCommentId", parentCommentId);
        return RestAssuredMockMvc
                .given().log().all().body(body).contentType(MediaType.APPLICATION_JSON)
                .when().post("/comments")
                .then().log().all()
                .extract();
    }

    public static void 댓글_포스트_성공_결과_검증(ExtractableResponse<MockMvcResponse> response) {
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(201),
                () -> assertThat(response.jsonPath().getString("commentId")).isNotNull()
        );
    }

    public static void 댓글_존재하지_않은_상태_결과_검증한다(ExtractableResponse<MockMvcResponse> response) {
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(400),
                () -> assertThat(response.jsonPath().getString("error-message")).isEqualTo(
                        ErrorMessage.COMMENT_NOT_FOUND_ERROR.getMessage())
        );
    }

    public static String 댓글을_포스트하고_댓글_아이디를_반환한다(String productId, String storeId) {
        return 댓글을_포스트한다(productId, storeId, null).jsonPath().getString("commentId");
    }

    public static ExtractableResponse<MockMvcResponse> 댓글을_수정_한다(String commentId) {
        Map<String, String> body = new HashMap<>();
        body.put("detail", "수정된 댓글");
        return RestAssuredMockMvc
                .given().log().all()
                .body(body).contentType(MediaType.APPLICATION_JSON)
                .when().put("/comments/{commentId}", commentId)
                .then().log().all()
                .extract();
    }

    public static AbstractIntegerAssert<?> 댓글_수정_성공_결과_검증(ExtractableResponse<MockMvcResponse> response) {
        return assertThat(response.statusCode()).isEqualTo(204);
    }

    public static void 댓글_삭제_성공_결과_검증(ExtractableResponse<MockMvcResponse> response) {
        assertThat(response.statusCode()).isEqualTo(204);
    }

    public static ExtractableResponse<MockMvcResponse> 댓글을_삭제_한다(String commentId) {
        return RestAssuredMockMvc.given().log().all()
                .when().delete("/comments/{commentId}", commentId)
                .then().log().all()
                .extract();
    }
}
