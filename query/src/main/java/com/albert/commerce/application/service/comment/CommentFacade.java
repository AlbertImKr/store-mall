package com.albert.commerce.application.service.comment;

import com.albert.commerce.application.service.comment.dto.CommentDeletedEvent;
import com.albert.commerce.application.service.comment.dto.CommentPostedCommand;
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
    public Comment post(CommentPostedCommand commentPostedCommand) {
        var product = productFacade.getProductByProductId(commentPostedCommand.productId());
        var user = userFacade.getUserById(commentPostedCommand.userId());
        var store = storeFacade.getStoreById(commentPostedCommand.storeId());
        var comment = toComment(commentPostedCommand, product, user, store);
        return commentDao.save(comment);
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

    private static Comment toComment(CommentPostedCommand commentPostedCommand, Product product,
            User user, Store store) {
        return Comment.builder()
                .commentId(commentPostedCommand.commentId())
                .productId(commentPostedCommand.productId())
                .productName(product.getProductName())
                .userId(commentPostedCommand.userId())
                .nickname(user.getNickname())
                .storeId(commentPostedCommand.storeId())
                .storeName(store.getStoreName())
                .detail(commentPostedCommand.detail())
                .parentCommentId(commentPostedCommand.parentCommentId())
                .createdTime(commentPostedCommand.createdTime())
                .updatedTime(commentPostedCommand.updatedTime())
                .build();
    }
}
