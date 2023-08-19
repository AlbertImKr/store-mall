package com.albert.commerce.adapter.in.web.dto;

public record CommentCreateRequest(
        String productId,
        String storeId,
        String parentCommentId,
        String detail
) {

}
