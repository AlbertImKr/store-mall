package com.albert.commerce.domain.comment;

import java.util.Optional;

public interface CommentDao {

    Optional<Comment> findById(CommentId commentId);

    boolean exists(CommentId commentId);

    Comment save(Comment comment);
}
