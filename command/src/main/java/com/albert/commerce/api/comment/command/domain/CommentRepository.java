package com.albert.commerce.api.comment.command.domain;

import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    boolean existsById(CommentId commentId);

    Optional<Comment> findById(CommentId commentId);
}
