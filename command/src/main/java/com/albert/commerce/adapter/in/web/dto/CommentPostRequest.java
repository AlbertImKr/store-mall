package com.albert.commerce.adapter.in.web.dto;

public record CommentPostRequest(
        String productId,
        String storeId,
        String parentCommentId,
        String detail
) {

}
