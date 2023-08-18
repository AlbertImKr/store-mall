package com.albert.commerce.query.application.comment;

import com.albert.commerce.query.application.comment.dto.CommentCreatedEvent;
import com.albert.commerce.query.application.comment.dto.CommentDeletedEvent;
import com.albert.commerce.query.application.comment.dto.CommentUpdatedEvent;
import com.albert.commerce.query.application.product.ProductFacade;
import com.albert.commerce.query.application.store.StoreFacade;
import com.albert.commerce.query.application.user.UserFacade;
import com.albert.commerce.query.common.exception.CommentNotFoundException;
import com.albert.commerce.query.domain.comment.CommentDao;
import com.albert.commerce.query.domain.comment.CommentData;
import com.albert.commerce.query.domain.product.ProductData;
import com.albert.commerce.query.domain.store.StoreData;
import com.albert.commerce.query.domain.user.UserData;
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
    public CommentData create(CommentCreatedEvent commentCreatedEvent) {
        ProductData product = productFacade.getByProductId(commentCreatedEvent.productId());
        UserData user = userFacade.getUserById(commentCreatedEvent.userId());
        StoreData store = storeFacade.getStoreById(commentCreatedEvent.storeId());
        CommentData comment = toComment(commentCreatedEvent, product, user, store);
        return commentDao.save(comment);
    }

    @Transactional
    public void update(CommentUpdatedEvent commentUpdatedEvent) {
        CommentData commentData = commentDao.findById(commentUpdatedEvent.commentId())
                .orElseThrow(CommentNotFoundException::new);
        commentData.update(commentUpdatedEvent.detail(), commentUpdatedEvent.updatedTime());
    }

    @Transactional
    public void delete(CommentDeletedEvent commentDeletedEvent) {
        CommentData commentData = commentDao.findById(commentDeletedEvent.commentId())
                .orElseThrow(CommentNotFoundException::new);
        commentData.delete(commentDeletedEvent.updatedTime());
    }

    private static CommentData toComment(CommentCreatedEvent commentCreatedEvent, ProductData product,
            UserData user, StoreData store) {
        return CommentData.builder()
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
