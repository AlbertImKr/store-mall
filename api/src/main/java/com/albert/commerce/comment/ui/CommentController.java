package com.albert.commerce.comment.ui;

import com.albert.commerce.comment.command.application.CommentRequest;
import com.albert.commerce.comment.command.application.CommentResponse;
import com.albert.commerce.comment.command.application.CommentService;
import com.albert.commerce.comment.command.application.CommentUpdateResponse;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.comment.query.application.CommentFacade;
import com.albert.commerce.comment.query.dto.CommentNode;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.query.application.ProductFacade;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.query.application.StoreFacade;
import com.albert.commerce.user.command.application.dto.UserInfoResponse;
import com.albert.commerce.user.query.application.UserFacade;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final CommentFacade commentFacade;
    private final UserFacade userFacade;
    private final ProductFacade productFacade;
    private final StoreFacade storeFacade;

    @PostMapping
    public EntityModel<CommentResponse> createComment(
            @RequestBody CommentRequest commentRequest,
            Principal principal) {
        String email = principal.getName();
        UserInfoResponse user = userFacade.findByEmail(email);

        ProductId productId = ProductId.from(commentRequest.productId());
        productFacade.checkId(productId);

        StoreId storeId = StoreId.from(commentRequest.storeId());
        storeFacade.checkId(storeId);

        CommentId parentCommentId =
                commentRequest.parentCommentId() == null ?
                        null :
                        CommentId.from(commentRequest.parentCommentId());
        commentFacade.checkId(parentCommentId);

        return EntityModel.of(
                commentService.create(productId, storeId, parentCommentId, user.getId(),
                        commentRequest.detail(), user.getNickname()));
    }

    @GetMapping(params = "productId")
    public CollectionModel<CommentNode> findCommentsByProductId(String productId) {
        return CollectionModel.of(
                commentFacade.findCommentsResponseByProductId(ProductId.from(productId)));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentUpdateResponse> updateComment(@PathVariable String commentId,
            @RequestBody String detail) {
        CommentId savedCommentId = commentService.update(CommentId.from(commentId), detail);
        CommentUpdateResponse commentUpdateResponse = CommentUpdateResponse.from(savedCommentId);
        return ResponseEntity.ok().body(commentUpdateResponse);
    }
}
