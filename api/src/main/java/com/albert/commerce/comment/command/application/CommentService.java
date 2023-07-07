package com.albert.commerce.comment.command.application;

import com.albert.commerce.comment.command.domain.Comment;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.comment.command.domain.CommentRepository;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponse create(ProductId productId, StoreId storeId, CommentId parentCommentId,
            UserId userId, String detail, String userNickname) {
        Comment comment = Comment.builder()
                .commentId(commentRepository.nextId())
                .productId(productId)
                .storeId(storeId)
                .userId(userId)
                .detail(detail)
                .parentCommentId(parentCommentId)
                .build();
        Comment savedComment = commentRepository.save(comment);
        return CommentResponse.of(savedComment, userNickname);
    }
}
