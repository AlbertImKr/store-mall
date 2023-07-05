package com.albert.commerce.comment.command.domain;

public interface CommentRepository {

    Comment save(Comment comment);

    CommentId nextId();

    boolean exists(CommentId parentCommentId);
}
