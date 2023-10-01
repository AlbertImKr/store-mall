package com.albert.commerce.adapter.in.web.store;

import static com.albert.commerce.adapter.in.web.store.StoreSteps.my_스토어_등록_결과_검증한다;
import static com.albert.commerce.adapter.in.web.store.StoreSteps.my_스토어_등록_실패_검증;
import static com.albert.commerce.adapter.in.web.store.StoreSteps.my_스토어_삭제_결과_검증;
import static com.albert.commerce.adapter.in.web.store.StoreSteps.my_스토어_업로드_결과_검증;
import static com.albert.commerce.adapter.in.web.store.StoreSteps.my_스토어_존재하지_않은_결과_검증;
import static com.albert.commerce.adapter.in.web.store.StoreSteps.my_스토어를_등록한다;
import static com.albert.commerce.adapter.in.web.store.StoreSteps.my_스토어를_삭제한다;
import static com.albert.commerce.adapter.in.web.store.StoreSteps.my_스토어를_업로드_한다;

import com.albert.commerce.adapter.in.web.AcceptanceTest;
import com.albert.commerce.adapter.in.web.security.WithMockOAuth2User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@WithMockOAuth2User
class StoreAcceptanceTest extends AcceptanceTest {

    @DisplayName("스토어 등록 요청이 성공하면 생성된 storeId를 반환한다")
    @Test
    void register_success() {
        // when
        var response = my_스토어를_등록한다();

        // then
        my_스토어_등록_결과_검증한다(response);
    }

    @DisplayName("스토어 등록 요청을 할 때 이미 스토어가 만들어져 있으면 에러를 반환한다")
    @Test
    void register_failed_by_already_created_store() {
        // given
        my_스토어를_등록한다();

        // when
        var response = my_스토어를_등록한다();

        // then
        my_스토어_등록_실패_검증(response);
    }

    @DisplayName("스토어 정보를 업로드 요청이 성공하면 응답코드 204를 응답한다")
    @Test
    void upload_store_info() {
        // given
        my_스토어를_등록한다();

        // when
        var response = my_스토어를_업로드_한다();

        // then
        my_스토어_업로드_결과_검증(response);
    }

    @DisplayName("스토어 정보를 업로드 요청시 스토어가 존재하지 않으면 에러를 반환하다")
    @Test
    void upload_store_info_failed_by_not_exist_store() {
        // when
        var response = my_스토어를_업로드_한다();

        // then
        my_스토어_존재하지_않은_결과_검증(response);
    }

    @DisplayName("스토어를 삭제 요청이 성공하면 204 응답코드를 반환한다")
    @Test
    void delete_store_success() {
        // given
        my_스토어를_등록한다();

        // when
        var response = my_스토어를_삭제한다();

        // then
        my_스토어_삭제_결과_검증(response);
    }
}
