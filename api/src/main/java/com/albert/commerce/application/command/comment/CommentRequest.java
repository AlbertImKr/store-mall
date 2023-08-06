package com.albert.commerce.application.command.comment;

public record CommentRequest(String productId,
                             String storeId,
                             String parentCommentId,
                             String detail) {

}
