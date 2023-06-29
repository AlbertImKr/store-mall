package com.albert.commerce.comment.ui;

import com.albert.commerce.comment.command.apllication.CommentRequest;
import com.albert.commerce.comment.command.apllication.CommentResponse;
import com.albert.commerce.comment.command.apllication.CommentService;
import com.albert.commerce.comment.query.CommentDao;
import com.albert.commerce.user.query.application.UserInfoResponse;
import com.albert.commerce.user.query.domain.UserDao;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/comments", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private final CommentDao commentDao;
    private final UserDao userDao;


    @PostMapping
    public EntityModel<CommentResponse> saveComment(@RequestBody CommentRequest commentRequest,
            Principal principal) {
        UserInfoResponse user = userDao.findUserProfileByEmail(principal.getName());
        CommentResponse commentResponse = commentService.save(commentRequest, user);
        return EntityModel.of(commentResponse);
    }
}
