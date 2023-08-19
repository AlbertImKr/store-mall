package com.albert.commerce.adapter.out.persistence;

import com.albert.commerce.adapter.out.persistence.imports.CommentJpaRepository;
import com.albert.commerce.application.port.out.CommentRepository;
import com.albert.commerce.application.port.out.persistence.SequenceGenerator;
import com.albert.commerce.domain.comment.Comment;
import com.albert.commerce.domain.comment.CommentId;
import com.albert.commerce.domain.user.UserId;
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

    @Override
    public Optional<Comment> findByCommentIdAndUserId(CommentId commentId, UserId userId) {
        return commentJpaRepository.findByCommentIdAndUserId(commentId, userId);
    }

    private CommentId nextId() {
        return CommentId.from(sequenceGenerator.generate());
    }

}
