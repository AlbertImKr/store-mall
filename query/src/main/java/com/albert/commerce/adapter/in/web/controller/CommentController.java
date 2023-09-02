package com.albert.commerce.adapter.in.web.controller;

import com.albert.commerce.adapter.in.web.facade.CommentFacade;
import com.albert.commerce.domain.comment.Comment;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentFacade commentFacade;

    @GetMapping("products/{productId}/comments/")
    public List<Comment> getAllByProductId(@PathVariable String productId) {
        return commentFacade.getAllByProductId(productId);
    }
}
