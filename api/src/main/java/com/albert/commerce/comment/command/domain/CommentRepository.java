package com.albert.commerce.comment.command.domain;

public interface CommentRepository {

    Comment save(Comment comment);

    boolean exists(CommentId parentCommentId);
}
