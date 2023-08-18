package com.albert.commerce.command.adapter.in.web.dto;

public record CommentRequest(
        String productId,
        String storeId,
        String parentCommentId,
        String detail
) {

}
