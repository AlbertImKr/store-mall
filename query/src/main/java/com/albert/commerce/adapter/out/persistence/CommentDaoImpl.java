package com.albert.commerce.adapter.out.persistence;

import com.albert.commerce.adapter.out.persistence.imports.CommentJpaRepository;
import com.albert.commerce.application.port.out.CommentDao;
import com.albert.commerce.domain.comment.Comment;
import com.albert.commerce.domain.product.ProductId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CommentDaoImpl implements CommentDao {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public List<Comment> findAllByProductId(ProductId productId) {
        return commentJpaRepository.findAllByProductId(productId);
    }
}
