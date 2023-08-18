package com.albert.commerce.command.interfaces;

import com.albert.commerce.command.application.comment.CommentRequest;
import com.albert.commerce.command.application.comment.CommentService;
import com.albert.commerce.command.application.comment.CommentUpdateRequest;
import com.albert.commerce.command.domain.comment.CommentId;
import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Map<String, String>> createComment(
            @RequestBody CommentRequest commentRequest,
            Principal principal) {
        String userEmail = principal.getName();
        CommentId commentId = commentService.create(userEmail, commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("commentId", commentId.getValue()));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Map<String, String>> updateComment(@PathVariable String commentId,
            @RequestBody CommentUpdateRequest commentUpdateRequest) {
        commentService.update(CommentId.from(commentId), commentUpdateRequest.detail());
        return ResponseEntity.ok().body(Map.of("commentId", commentId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable String commentId) {
        commentService.delete(CommentId.from(commentId));
        return ResponseEntity.noContent().build();
    }
}
