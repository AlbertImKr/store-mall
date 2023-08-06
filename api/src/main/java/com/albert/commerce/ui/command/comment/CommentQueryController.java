package com.albert.commerce.ui.command.comment;

import com.albert.commerce.application.query.comment.CommentFacade;
import com.albert.commerce.application.query.comment.dto.CommentNode;
import com.albert.commerce.domain.command.product.ProductId;
import com.albert.commerce.domain.command.store.StoreId;
import com.albert.commerce.domain.command.user.UserId;
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
                commentFacade.findCommentsResponseByStoreId(StoreId.from(storeId)));
    }
}
