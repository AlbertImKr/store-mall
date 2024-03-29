package com.albert.commerce.application.service;

import com.albert.commerce.application.service.store.StoreDeleteCommand;
import com.albert.commerce.application.service.store.StoreRegisterCommand;
import com.albert.commerce.domain.comment.CommentId;
import com.albert.commerce.domain.order.OrderId;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.UserId;
import java.time.LocalDate;

public class ApplicationFixture {

    public static final String USER_EMAIL = "test@email.com";
    public static final String UPDATE_USER_NICKNAME = "nick";
    public static final LocalDate UPDATE_BIRTH_DAY = LocalDate.of(2023, 9, 30);
    public static final String UPDATE_PHONE_NUMBER = "010-2222-2222";
    public static final String UPDATE_ADDRESS = "서울시 강남구";
    public static final String STORE_NAME = "GoodLife";
    public static final String STORE_ADDRESS = "서울시 강남구 신사동";
    public static final String STORE_PHONE = "010-3333-3333";
    public static final String STORE_EMAIL = "goodlife@email.com";
    public static final String STORE_OWNER = "GoodLife co.,ltd";
    public static final String UPLOAD_STORE_NAME = "GoodDay";
    public static final String UPLOAD_STORE_ADDRESS = "서울시 강남구 삼성2동";
    public static final String UPLOAD_STORE_PHONE_NUMBER = "010-1111-1111";
    public static final String UPLOAD_STORE_EMAIL = "goodday@email.com";
    public static final String UPLOAD_STORE_OWNER_NAME = "GoodDay co.,ltd";
    public static final String USER_ID = "1";
    public static final String STORE_ID = "1";
    public static final String NOT_EXISTS_ID = "not_exists_id";
    public static final String ORDER_ID = "1";
    public static final String COMMENT_ID = "1";

    public static UserId getUserId() {
        return UserId.from(USER_ID);
    }

    public static StoreId getStoreId() {
        return StoreId.from(STORE_ID);
    }

    public static StoreRegisterCommand getStoreRegisterCommand() {
        return new StoreRegisterCommand(USER_EMAIL, STORE_NAME, STORE_ADDRESS, STORE_PHONE
                , STORE_EMAIL, STORE_OWNER);
    }

    public static StoreDeleteCommand getStoreDeleteCommand() {
        return new StoreDeleteCommand(USER_EMAIL);
    }

    public static ProductId getNotExistsProductId() {
        return ProductId.from(NOT_EXISTS_ID);
    }

    public static StoreId getNotExistsStoreId() {
        return StoreId.from(NOT_EXISTS_ID);
    }

    public static OrderId getOrderId() {
        return OrderId.from(ORDER_ID);
    }

    public static CommentId getCommentId() {
        return CommentId.from(COMMENT_ID);
    }
}
