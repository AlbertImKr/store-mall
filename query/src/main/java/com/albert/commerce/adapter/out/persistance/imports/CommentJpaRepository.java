package com.albert.commerce.adapter.out.persistance.imports;

import com.albert.commerce.domain.comment.Comment;
import com.albert.commerce.domain.comment.CommentId;
import com.albert.commerce.domain.product.ProductId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, CommentId> {

    List<Comment> findAllByProductId(ProductId productId);
}
