package com.albert.commerce.comment.query;

import com.albert.commerce.comment.command.apllication.CommentResponse;
import com.albert.commerce.comment.command.domain.Comment;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.product.command.domain.ProductId;
import java.util.List;

public interface CommentDao {

    Comment findById(CommentId commentId);

    List<CommentResponse> findCommentResponseByProductId(ProductId productId);

}
