package com.albert.commerce.comment.query;

import com.albert.commerce.comment.command.domain.Comment;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.comment.infra.presentation.CommentDTO;
import com.albert.commerce.product.command.domain.ProductId;
import java.util.Collection;

public interface CommentDao {

    Comment findById(CommentId commentId);

    Collection<CommentDTO> findByProductId(ProductId productId);

}
