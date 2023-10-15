package com.albert.commerce.adapter.in.web;

import com.albert.commerce.adapter.in.web.request.CommentPostRequest;
import com.albert.commerce.adapter.in.web.request.CommentUpdateRequest;
import com.albert.commerce.application.port.in.CommandGateway;
import com.albert.commerce.application.service.comment.CommentDeleteCommand;
import com.albert.commerce.application.service.comment.CommentPostCommand;
import com.albert.commerce.application.service.comment.CommentUpdateCommand;
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

    private final CommandGateway commandGateway;

    @PostMapping
    public ResponseEntity<Map<String, String>> post(
            @RequestBody CommentPostRequest commentPostRequest,
            Principal principal
    ) {
        String userEmail = principal.getName();
        var commentPostCommand = toCommentPostCommand(commentPostRequest, userEmail);
        String commentId = commandGateway.request(commentPostCommand);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        Map.of("commentId", commentId)
                );
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> update(
            Principal principal,
            @PathVariable String commentId,
            @RequestBody CommentUpdateRequest commentUpdateRequest
    ) {
        String userEmail = principal.getName();
        var commentUpdateCommand = toCommentUpdateCommand(commentId, commentUpdateRequest, userEmail);
        commandGateway.request(commentUpdateCommand);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(
            Principal principal,
            @PathVariable String commentId
    ) {
        String userEmail = principal.getName();
        var commentDeleteCommand = new CommentDeleteCommand(
                userEmail,
                commentId
        );
        commandGateway.request(commentDeleteCommand);
        return ResponseEntity.noContent().build();
    }

    private static CommentPostCommand toCommentPostCommand(CommentPostRequest commentPostRequest, String userEmail) {
        return new CommentPostCommand(
                userEmail,
                commentPostRequest.productId(),
                commentPostRequest.storeId(),
                commentPostRequest.parentCommentId(),
                commentPostRequest.detail()
        );
    }

    private static CommentUpdateCommand toCommentUpdateCommand(String commentId,
            CommentUpdateRequest commentUpdateRequest, String userEmail) {
        return new CommentUpdateCommand(
                userEmail,
                commentId,
                commentUpdateRequest.detail()
        );
    }
}
