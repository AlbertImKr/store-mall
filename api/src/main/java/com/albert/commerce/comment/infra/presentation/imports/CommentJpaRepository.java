package com.albert.commerce.comment.infra.presentation.imports;

import com.albert.commerce.comment.command.domain.Comment;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, CommentId> {

    List<Comment> findByProductId(ProductId productId);

    List<Comment> findByStoreId(StoreId storeId);

}
