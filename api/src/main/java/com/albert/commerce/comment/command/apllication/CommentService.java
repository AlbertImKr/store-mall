package com.albert.commerce.comment.command.apllication;

import com.albert.commerce.comment.command.domain.Comment;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.comment.command.domain.CommentRepository;
import com.albert.commerce.comment.query.CommentDao;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentDao commentDao;


    public CommentResponse save(UserId userId, String nickname, ProductId productId,
            StoreId storeId, String detail,
            CommentId parentCommentId) {
        Comment parentComment = commentDao.findById(parentCommentId);
        Comment comment = Comment.builder()
                .commentId(commentRepository.nextId())
                .productId(productId)
                .storeId(storeId)
                .userId(userId)
                .detail(detail)
                .build();
        Comment savedComment = commentRepository.save(comment);
        parentComment.updateChildCommentId(comment.getCommentId());
        return CommentResponse.of(savedComment, nickname);
    }

    public CommentResponse save(UserId userId, String nickname, ProductId productId,
            StoreId storeId,
            String detail) {
        Comment comment = Comment.builder()
                .commentId(commentRepository.nextId())
                .productId(productId)
                .storeId(storeId)
                .userId(userId)
                .detail(detail)
                .build();
        Comment savedComment = commentRepository.save(comment);
        return CommentResponse.of(savedComment, nickname);
    }
}
