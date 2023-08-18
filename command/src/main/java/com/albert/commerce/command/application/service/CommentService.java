package com.albert.commerce.command.application.service;

import com.albert.commerce.command.adapter.in.web.dto.CommentRequest;
import com.albert.commerce.command.application.port.out.CommentRepository;
import com.albert.commerce.command.domain.comment.Comment;
import com.albert.commerce.command.domain.comment.CommentId;
import com.albert.commerce.command.domain.product.ProductId;
import com.albert.commerce.command.domain.store.StoreId;
import com.albert.commerce.command.domain.user.UserId;
import com.albert.commerce.common.exception.CommentNotFoundException;
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
