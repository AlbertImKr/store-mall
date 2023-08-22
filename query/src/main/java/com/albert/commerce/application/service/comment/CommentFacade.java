package com.albert.commerce.application.service.comment;

import com.albert.commerce.application.service.comment.dto.CommentDeletedEvent;
import com.albert.commerce.application.service.comment.dto.CommentPostedEvent;
import com.albert.commerce.application.service.comment.dto.CommentUpdatedEvent;
import com.albert.commerce.application.service.product.ProductFacade;
import com.albert.commerce.application.service.store.StoreFacade;
import com.albert.commerce.application.service.user.UserFacade;
import com.albert.commerce.domain.comment.Comment;
import com.albert.commerce.domain.comment.CommentDao;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.user.User;
import com.albert.commerce.exception.error.CommentNotFoundException;
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
    public String post(CommentPostedEvent commentPostedEvent) {
        var product = productFacade.getProductByProductId(commentPostedEvent.productId());
        var user = userFacade.getUserById(commentPostedEvent.userId());
        var store = storeFacade.getStoreById(commentPostedEvent.storeId());
        var comment = toComment(commentPostedEvent, product, user, store);
        return commentDao.save(comment)
                .getCommentId()
                .getValue();
    }

    @Transactional
    public void update(CommentUpdatedEvent commentUpdatedEvent) {
        var comment = commentDao.findById(commentUpdatedEvent.commentId())
                .orElseThrow(CommentNotFoundException::new);
        comment.update(commentUpdatedEvent.detail(), commentUpdatedEvent.updatedTime());
    }

    @Transactional
    public void delete(CommentDeletedEvent commentDeletedEvent) {
        var comment = commentDao.findById(commentDeletedEvent.commentId())
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
