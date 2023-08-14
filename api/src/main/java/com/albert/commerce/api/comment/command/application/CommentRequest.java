package com.albert.commerce.api.comment.command.application;

public record CommentRequest(String productId,
                             String storeId,
                             String parentCommentId,
                             String detail) {

}
