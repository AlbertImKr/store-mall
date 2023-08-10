package com.albert.commerce.api.comment.query.domain;

import com.albert.commerce.api.comment.command.application.CommentResponse;
import com.albert.commerce.api.comment.command.domain.Comment;
import com.albert.commerce.api.comment.command.domain.CommentId;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.user.command.domain.UserId;
import com.albert.commerce.common.domain.DomainId;
import java.util.List;
import java.util.Optional;

public interface CommentDao {

    Optional<Comment> findById(CommentId commentId);

    List<CommentResponse> findCommentResponseByProductId(ProductId productId);

    boolean exists(CommentId commentId);

    List<CommentResponse> findCommentResponseByUserId(UserId userId);

    List<CommentResponse> findCommentResponseByStoreId(DomainId storeId);
}
