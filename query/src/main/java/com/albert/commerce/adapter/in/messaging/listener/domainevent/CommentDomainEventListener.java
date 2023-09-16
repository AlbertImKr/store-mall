package com.albert.commerce.adapter.in.messaging.listener.domainevent;

import com.albert.commerce.adapter.in.messaging.listener.domainevent.dto.CommentDeletedEvent;
import com.albert.commerce.adapter.in.messaging.listener.domainevent.dto.CommentPostedEvent;
import com.albert.commerce.adapter.in.messaging.listener.domainevent.dto.CommentUpdatedEvent;
import com.albert.commerce.adapter.out.persistence.imports.CommentJpaRepository;
import com.albert.commerce.adapter.out.persistence.imports.ProductJpaRepository;
import com.albert.commerce.adapter.out.persistence.imports.StoreJpaRepository;
import com.albert.commerce.adapter.out.persistence.imports.UserJpaRepository;
import com.albert.commerce.domain.comment.Comment;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.user.User;
import com.albert.commerce.exception.error.CommentNotFoundException;
import com.albert.commerce.exception.error.ProductNotFoundException;
import com.albert.commerce.exception.error.StoreNotFoundException;
import com.albert.commerce.exception.error.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentDomainEventListener {

    private final CommentJpaRepository commentJpaRepository;
    private final ProductJpaRepository productJpaRepository;
    private final StoreJpaRepository storeJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @KafkaListener(topics = "CommentPostedEvent")
    public void handleCommentPostedCommand(CommentPostedEvent commentPostedEvent) {
        var product = productJpaRepository.findById(commentPostedEvent.productId())
                .orElseThrow(ProductNotFoundException::new);
        var user = userJpaRepository.findById(commentPostedEvent.userId())
                .orElseThrow(UserNotFoundException::new);
        var store = storeJpaRepository.findById(commentPostedEvent.storeId())
                .orElseThrow(StoreNotFoundException::new);
        var comment = toComment(commentPostedEvent, product, user, store);
        commentJpaRepository.save(comment);
    }

    @Transactional
    @CacheEvict(value = "comment", key = "#commentUpdatedEvent.commentId().value")
    @KafkaListener(topics = "CommentUpdatedEvent")
    public void handleCommentUpdatedEvent(CommentUpdatedEvent commentUpdatedEvent) {
        var comment = commentJpaRepository.findById(commentUpdatedEvent.commentId())
                .orElseThrow(CommentNotFoundException::new);
        comment.update(commentUpdatedEvent.detail(), commentUpdatedEvent.updatedTime());
    }

    @Transactional
    @CacheEvict(value = "comment", key = "#commentDeletedEvent.commentId().value")
    @KafkaListener(topics = "CommentDeletedEvent")
    public void handleCommentDeletedEvent(CommentDeletedEvent commentDeletedEvent) {
        var comment = commentJpaRepository.findById(commentDeletedEvent.commentId())
                .orElseThrow(CommentNotFoundException::new);
        comment.delete(commentDeletedEvent.updatedTime());
    }

    private static Comment toComment(CommentPostedEvent commentPostedEvent, Product product,
            User user, Store store) {
        return Comment.builder()
                .commentId(commentPostedEvent.commentId())
                .productId(commentPostedEvent.productId())
                .productName(product.getProductName())
                .userId(commentPostedEvent.userId())
                .nickname(user.getNickname())
                .storeId(commentPostedEvent.storeId())
                .storeName(store.getStoreName())
                .detail(commentPostedEvent.detail())
                .parentCommentId(commentPostedEvent.parentCommentId())
                .createdTime(commentPostedEvent.createdTime())
                .updatedTime(commentPostedEvent.updatedTime())
                .build();
    }
}
