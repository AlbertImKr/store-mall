package com.albert.commerce.comment.ui;

import com.albert.commerce.comment.command.application.CommentRequest;
import com.albert.commerce.comment.command.application.CommentResponse;
import com.albert.commerce.comment.command.application.CommentService;
import com.albert.commerce.comment.query.application.CommentDaoFacade;
import com.albert.commerce.comment.query.dto.CommentNode;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/comments", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private final CommentDaoFacade commentDaoFacade;


    @PostMapping
    public EntityModel<CommentResponse> saveComment(
            @RequestBody CommentRequest commentRequest,
            Principal principal) {
        String email = principal.getName();
        return EntityModel.of(commentService.save(commentRequest, email));
    }

    @GetMapping(params = "productId")
    public CollectionModel<CommentNode> findCommentsByProductId(String productId) {
        return CollectionModel.of(commentDaoFacade.findCommentsResponseByProductId(productId));
    }
}
