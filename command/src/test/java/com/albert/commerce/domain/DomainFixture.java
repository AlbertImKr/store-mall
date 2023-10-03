package com.albert.commerce.domain;

import com.albert.commerce.adapter.out.persistence.Money;
import com.albert.commerce.application.service.store.StoreRegisterCommand;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.UserId;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DomainFixture {

    public static final String USER_EMAIL = "test@email.com";
    public static final String UPDATE_USER_NICKNAME = "nick";
    public static final LocalDate UPDATE_BIRTH_DAY = LocalDate.of(1999, 9, 30);
    public static final String UPDATE_PHONE_NUMBER = "010-2222-2222";
    public static final String UPDATE_ADDRESS = "서울시 강남구";
    public static final String USER_ID = "1";
    public static final String STORE_ID = "1";
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
    public static final String PRODUCT_ID = "1";
    public static final String PRODUCT_NAME = "흰 티셔츠";
    public static final String PRODUCT_DESCRIPTION = "부드럽다";
    public static final String PRODUCT_BRAND = "GoodLife";
    public static final String PRODUCT_CATEGORY = "면 80%";
    public static final String UPDATED_PRODUCT_NAME = "검증 티셔츠";
    public static final String UPDATED_PRODUCT_BRAND = "GoodDay";
    public static final String UPDATED_PRODUCT_CATEGORY = "면 100%";
    public static final String UPDATED_PRODUCT_DESCRIPTION = "매우 부드럽다";

    public static UserId getUserId() {
        return UserId.from(USER_ID);
    }

    public static String getUpdatedAddress() {
        return UPDATE_ADDRESS;
    }

    public static String getUpdatedNickname() {
        return UPDATE_USER_NICKNAME;
    }

    public static String getUpdatedPhoneNumber() {
        return UPDATE_PHONE_NUMBER;
    }

    public static LocalDate getUpdatedDateOfBirth() {
        return UPDATE_BIRTH_DAY;
    }

    public static LocalDateTime getUpdatedTime() {
        return LocalDateTime.of(2023, 9, 30, 0, 0);
    }

    public static LocalDateTime getCreatedTime() {
        return LocalDateTime.of(2023, 9, 29, 0, 0);
    }

    public static StoreId getStoreId() {
        return StoreId.from(STORE_ID);
    }

    public static StoreRegisterCommand getStoreRegisterCommand() {
        return new StoreRegisterCommand(USER_EMAIL, STORE_NAME, STORE_ADDRESS, STORE_PHONE, STORE_EMAIL, STORE_OWNER);
    }

    public static Store getStore() {
        StoreId storeId = DomainFixture.getStoreId();
        StoreRegisterCommand storeRegisterCommand = DomainFixture.getStoreRegisterCommand();
        UserId userId = DomainFixture.getUserId();
        return Store.from(storeId, storeRegisterCommand, userId);
    }

    public static ProductId getProductId() {
        return ProductId.from(PRODUCT_ID);
    }

    public static Money getProductPrice() {
        return new Money(100000);
    }

    public static Product createProduct() {
        ProductId productId = DomainFixture.getProductId();
        StoreId storeId = DomainFixture.getStoreId();
        String productName = DomainFixture.PRODUCT_NAME;
        Money productPrice = DomainFixture.getProductPrice();
        String productDescription = DomainFixture.PRODUCT_DESCRIPTION;
        String productBrand = DomainFixture.PRODUCT_BRAND;
        String productCategory = DomainFixture.PRODUCT_CATEGORY;
        LocalDateTime createdTime = DomainFixture.getCreatedTime();
        LocalDateTime updatedTime = DomainFixture.getCreatedTime();

        return new Product(
                productId,
                storeId,
                productName,
                productPrice,
                productDescription,
                productBrand,
                productCategory,
                createdTime,
                updatedTime
        );
    }

    public static Money getUpdatedProductPrice() {
        return new Money(200000);
    }
}
