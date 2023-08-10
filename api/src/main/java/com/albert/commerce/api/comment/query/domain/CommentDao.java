package com.albert.commerce.api.comment.query.domain;

import com.albert.commerce.api.comment.command.application.CommentResponse;
import com.albert.commerce.api.comment.command.domain.Comment;
import com.albert.commerce.common.domain.DomainId;
import java.util.List;
import java.util.Optional;

public interface CommentDao {

    Optional<Comment> findById(DomainId commentId);

    List<CommentResponse> findCommentResponseByProductId(DomainId productId);

    boolean exists(DomainId commentId);

    List<CommentResponse> findCommentResponseByUserId(DomainId userId);

    List<CommentResponse> findCommentResponseByStoreId(DomainId storeId);
}
