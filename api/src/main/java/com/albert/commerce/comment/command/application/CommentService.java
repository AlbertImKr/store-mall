package com.albert.commerce.comment.command.application;

import com.albert.commerce.comment.command.domain.Comment;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.comment.command.domain.CommentRepository;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.infra.persistence.ProductNotFoundException;
import com.albert.commerce.product.query.ProductDao;
import com.albert.commerce.store.StoreNotFoundException;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.query.domain.StoreDao;
import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.query.domain.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserDao userDao;
    private final ProductDao productDao;
    private final StoreDao storeDao;

    @Transactional
    public CommentResponse save(CommentRequest commentRequest, String email) {
        User user = userDao.findByEmail(email).orElseThrow(UserNotFoundException::new);
        ProductId productId = ProductId.from(commentRequest.productId());
        checkProductId(productId);
        StoreId storeId = StoreId.from(commentRequest.storeId());
        checkStoreId(storeId);
        CommentId parentCommentId = commentRequest.parentCommentId() == null ?
                null : CommentId.from(
                commentRequest.parentCommentId());
        checkParentCommentId(parentCommentId);
        Comment comment = Comment.builder()
                .commentId(commentRepository.nextId())
                .productId(productId)
                .storeId(storeId)
                .userId(user.getId())
                .detail(commentRequest.detail())
                .parentCommentId(parentCommentId)
                .build();
        Comment savedComment = commentRepository.save(comment);
        return CommentResponse.of(savedComment, user.getNickname());
    }

    private void checkParentCommentId(CommentId parentCommentId) {
        if (parentCommentId != null && !commentRepository.exists(parentCommentId)) {
            throw new CommentNotFoundException();
        }
    }

    private void checkStoreId(StoreId storeId) {
        if (!storeDao.exists(storeId)) {
            throw new StoreNotFoundException();
        }
    }

    private void checkProductId(ProductId productId) {
        if (!productDao.exists(productId)) {
            throw new ProductNotFoundException();
        }
    }
}
