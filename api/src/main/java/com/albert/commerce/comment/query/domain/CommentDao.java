package com.albert.commerce.comment.query.domain;

import com.albert.commerce.comment.command.application.CommentResponse;
import com.albert.commerce.comment.command.domain.Comment;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.user.command.domain.UserId;
import java.util.List;
import java.util.Optional;

public interface CommentDao {

    Optional<Comment> findById(CommentId commentId);

    List<CommentResponse> findCommentResponseByProductId(ProductId productId);

    boolean exists(CommentId commentId);

    List<CommentResponse> findCommentResponseByUserId(UserId userId);
}
