package com.albert.commerce.application.port.out;

import com.albert.commerce.domain.comment.Comment;
import com.albert.commerce.domain.comment.CommentId;
import com.albert.commerce.domain.user.UserId;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    boolean existsById(CommentId commentId);

    Optional<Comment> findById(CommentId commentId);

    Optional<Comment> findByCommentIdAndUserId(CommentId commentId, UserId userId);
}
