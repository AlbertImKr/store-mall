package com.albert.commerce.comment.ui;

import com.albert.commerce.comment.command.application.CommentRequest;
import com.albert.commerce.comment.command.application.CommentService;
import com.albert.commerce.comment.command.application.CommentUpdateResponse;
import com.albert.commerce.comment.command.domain.CommentId;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/comments", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public EntityModel<CommentId> createComment(
            @RequestBody CommentRequest commentRequest,
            Principal principal) {
        String userEmail = principal.getName();
        return EntityModel.of(commentService.create(userEmail, commentRequest));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentUpdateResponse> updateComment(@PathVariable String commentId,
            @RequestBody String detail) {
        CommentId savedCommentId = commentService.update(CommentId.from(commentId), detail);
        CommentUpdateResponse commentUpdateResponse = CommentUpdateResponse.from(savedCommentId);
        return ResponseEntity.ok().body(commentUpdateResponse);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable String commentId) {
        commentService.delete(CommentId.from(commentId));
        return ResponseEntity.noContent().build();
    }
}
