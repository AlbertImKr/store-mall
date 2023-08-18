package com.albert.commerce.command.application.comment;

public record CommentRequest(String productId,
                             String storeId,
                             String parentCommentId,
                             String detail) {

}
