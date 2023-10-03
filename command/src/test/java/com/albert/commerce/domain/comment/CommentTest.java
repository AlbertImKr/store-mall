package com.albert.commerce.domain.comment;

import static org.assertj.core.api.Assertions.assertThat;

import com.albert.commerce.domain.DomainFixture;
import com.albert.commerce.domain.event.DomainEvent;
import com.albert.commerce.domain.event.Events;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.UserId;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommentTest {

    @DisplayName("댓글 생성시 이벤트가 발행한다")
    @Test
    void when_create_comment_then_publish_event() {
        // given
        CommentId commentId = DomainFixture.getCommentId();
        ProductId productId = DomainFixture.getProductId("1");
        StoreId storeId = DomainFixture.getStoreId();
        UserId userId = DomainFixture.getUserId();
        String commentDetail = DomainFixture.COMMENT_DETAIL;
        CommentId parentCommentId = DomainFixture.getParentCommentId();
        LocalDateTime createdTime = DomainFixture.getCreatedTime();
        Events.clear();

        // when
        Comment.from(
                commentId,
                productId,
                storeId,
                userId,
                commentDetail,
                parentCommentId,
                createdTime
        );

        // then
        checkCommentPostedEvent(commentId, productId, storeId, userId, commentDetail, parentCommentId, createdTime);
    }

    @DisplayName("댓글 업로드 시 이벤트가 발생한다")
    @Test
    void when_update_comment_then_publish_event() {
        // given
        String commentDetail = DomainFixture.COMMENT_DETAIL;
        LocalDateTime updatedTime = DomainFixture.getUpdatedTime();
        Comment comment = getComment(commentDetail);
        Events.clear();

        // when
        comment.update(commentDetail, updatedTime);

        // then
        checkCommentUpdatedEvent(comment, commentDetail, updatedTime);
    }

    @DisplayName("댓글 삭제 시 이벤트가 발새한다")
    @Test
    void when_delete_comment_then_publish_event() {
        // given
        Comment comment = getComment(DomainFixture.COMMENT_DETAIL);
        LocalDateTime updatedTime = DomainFixture.getUpdatedTime();
        Events.clear();

        // when
        comment.delete(updatedTime);

        // then
        checkCommentDeletedEvent(comment, updatedTime);
    }

    private static void checkCommentDeletedEvent(Comment comment, LocalDateTime updatedTime) {
        List<DomainEvent> events = Events.getEvents();
        DomainEvent domainEvent = events.get(0);
        Assertions.assertAll(
                () -> assertThat(domainEvent.getClass()).isEqualTo(CommentDeletedEvent.class),
                () -> assertThat(((CommentDeletedEvent) domainEvent).getCommentId()).isEqualTo(comment.getCommentId()),
                () -> assertThat(((CommentDeletedEvent) domainEvent).getUpdatedTime()).isEqualTo(updatedTime)
        );
    }

    private static void checkCommentUpdatedEvent(Comment comment, String commentDetail, LocalDateTime updatedTime) {
        List<DomainEvent> events = Events.getEvents();
        DomainEvent domainEvent = events.get(0);
        Assertions.assertAll(
                () -> assertThat(domainEvent.getClass()).isEqualTo(CommentUpdatedEvent.class),
                () -> assertThat(((CommentUpdatedEvent) domainEvent).getCommentId()).isEqualTo(comment.getCommentId()),
                () -> assertThat(((CommentUpdatedEvent) domainEvent).getDetail()).isEqualTo(commentDetail),
                () -> assertThat(((CommentUpdatedEvent) domainEvent).getUpdatedTime()).isEqualTo(updatedTime)
        );
    }

    private static Comment getComment(String commentDetail) {
        CommentId commentId = DomainFixture.getCommentId();
        ProductId productId = DomainFixture.getProductId("1");
        StoreId storeId = DomainFixture.getStoreId();
        UserId userId = DomainFixture.getUserId();
        CommentId parentCommentId = DomainFixture.getParentCommentId();
        LocalDateTime createdTime = DomainFixture.getCreatedTime();
        Events.clear();
        Comment comment = Comment.from(
                commentId,
                productId,
                storeId,
                userId,
                commentDetail,
                parentCommentId,
                createdTime
        );
        return comment;
    }

    private static void checkCommentPostedEvent(CommentId commentId, ProductId productId, StoreId storeId,
            UserId userId,
            String commentDetail, CommentId parentCommentId, LocalDateTime createdTime) {
        List<DomainEvent> events = Events.getEvents();
        DomainEvent domainEvent = events.get(0);
        Assertions.assertAll(
                () -> assertThat(domainEvent.getClass()).isEqualTo(CommentPostedEvent.class),
                () -> assertThat(((CommentPostedEvent) domainEvent).getCommentId()).isEqualTo(commentId),
                () -> assertThat(((CommentPostedEvent) domainEvent).getProductId()).isEqualTo(productId),
                () -> assertThat(((CommentPostedEvent) domainEvent).getStoreId()).isEqualTo(storeId),
                () -> assertThat(((CommentPostedEvent) domainEvent).getUserId()).isEqualTo(userId),
                () -> assertThat(((CommentPostedEvent) domainEvent).getDetail()).isEqualTo(commentDetail),
                () -> assertThat(((CommentPostedEvent) domainEvent).getParentCommentId()).isEqualTo(parentCommentId),
                () -> assertThat(((CommentPostedEvent) domainEvent).getCreatedTime()).isEqualTo(createdTime)
        );
    }
}
