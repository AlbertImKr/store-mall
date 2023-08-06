package com.albert.commerce.domain.command.comment;

import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    boolean exists(CommentId parentCommentId);

    Optional<Comment> findById(CommentId commentId);
}
