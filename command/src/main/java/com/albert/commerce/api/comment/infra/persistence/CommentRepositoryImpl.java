package com.albert.commerce.api.comment.infra.persistence;

import com.albert.commerce.api.comment.command.domain.Comment;
import com.albert.commerce.api.comment.command.domain.CommentId;
import com.albert.commerce.api.comment.command.domain.CommentRepository;
import com.albert.commerce.api.comment.infra.persistence.imports.CommentJpaRepository;
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

    @Override
    public Optional<Comment> findById(CommentId commentId) {
        return commentJpaRepository.findById(commentId);
    }

    @Override
    public Comment save(Comment comment) {
        comment.updateId(nextId(), LocalDateTime.now(), LocalDateTime.now());
        return commentJpaRepository.save(comment);
    }

    @Override
    public boolean existsById(CommentId commentId) {
        return commentJpaRepository.existsById(commentId);
    }

    private CommentId nextId() {
        return CommentId.from(sequenceGenerator.generate());
    }

}
