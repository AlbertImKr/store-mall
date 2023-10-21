package com.albert.commerce.adapter.in.messaging.listener.domainevent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.albert.commerce.adapter.in.messaging.listener.domainevent.dto.CommentDeletedEvent;
import com.albert.commerce.adapter.in.messaging.listener.domainevent.dto.CommentPostedEvent;
import com.albert.commerce.adapter.in.messaging.listener.domainevent.dto.CommentUpdatedEvent;
import com.albert.commerce.adapter.out.persistence.imports.CommentJpaRepository;
import com.albert.commerce.adapter.out.persistence.imports.ProductJpaRepository;
import com.albert.commerce.adapter.out.persistence.imports.StoreJpaRepository;
import com.albert.commerce.adapter.out.persistence.imports.UserJpaRepository;
import com.albert.commerce.application.service.exception.error.CommentNotFoundException;
import com.albert.commerce.application.service.exception.error.ProductNotFoundException;
import com.albert.commerce.application.service.exception.error.StoreNotFoundException;
import com.albert.commerce.application.service.exception.error.UserNotFoundException;
import com.albert.commerce.domain.comment.Comment;
import com.albert.commerce.util.Fixture;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("댓글 도메인 이벤트 리스너 테스트")
@ExtendWith(MockitoExtension.class)
class CommentDomainEventListenerTest {

    @InjectMocks
    CommentDomainEventListener commentDomainEventListener;
    @Mock
    CommentJpaRepository commentJpaRepository;
    @Mock
    ProductJpaRepository productJpaRepository;
    @Mock
    StoreJpaRepository storeJpaRepository;
    @Mock
    UserJpaRepository userJpaRepository;


    @DisplayName("댓글 등록 이벤트를 처리시 유저가 존재하지 않으면 예외를 발생시킨다.")
    @Test
    void when_handle_comment_posted_event_if_user_not_exists_then_throw_exception() {
        // given
        CommentPostedEvent commentPostedEvent = Fixture.commentPostedEvent();
        given(userJpaRepository.findById(commentPostedEvent.userId()))
                .willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> commentDomainEventListener.handleCommentPostedCommand(commentPostedEvent))
                .isInstanceOf(UserNotFoundException.class);
    }

    @DisplayName("댓글 등록 이벤트를 처리시 유저가 존재하는데 스토어가 존재하지 않으면 예외를 발생시킨다.")
    @Test
    void when_handle_comment_posted_event_if_user_exists_but_store_not_exists_then_throw_exception() {
        // given
        CommentPostedEvent commentPostedEvent = Fixture.commentPostedEvent();
        given(userJpaRepository.findById(commentPostedEvent.userId()))
                .willReturn(Optional.of(Fixture.user()));
        given(storeJpaRepository.findById(commentPostedEvent.storeId()))
                .willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> commentDomainEventListener.handleCommentPostedCommand(commentPostedEvent))
                .isInstanceOf(StoreNotFoundException.class);
    }

    @DisplayName("댓글 등록 이벤트를 처리시 유저와 스토어가 존재하는데 상품이 존재하지 않으면 예외를 발생시킨다.")
    @Test
    void when_handle_comment_posted_event_if_user_and_store_exists_but_product_not_exists_then_throw_exception() {
        // given
        CommentPostedEvent commentPostedEvent = Fixture.commentPostedEvent();
        given(userJpaRepository.findById(commentPostedEvent.userId()))
                .willReturn(Optional.of(Fixture.user()));
        given(storeJpaRepository.findById(commentPostedEvent.storeId()))
                .willReturn(Optional.of(Fixture.store()));
        given(productJpaRepository.findById(commentPostedEvent.productId()))
                .willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> commentDomainEventListener.handleCommentPostedCommand(commentPostedEvent))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("댓글 등록 이벤트를 처리시 유저와 스토어, 상품이 존재하면 댓글을 등록한다.")
    @Test
    void when_handle_comment_posted_event_if_user_and_store_and_product_exists_then_save_comment() {
        // given
        CommentPostedEvent commentPostedEvent = Fixture.commentPostedEvent();
        given(userJpaRepository.findById(commentPostedEvent.userId()))
                .willReturn(Optional.of(Fixture.user()));
        given(storeJpaRepository.findById(commentPostedEvent.storeId()))
                .willReturn(Optional.of(Fixture.store()));
        given(productJpaRepository.findById(commentPostedEvent.productId()))
                .willReturn(Optional.of(Fixture.product()));

        // when
        commentDomainEventListener.handleCommentPostedCommand(commentPostedEvent);

        // then
        verify(commentJpaRepository).save(any(Comment.class));
    }

    @DisplayName("댓글 수정 이벤트를 처리시 댓글이 존재하지 않으면 예외를 발생시킨다.")
    @Test
    void when_handle_comment_updated_event_if_comment_not_exists_then_throw_exception() {
        // given
        CommentUpdatedEvent commentUpdatedEvent = Fixture.commentUpdatedEvent();
        given(commentJpaRepository.findById(commentUpdatedEvent.commentId()))
                .willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> commentDomainEventListener.handleCommentUpdatedEvent(commentUpdatedEvent))
                .isInstanceOf(CommentNotFoundException.class);
    }

    @DisplayName("댓글 수정 이벤트를 처리시 댓글이 존재하면 댓글을 수정한다.")
    @Test
    void when_handle_comment_updated_event_if_comment_exists_then_update_comment() {
        // given
        CommentUpdatedEvent commentUpdatedEvent = Fixture.commentUpdatedEvent();
        Comment comment = Fixture.comment();
        given(commentJpaRepository.findById(commentUpdatedEvent.commentId()))
                .willReturn(Optional.of(comment));

        // when
        commentDomainEventListener.handleCommentUpdatedEvent(commentUpdatedEvent);

        // then
        Assertions.assertAll(
                () -> assertThat(comment.getDetail()).isEqualTo(Fixture.newDetail()),
                () -> assertThat(comment.getUpdatedTime()).isEqualTo(Fixture.newUpdatedTime())
        );
    }

    @DisplayName("댓글 삭제 이벤트를 처리시 댓글이 존재하지 않으면 예외를 발생시킨다.")
    @Test
    void when_handle_comment_deleted_event_if_comment_not_exists_then_throw_exception() {
        // given
        CommentDeletedEvent commentDeletedEvent = Fixture.commentDeletedEvent();
        given(commentJpaRepository.findById(commentDeletedEvent.commentId()))
                .willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> commentDomainEventListener.handleCommentDeletedEvent(commentDeletedEvent))
                .isInstanceOf(CommentNotFoundException.class);
    }

    @DisplayName("댓글 삭제 이벤트를 처리시 댓글이 존재하면 댓글을 삭제한다.")
    @Test
    void when_handle_comment_deleted_event_if_comment_exists_then_delete_comment() {
        // given
        CommentDeletedEvent commentDeletedEvent = Fixture.commentDeletedEvent();
        Comment comment = Fixture.comment();
        given(commentJpaRepository.findById(commentDeletedEvent.commentId()))
                .willReturn(Optional.of(comment));

        // when
        commentDomainEventListener.handleCommentDeletedEvent(commentDeletedEvent);

        // then
        Assertions.assertAll(
                () -> assertThat(comment.getUpdatedTime()).isEqualTo(Fixture.newUpdatedTime()),
                () -> assertThat(comment.getUserId()).isNull(),
                () -> assertThat(comment.getDetail()).isNull()
        );
    }
}
