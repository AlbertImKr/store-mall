package com.albert.commerce.adapter.out.persistence;

import com.albert.commerce.adapter.out.persistence.imports.CommentJpaRepository;
import com.albert.commerce.application.port.out.CommentRepository;
import com.albert.commerce.application.port.out.persistence.SequenceGenerator;
import com.albert.commerce.domain.comment.Comment;
import com.albert.commerce.domain.comment.CommentId;
import com.albert.commerce.domain.user.UserId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;
    private final SequenceGenerator sequenceGenerator;

    @Override
    public Comment save(Comment comment) {
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

    @Override
    public CommentId nextId() {
        return CommentId.from(sequenceGenerator.generate());
    }

}
