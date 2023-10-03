package com.albert.commerce.adapter.in.web.comment;

import static com.albert.commerce.adapter.in.web.comment.CommentSteps.댓글_삭제_성공_결과_검증;
import static com.albert.commerce.adapter.in.web.comment.CommentSteps.댓글_수정_성공_결과_검증;
import static com.albert.commerce.adapter.in.web.comment.CommentSteps.댓글_존재하지_않은_상태_결과_검증한다;
import static com.albert.commerce.adapter.in.web.comment.CommentSteps.댓글_포스트_성공_결과_검증;
import static com.albert.commerce.adapter.in.web.comment.CommentSteps.댓글을_삭제_한다;
import static com.albert.commerce.adapter.in.web.comment.CommentSteps.댓글을_수정_한다;
import static com.albert.commerce.adapter.in.web.comment.CommentSteps.댓글을_포스트하고_댓글_아이디를_반환한다;
import static com.albert.commerce.adapter.in.web.comment.CommentSteps.댓글을_포스트한다;
import static com.albert.commerce.adapter.in.web.product.ProductSteps.상품을_등록하고_상품_아이디를_반환한다;
import static com.albert.commerce.adapter.in.web.product.ProductSteps.존재하지_않는_상품_예외_결과_검증한다;
import static com.albert.commerce.adapter.in.web.store.StoreSteps.my_스토어_존재하지_않은_결과_검증;
import static com.albert.commerce.adapter.in.web.store.StoreSteps.my_스토어를_등록하고_등록된_스토어_아이디를_반환한다;

import com.albert.commerce.adapter.in.web.AcceptanceFixture;
import com.albert.commerce.adapter.in.web.AcceptanceTest;
import com.albert.commerce.adapter.in.web.security.WithMockOAuth2User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@WithMockOAuth2User
class CommentAcceptanceTest extends AcceptanceTest {

    @DisplayName("댓글 추가 요청이 성공하면 댓글 아이디를 반환한다")
    @Test
    void when_post_comment_then_return_comment_id() {
        // given
        String storeId = my_스토어를_등록하고_등록된_스토어_아이디를_반환한다();
        String productId = 상품을_등록하고_상품_아이디를_반환한다();

        // when
        var response = 댓글을_포스트한다(productId, storeId, null);

        // then
        댓글_포스트_성공_결과_검증(response);
    }

    @DisplayName("댓글 추가 요청 시 상품 아이디가 없으면 에러를 반환한다")
    @Test
    void when_post_comment_with_not_exists_product_id_then_return_error() {
        // given
        String storeId = my_스토어를_등록하고_등록된_스토어_아이디를_반환한다();

        // when
        var response = 댓글을_포스트한다(AcceptanceFixture.NOT_EXISTS_PRODUCT_ID, storeId, null);

        // then
        존재하지_않는_상품_예외_결과_검증한다(response);
    }

    @DisplayName("댓글 추가 요청 시 스토어 아이디가 없으면 에러를 반환한다")
    @Test
    void when_post_comment_with_not_exists_store_id_then_return_error() {
        // given
        my_스토어를_등록하고_등록된_스토어_아이디를_반환한다();
        String productId = 상품을_등록하고_상품_아이디를_반환한다();

        // when
        var response = 댓글을_포스트한다(productId, AcceptanceFixture.NOT_EXISTS_STORE_ID, null);

        // then
        my_스토어_존재하지_않은_결과_검증(response);
    }


    @DisplayName("댓글 추가 요청 시 상위 댓글이 잘못 입력 했으면 에러를 반환한다")
    @Test
    void when_post_comment_with_not_exists_parent_comment_id_then_return_error() {
        // given
        String storeId = my_스토어를_등록하고_등록된_스토어_아이디를_반환한다();
        String productId = 상품을_등록하고_상품_아이디를_반환한다();

        // when
        var response = 댓글을_포스트한다(productId, storeId, AcceptanceFixture.NOT_EXISTS_COMMENT_ID);

        // then
        댓글_존재하지_않은_상태_결과_검증한다(response);
    }

    @DisplayName("댓글 수정 요청이 성공하면 204 코드를 반환한다")
    @Test
    void when_update_comment_then_return_204_code() {
        // given
        String storeId = my_스토어를_등록하고_등록된_스토어_아이디를_반환한다();
        String productId = 상품을_등록하고_상품_아이디를_반환한다();
        var commentId = 댓글을_포스트하고_댓글_아이디를_반환한다(productId, storeId);

        // when
        var response = 댓글을_수정_한다(commentId);

        // then
        댓글_수정_성공_결과_검증(response);
    }

    @DisplayName("댓글 수정 요청 시 해당 댓글이 존재하지 않으면 에러를 반환한다")
    @Test
    void when_update_comment_with_not_exists_comment_id_then_return_error() {
        // when
        var response = 댓글을_수정_한다(AcceptanceFixture.NOT_EXISTS_COMMENT_ID);

        // then
        댓글_존재하지_않은_상태_결과_검증한다(response);
    }

    @DisplayName("댓글 삭제 요청이 성공하면 204 코드를 반환한다")
    @Test
    void when_delete_comment_then_return_204_code() {
        // given
        String storeId = my_스토어를_등록하고_등록된_스토어_아이디를_반환한다();
        String productId = 상품을_등록하고_상품_아이디를_반환한다();
        var commentId = 댓글을_포스트하고_댓글_아이디를_반환한다(productId, storeId);

        // when
        var response = 댓글을_삭제_한다(commentId);

        // then
        댓글_삭제_성공_결과_검증(response);
    }

    @DisplayName("댓글 삭제 요청 시 해당 댓글이 존재하지 않으면 에러를 반환한다")
    @Test
    void when_delete_comment_with_not_exists_comment_id_then_return_error() {
        // when
        var response = 댓글을_삭제_한다(AcceptanceFixture.NOT_EXISTS_COMMENT_ID);

        // then
        댓글_존재하지_않은_상태_결과_검증한다(response);
    }
}
