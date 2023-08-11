package com.albert.commerce.api.comment.ui;

import com.albert.commerce.api.comment.query.application.CommentFacade;
import com.albert.commerce.api.comment.query.application.dto.CommentNode;
import com.albert.commerce.common.domain.DomainId;
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
                commentFacade.findCommentsResponseByProductId(DomainId.from(productId)));
    }

    @GetMapping(params = "userId")
    public CollectionModel<CommentNode> findCommentsByUserId(String userId) {
        return CollectionModel.of(
                commentFacade.findCommentsResponseByUserId(DomainId.from(userId)));
    }

    @GetMapping(params = "storeId")
    public CollectionModel<CommentNode> findCommentsByStoreId(String storeId) {
        return CollectionModel.of(
                commentFacade.findCommentsResponseByStoreId(DomainId.from(storeId)));
    }
}
