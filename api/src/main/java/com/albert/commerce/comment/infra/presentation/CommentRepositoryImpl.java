package com.albert.commerce.comment.infra.presentation;

import com.albert.commerce.comment.command.domain.Comment;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.comment.command.domain.CommentRepository;
import com.albert.commerce.comment.infra.presentation.imports.CommentJpaRepository;
import com.albert.commerce.comment.query.CommentDao;
import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentRepository, CommentDao {

    private final CommentJpaRepository commentJpaRepository;
    private final SequenceGenerator sequenceGenerator;

    @Override
    public CommentId nextId() {
        return CommentId.from(sequenceGenerator.generate());
    }

    @Override
    public Comment save(Comment comment) {
        return commentJpaRepository.save(comment);
    }

    @Override
    public Optional<Comment> findById(CommentId commentId) {
        return commentJpaRepository.findById(commentId);
    }

    @Override
    public List<Comment> findByProductId(ProductId productId) {
        return commentJpaRepository.findByProductId(productId);
    }

    @Override
    public List<Comment> findByStoreId(StoreId storeId) {
        return commentJpaRepository.findByStoreId(storeId);
    }
}
