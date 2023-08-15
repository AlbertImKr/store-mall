package com.albert.commerce.api.comment.command.application;

import com.albert.commerce.api.comment.command.domain.Comment;
import com.albert.commerce.api.comment.command.domain.CommentId;
import com.albert.commerce.api.comment.command.domain.CommentRepository;
import com.albert.commerce.api.product.command.application.ProductService;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.store.command.application.StoreService;
import com.albert.commerce.api.store.command.domain.StoreId;
import com.albert.commerce.api.user.command.application.UserService;
import com.albert.commerce.api.user.command.domain.UserId;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ProductService productService;
    private final StoreService storeService;

    @Transactional
    public CommentId create(String userEmail, CommentRequest commentRequest) {
        UserId userId = userService.findIdByEmail(userEmail);

        ProductId productId = ProductId.from(commentRequest.productId());
        productService.checkId(productId);

        StoreId storeId = StoreId.from(commentRequest.storeId());
        storeService.checkId(storeId);

        CommentId parentCommentId = getParentCommentId(commentRequest.parentCommentId());
        Comment comment = toComment(commentRequest, userId, productId, storeId, parentCommentId);
        return commentRepository.save(comment).getCommentId();
    }

    @Transactional
    public CommentId update(CommentId commentId, String detail) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        comment.update(detail, LocalDateTime.now());
        return comment.getCommentId();
    }

    @Transactional
    public void delete(CommentId commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        comment.delete(LocalDateTime.now());
    }

    private CommentId getParentCommentId(String parentCommentIdValue) {
        CommentId parentCommentId = parentCommentIdValue != null ?
                CommentId.from(parentCommentIdValue) :
                null;
        if (parentCommentId != null && !commentRepository.existsById(parentCommentId)) {
            throw new CommentNotFoundException();
        }
        return parentCommentId;
    }

    private static Comment toComment(CommentRequest commentRequest, UserId userId, ProductId productId,
            StoreId storeId, CommentId parentCommentId) {
        return Comment.builder()
                .productId(productId)
                .storeId(storeId)
                .userId(userId)
                .detail(commentRequest.detail())
                .parentCommentId(parentCommentId)
                .build();
    }
}
