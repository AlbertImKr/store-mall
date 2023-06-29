package com.albert.commerce.comment.query;

import com.albert.commerce.comment.command.domain.Comment;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;
import java.util.List;
import java.util.Optional;

public interface CommentDao {

    Optional<Comment> findById(CommentId commentId);

    List<Comment> findByProductId(ProductId productId);

    List<Comment> findByStoreId(StoreId storeId);
}
