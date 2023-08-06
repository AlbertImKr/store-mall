package com.albert.commerce.domain.query.comment;

import com.albert.commerce.application.command.comment.CommentResponse;
import com.albert.commerce.domain.command.comment.Comment;
import com.albert.commerce.domain.command.comment.CommentId;
import com.albert.commerce.domain.command.product.ProductId;
import com.albert.commerce.domain.command.store.StoreId;
import com.albert.commerce.domain.command.user.UserId;
import java.util.List;
import java.util.Optional;

public interface CommentDao {

    Optional<Comment> findById(CommentId commentId);

    List<CommentResponse> findCommentResponseByProductId(ProductId productId);

    boolean exists(CommentId commentId);

    List<CommentResponse> findCommentResponseByUserId(UserId userId);

    List<CommentResponse> findCommentResponseByStoreId(StoreId storeId);
}
