package com.albert.commerce.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import com.albert.commerce.adapter.out.persistence.Money;
import com.albert.commerce.domain.DomainFixture;
import com.albert.commerce.domain.event.DomainEvent;
import com.albert.commerce.domain.event.Events;
import com.albert.commerce.domain.store.StoreId;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @DisplayName("상품 생성 시 이벤트가 발생한다")
    @Test
    void create_product_publish_event() {
        // given
        ProductId productId = DomainFixture.getProductId(DomainFixture.PRODUCT_ID_1);
        StoreId storeId = DomainFixture.getStoreId();
        String productName = DomainFixture.PRODUCT_NAME;
        Money productPrice = DomainFixture.getProductPrice();
        String productDescription = DomainFixture.PRODUCT_DESCRIPTION;
        String productBrand = DomainFixture.PRODUCT_BRAND;
        String productCategory = DomainFixture.PRODUCT_CATEGORY;
        LocalDateTime createdTime = DomainFixture.getCreatedTime();
        LocalDateTime updatedTime = DomainFixture.getUpdatedTime();
        Events.clear();

        // when
        new Product(
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

        // then
        List<DomainEvent> events = Events.getEvents();
        DomainEvent domainEvent = events.get(0);

        Assertions.assertAll(
                () -> assertThat(domainEvent.getClass()).isEqualTo(ProductCreatedEvent.class),
                () -> assertThat(((ProductCreatedEvent) domainEvent).getProductId()).isEqualTo(productId),
                () -> assertThat(((ProductCreatedEvent) domainEvent).getStoreId()).isEqualTo(storeId),
                () -> assertThat(((ProductCreatedEvent) domainEvent).getProductName()).isEqualTo(productName),
                () -> assertThat(((ProductCreatedEvent) domainEvent).getPrice()).isEqualTo(productPrice),
                () -> assertThat(((ProductCreatedEvent) domainEvent).getDescription()).isEqualTo(productDescription),
                () -> assertThat(((ProductCreatedEvent) domainEvent).getBrand()).isEqualTo(productBrand),
                () -> assertThat(((ProductCreatedEvent) domainEvent).getCategory()).isEqualTo(productCategory),
                () -> assertThat(((ProductCreatedEvent) domainEvent).getCreatedTime()).isEqualTo(createdTime),
                () -> assertThat(((ProductCreatedEvent) domainEvent).getUpdatedTime()).isEqualTo(updatedTime)
        );
    }

    @DisplayName("상품 업로드 시 이벤트가 발생한다")
    @Test
    void upload_product_publish_event() {
        // given
        Product product = DomainFixture.createProduct(DomainFixture.PRODUCT_ID_1);
        Events.clear();

        String updatedProductName = DomainFixture.UPDATED_PRODUCT_NAME;
        Money updatedProductPrice = DomainFixture.getUpdatedProductPrice();
        String updatedProductBrand = DomainFixture.UPDATED_PRODUCT_BRAND;
        String updatedProductCategory = DomainFixture.UPDATED_PRODUCT_CATEGORY;
        String updatedProductDescription = DomainFixture.UPDATED_PRODUCT_DESCRIPTION;
        LocalDateTime updatedTime = DomainFixture.getUpdatedTime();

        // when
        product.upload(
                updatedProductName,
                updatedProductPrice,
                updatedProductBrand,
                updatedProductCategory,
                updatedProductDescription,
                updatedTime
        );

        // then
        List<DomainEvent> events = Events.getEvents();
        DomainEvent domainEvent = events.get(0);

        Assertions.assertAll(
                () -> assertThat(domainEvent.getClass()).isEqualTo(ProductUpdatedEvent.class),
                () -> assertThat(((ProductUpdatedEvent) domainEvent).getProductId()).isEqualTo(product.getProductId()),
                () -> assertThat(((ProductUpdatedEvent) domainEvent).getProductName()).isEqualTo(updatedProductName),
                () -> assertThat(((ProductUpdatedEvent) domainEvent).getPrice()).isEqualTo(updatedProductPrice),
                () -> assertThat(((ProductUpdatedEvent) domainEvent).getBrand()).isEqualTo(updatedProductBrand),
                () -> assertThat(((ProductUpdatedEvent) domainEvent).getCategory()).isEqualTo(updatedProductCategory),
                () -> assertThat(((ProductUpdatedEvent) domainEvent).getDescription()).isEqualTo(
                        updatedProductDescription),
                () -> assertThat(((ProductUpdatedEvent) domainEvent).getUpdatedTime()).isEqualTo(updatedTime)
        );
    }
}
