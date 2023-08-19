package com.albert.commerce.application.service.comment;

import com.albert.commerce.application.service.comment.dto.CommentCreatedEvent;
import com.albert.commerce.application.service.comment.dto.CommentDeletedEvent;
import com.albert.commerce.application.service.comment.dto.CommentUpdatedEvent;
import com.albert.commerce.application.service.product.ProductFacade;
import com.albert.commerce.application.service.store.StoreFacade;
import com.albert.commerce.application.service.user.UserFacade;
import com.albert.commerce.domain.comment.Comment;
import com.albert.commerce.domain.comment.CommentDao;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.user.User;
import com.albert.commerce.exception.CommentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class CommentFacade {

    private final CommentDao commentDao;
    private final ProductFacade productFacade;
    private final StoreFacade storeFacade;
    private final UserFacade userFacade;


    @Transactional
    public Comment create(CommentCreatedEvent commentCreatedEvent) {
        Product product = productFacade.getByProductId(commentCreatedEvent.productId());
        User user = userFacade.getUserById(commentCreatedEvent.userId());
        Store store = storeFacade.getStoreById(commentCreatedEvent.storeId());
        Comment comment = toComment(commentCreatedEvent, product, user, store);
        return commentDao.save(comment);
    }

    @Transactional
    public void update(CommentUpdatedEvent commentUpdatedEvent) {
        Comment comment = commentDao.findById(commentUpdatedEvent.commentId())
                .orElseThrow(CommentNotFoundException::new);
        comment.update(commentUpdatedEvent.detail(), commentUpdatedEvent.updatedTime());
    }

    @Transactional
    public void delete(CommentDeletedEvent commentDeletedEvent) {
        Comment comment = commentDao.findById(commentDeletedEvent.commentId())
                .orElseThrow(CommentNotFoundException::new);
        comment.delete(commentDeletedEvent.updatedTime());
    }

    private static Comment toComment(CommentCreatedEvent commentCreatedEvent, Product product,
            User user, Store store) {
        return Comment.builder()
                .commentId(commentCreatedEvent.commentId())
                .productId(commentCreatedEvent.productId())
                .productName(product.getProductName())
                .userId(commentCreatedEvent.userId())
                .nickname(user.getNickname())
                .storeId(commentCreatedEvent.storeId())
                .storeName(store.getStoreName())
                .detail(commentCreatedEvent.detail())
                .parentCommentId(commentCreatedEvent.parentCommentId())
                .createdTime(commentCreatedEvent.createdTime())
                .updatedTime(commentCreatedEvent.updatedTime())
                .build();
    }
}
