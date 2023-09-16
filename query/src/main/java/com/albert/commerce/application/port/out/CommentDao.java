package com.albert.commerce.application.port.out;

import com.albert.commerce.domain.comment.Comment;
import com.albert.commerce.domain.product.ProductId;
import java.util.List;

public interface CommentDao {

    List<Comment> findAllByProductId(ProductId productId);
}
