package com.albert.commerce.query.domain.comment;

import java.util.Optional;

public interface CommentDao {

    Optional<CommentData> findById(CommentId commentId);

    boolean exists(CommentId commentId);

    CommentData save(CommentData comment);
}
