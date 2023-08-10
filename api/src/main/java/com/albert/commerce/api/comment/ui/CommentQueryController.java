package com.albert.commerce.api.comment.ui;

import com.albert.commerce.api.comment.query.application.CommentFacade;
import com.albert.commerce.api.comment.query.dto.CommentNode;
import com.albert.commerce.api.common.domain.DomainId;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.user.command.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/comments", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
@RestController
public class CommentQueryController {

    private final CommentFacade commentFacade;

    @GetMapping(params = "productId")
    public CollectionModel<CommentNode> findCommentsByProductId(String productId) {
        return CollectionModel.of(
                commentFacade.findCommentsResponseByProductId(ProductId.from(productId)));
    }

    @GetMapping(params = "userId")
    public CollectionModel<CommentNode> findCommentsByUserId(String userId) {
        return CollectionModel.of(
                commentFacade.findCommentsResponseByUserId(UserId.from(userId)));
    }

    @GetMapping(params = "storeId")
    public CollectionModel<CommentNode> findCommentsByStoreId(String storeId) {
        return CollectionModel.of(
                commentFacade.findCommentsResponseByStoreId(DomainId.from(storeId)));
    }
}
