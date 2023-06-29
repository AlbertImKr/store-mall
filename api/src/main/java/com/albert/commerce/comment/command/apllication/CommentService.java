package com.albert.commerce.comment.command.apllication;

import com.albert.commerce.comment.command.domain.Comment;
import com.albert.commerce.comment.command.domain.CommentRepository;
import com.albert.commerce.comment.query.CommentDao;
import com.albert.commerce.user.query.application.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentDao commentDao;


    public CommentResponse save(CommentRequest commentRequest, UserInfoResponse user) {
        Comment comment = commentRepository.save(
                commentRequest.toEntity(user.getId(), commentRepository.nextId()));
        return CommentResponse.of(comment, user.getNickname());
    }
}
