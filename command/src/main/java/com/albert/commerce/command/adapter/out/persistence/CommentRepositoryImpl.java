package com.albert.commerce.command.adapter.out.persistence;

import com.albert.commerce.command.adapter.out.persistence.imports.CommentJpaRepository;
import com.albert.commerce.command.application.port.out.CommentRepository;
import com.albert.commerce.command.domain.comment.Comment;
import com.albert.commerce.command.domain.comment.CommentId;
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
