package com.albert.commerce.adapter.in.web.request;

public record CommentPostRequest(
        String productId,
        String storeId,
        String parentCommentId,
        String detail
) {

}
