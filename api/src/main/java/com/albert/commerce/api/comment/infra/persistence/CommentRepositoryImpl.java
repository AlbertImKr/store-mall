package com.albert.commerce.api.comment.infra.persistence;

import com.albert.commerce.api.comment.command.domain.Comment;
import com.albert.commerce.api.comment.command.domain.CommentRepository;
import com.albert.commerce.api.comment.infra.persistence.imports.CommentJpaRepository;
import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;
    private final SequenceGenerator sequenceGenerator;

    private DomainId nextId() {
        return DomainId.from(sequenceGenerator.generate());
    }

    @Override
    public Comment save(Comment comment) {
        comment.updateId(nextId(), LocalDateTime.now(), LocalDateTime.now());
        return commentJpaRepository.save(comment);
    }

    @Override
    public Optional<Comment> findById(DomainId commentId) {
        return commentJpaRepository.findById(commentId);
    }

    @Override
    public boolean existsById(DomainId domainId) {
        return commentJpaRepository.existsById(domainId);
    }

}
