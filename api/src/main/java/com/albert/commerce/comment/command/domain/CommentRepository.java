package com.albert.commerce.comment.command.domain;

import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    boolean exists(CommentId parentCommentId);

    Optional<Comment> findById(CommentId commentId);
}
