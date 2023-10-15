package com.albert.commerce.adapter.in.web.order;

import static com.albert.commerce.adapter.in.web.order.OrderSteps.성공적으로_주문_취소_결과_검증한다;
import static com.albert.commerce.adapter.in.web.order.OrderSteps.성공적으로_주문한_결과를_검증한다;
import static com.albert.commerce.adapter.in.web.order.OrderSteps.여러_상품을_등록한다;
import static com.albert.commerce.adapter.in.web.order.OrderSteps.주문을_취소한다;
import static com.albert.commerce.adapter.in.web.order.OrderSteps.주문을_하고_주문아이디를_반환한다;
import static com.albert.commerce.adapter.in.web.order.OrderSteps.주문을_한다;
import static com.albert.commerce.adapter.in.web.order.OrderSteps.주문이_존재하지_않은_결과_검증한다;
import static com.albert.commerce.adapter.in.web.product.ProductSteps.존재하지_않는_상품_예외_결과_검증한다;
import static com.albert.commerce.adapter.in.web.store.StoreSteps.my_스토어_존재하지_않은_결과_검증;
import static com.albert.commerce.adapter.in.web.store.StoreSteps.my_스토어를_등록하고_등록된_스토어_아이디를_반환한다;
import static com.albert.commerce.adapter.in.web.store.StoreSteps.my_스토어를_등록한다;

import com.albert.commerce.adapter.in.web.AcceptanceFixture;
import com.albert.commerce.adapter.in.web.AcceptanceTest;
import com.albert.commerce.adapter.in.web.security.WithMockOAuth2User;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@WithMockOAuth2User
class OrderAcceptanceTest extends AcceptanceTest {

    @DisplayName("주문을 성공적으로 생성하면 아이디를 반환한다")
    @Test
    void when_place_order_if_success_then_return_id() {
        // given
        var storeId = my_스토어를_등록하고_등록된_스토어_아이디를_반환한다();
        List<String> productIds = 여러_상품을_등록한다(3);

        // when
        var response = 주문을_한다(productIds, storeId);

        // then
        성공적으로_주문한_결과를_검증한다(response);
    }

    @DisplayName("주문 생성 요청 시 스토어가 존재하지 않으면 에러를 반환한다")
    @Test
    void when_place_order_if_store_not_exist_then_return_error() {
        // given
        my_스토어를_등록한다();
        List<String> productIds = 여러_상품을_등록한다(3);

        // when
        var response = 주문을_한다(productIds, AcceptanceFixture.NOT_EXISTS_STORE_ID);

        // then
        my_스토어_존재하지_않은_결과_검증(response);
    }

    @DisplayName("주문 생성 요청 시 상품이 존재하지 않으면 예외가 발생한다")
    @Test
    void when_place_order_if_product_not_exist_then_throw_exception() {
        // given
        String storeId = my_스토어를_등록하고_등록된_스토어_아이디를_반환한다();
        List<String> productIds = 여러_상품을_등록한다(3);
        productIds.add(AcceptanceFixture.NOT_EXISTS_PRODUCT_ID);

        // when
        var response = 주문을_한다(productIds, storeId);

        // then
        존재하지_않는_상품_예외_결과_검증한다(response);
    }

    @DisplayName("주문을 성공적으로 취소하면 응답코드 204를 반환하다")
    @Test
    void when_cancel_order_if_success_then_return_204() {
        // given
        var storeId = my_스토어를_등록하고_등록된_스토어_아이디를_반환한다();
        List<String> productIds = 여러_상품을_등록한다(3);
        var orderId = 주문을_하고_주문아이디를_반환한다(productIds, storeId);

        // when
        var response = 주문을_취소한다(orderId);

        // then
        성공적으로_주문_취소_결과_검증한다(response);
    }

    @DisplayName("주문을 취소 요청 시 주문이 존재하지 않으면 에러를 반환한다")
    @Test
    void when_cancel_order_if_order_not_exist_then_return_error() {
        // given
        var orderId = AcceptanceFixture.NOT_EXISTS_ORDER_ID;

        // when
        var response = 주문을_취소한다(orderId);

        // then
        주문이_존재하지_않은_결과_검증한다(response);
    }
}
