package com.albert.commerce.comment.command.apllication;

import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;

public record CommentRequest(ProductId productId,
                             StoreId storeId,
                             CommentId parentCommentId,
                             String detail) {

}
