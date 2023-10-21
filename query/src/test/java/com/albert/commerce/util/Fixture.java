package com.albert.commerce.util;

import com.albert.commerce.adapter.in.messaging.listener.domainevent.dto.CommentDeletedEvent;
import com.albert.commerce.adapter.in.messaging.listener.domainevent.dto.CommentPostedEvent;
import com.albert.commerce.adapter.in.messaging.listener.domainevent.dto.CommentUpdatedEvent;
import com.albert.commerce.adapter.out.persistence.Money;
import com.albert.commerce.domain.comment.Comment;
import com.albert.commerce.domain.comment.CommentId;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.Role;
import com.albert.commerce.domain.user.User;
import com.albert.commerce.domain.user.UserId;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Fixture {

    public static CommentId commentId() {
        return CommentId.from("commentId");
    }

    public static ProductId productId() {
        return ProductId.from("productId");
    }

    public static StoreId storeId() {
        return StoreId.from("storeId");
    }

    public static UserId userId() {
        return UserId.from("userId");
    }

    public static CommentId parentCommentId() {
        return CommentId.from("parentCommentId");
    }

    public static String detail() {
        return "detail";
    }

    public static CommentPostedEvent commentPostedEvent() {
        return new CommentPostedEvent(
                commentId(),
                productId(),
                storeId(),
                userId(),
                parentCommentId(),
                detail(),
                createdTime(),
                updatedTime()
        );
    }

    public static User user() {
        return User.builder()
                .userId(userId())
                .email("example@email.com")
                .nickname("nickname")
                .address("서울시 강남구 역삼동")
                .createdTime(createdTime())
                .updatedTime(updatedTime())
                .phoneNumber("010-1234-5678")
                .dateOfBirth(LocalDate.of(2021, 1, 1))
                .isActive(true)
                .role(Role.USER)
                .build();
    }

    public static Store store() {
        return Store.builder()
                .storeId(storeId())
                .userId(userId())
                .ownerName("ownerName")
                .phoneNumber("010-1234-5678")
                .email("store@email.com")
                .storeName("storeName")
                .address("서울시 강남구 역삼동")
                .createdTime(createdTime())
                .updatedTime(updatedTime())
                .build();
    }

    public static Product product() {
        return Product.builder()
                .productId(productId())
                .productName("productName")
                .price(Money.from(10000L))
                .description("It's product description.")
                .brand("brand")
                .category("category")
                .storeId(storeId())
                .createdTime(createdTime())
                .updatedTime(updatedTime())
                .build();
    }

    public static Comment comment() {
        return Comment.builder()
                .commentId(commentId())
                .productId(productId())
                .productName("productName")
                .userId(userId())
                .nickname("nickname")
                .storeId(storeId())
                .storeName("storeName")
                .detail("detail")
                .parentCommentId(parentCommentId())
                .createdTime(createdTime())
                .updatedTime(updatedTime())
                .build();
    }

    public static CommentUpdatedEvent commentUpdatedEvent() {
        return new CommentUpdatedEvent(
                commentId(),
                newDetail(),
                newUpdatedTime()
        );
    }

    public static LocalDateTime newUpdatedTime() {
        return LocalDateTime.of(2021, 1, 3, 3, 4, 5);
    }

    public static String newDetail() {
        return "Very good!";
    }

    public static LocalDateTime updatedTime() {
        return LocalDateTime.of(2021, 1, 2, 3, 4, 5);
    }

    public static LocalDateTime createdTime() {
        return LocalDateTime.of(2021, 1, 1, 0, 0, 0);
    }

    public static CommentDeletedEvent commentDeletedEvent() {
        return new CommentDeletedEvent(
                commentId(),
                newUpdatedTime()
        );
    }
}
