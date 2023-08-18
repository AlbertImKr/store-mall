package com.albert.commerce.command.adapter.in.web;

import com.albert.commerce.command.adapter.in.web.dto.CommentCreateRequest;
import com.albert.commerce.command.adapter.in.web.dto.CommentUpdateRequest;
import com.albert.commerce.shared.messaging.application.CommandGateway;
import com.albert.commerce.shared.messaging.application.CommentCreateCommand;
import com.albert.commerce.shared.messaging.application.CommentDeleteCommand;
import com.albert.commerce.shared.messaging.application.CommentUpdateCommand;
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
    public ResponseEntity<Map<String, String>> create(
            @RequestBody CommentCreateRequest commentCreateRequest,
            Principal principal
    ) {
        String userEmail = principal.getName();
        CommentCreateCommand commentCreateCommand = new CommentCreateCommand(
                userEmail,
                commentCreateRequest.productId(),
                commentCreateRequest.storeId(),
                commentCreateRequest.parentCommentId(),
                commentCreateRequest.detail()
        );
        String commentId = commandGateway.request(commentCreateCommand);
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
        CommentUpdateCommand commentUpdateCommand = new CommentUpdateCommand(
                userEmail,
                commentId,
                commentUpdateRequest.detail()
        );
        commandGateway.request(commentUpdateCommand);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(
            Principal principal,
            @PathVariable String commentId
    ) {
        String userEmail = principal.getName();
        CommentDeleteCommand commentDeleteCommand = new CommentDeleteCommand(
                userEmail,
                commentId
        );
        commandGateway.request(commentDeleteCommand);
        return ResponseEntity.noContent().build();
    }
}
