package com.albert.commerce.adapter.in.web.product;

import static com.albert.commerce.adapter.in.web.product.ProductSteps.상품_등록_성공_결과_검증한다;
import static com.albert.commerce.adapter.in.web.product.ProductSteps.상품_삭제_성공_결과를_검증한다;
import static com.albert.commerce.adapter.in.web.product.ProductSteps.상품_업로드_결과를_검증한다;
import static com.albert.commerce.adapter.in.web.product.ProductSteps.상품을_등록한다;
import static com.albert.commerce.adapter.in.web.product.ProductSteps.상품을_삭제한다;
import static com.albert.commerce.adapter.in.web.product.ProductSteps.상품을_업로드한다;
import static com.albert.commerce.adapter.in.web.product.ProductSteps.스토어가_존재_하지_않는_예외_결과_검증한다;
import static com.albert.commerce.adapter.in.web.product.ProductSteps.존재하지_않는_상품_예외_결과_검증한다;
import static com.albert.commerce.adapter.in.web.store.StoreSteps.my_스토어를_등록한다;

import com.albert.commerce.adapter.in.web.AcceptanceTest;
import com.albert.commerce.adapter.in.web.security.WithMockOAuth2User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@WithMockOAuth2User
class ProductAcceptanceTestTest extends AcceptanceTest {

    @DisplayName("상품 등록 요청 성공하면 상품 아이디를 반환하다")
    @Test
    void register_product_success_then_return_product_id() {
        // given
        my_스토어를_등록한다();

        // when
        var response = 상품을_등록한다();

        // then
        상품_등록_성공_결과_검증한다(response);
    }


    @DisplayName("상품 등록 요청 시 my 스토어가 없으면 예외를 던진다.")
    @Test
    void register_product_when_my_store_is_not_exist_then_throw_exception() {
        // when
        var response = 상품을_등록한다();

        // then
        스토어가_존재_하지_않는_예외_결과_검증한다(response);
    }

    @DisplayName("상품 업로드 요청 성공하면 204 코드를 응답한다")
    @Test
    void upload_product_success_then_return_204_code() {
        // given
        my_스토어를_등록한다();
        String productId = 상품을_등록한다().jsonPath().getString("productId");

        // when
        var response = 상품을_업로드한다(productId);

        // then
        상품_업로드_결과를_검증한다(response);
    }

    @DisplayName("상품 업로드 요청 시 스토어가 존재하지 않으면 예외를 던진다")
    @Test
    void upload_product_fail_when_store_is_not_exist_then_throw_exception() {
        // when
        var response = 상품을_업로드한다("not-exist-product-id");

        // then
        스토어가_존재_하지_않는_예외_결과_검증한다(response);
    }

    @DisplayName("상품 업로드 요청 시 productId 가 내 my 스토어에 존재하지 않으면 예외를 던진다")
    @Test
    void upload_p_product_fail_when_product_is_not_exist_in_my_store_then_throw_exception() {
        // given
        my_스토어를_등록한다();

        // when
        var response = 상품을_업로드한다("not-exist-product-id");

        // then
        존재하지_않는_상품_예외_결과_검증한다(response);
    }

    @DisplayName("상품을 삭제 요청이 성공하면 204 응답을 한다")
    @Test
    void delete_product_success_then_return_204_code() {
        // given
        my_스토어를_등록한다();
        String productId = 상품을_등록한다().jsonPath().getString("productId");

        // when
        var response = 상품을_삭제한다(productId);

        // then
        상품_삭제_성공_결과를_검증한다(response);
    }

    @DisplayName("상품을 삭제 요청 시 해당 상품이 my 스토어에 존재하지 않으면 예외를 던진다")
    @Test
    void delete_product_fail_when_product_is_not_exist_in_my_store_then_throw_exception() {
        // given
        my_스토어를_등록한다();

        // when
        var response = 상품을_삭제한다("not-exist-product-id");

        // then
        존재하지_않는_상품_예외_결과_검증한다(response);
    }
}
