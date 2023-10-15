package com.albert.commerce.domain.store;

import static org.assertj.core.api.Assertions.assertThat;

import com.albert.commerce.application.service.store.StoreRegisterCommand;
import com.albert.commerce.domain.DomainFixture;
import com.albert.commerce.domain.event.DomainEvent;
import com.albert.commerce.domain.event.Events;
import com.albert.commerce.domain.user.UserId;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StoreTest {

    @BeforeEach
    void setUp() {
        Events.clear();
    }

    @DisplayName("스토어를 업로드하면 스토어 업로드 이벤트가 발행한다")
    @Test
    void upload_store_event_published() {
        // given
        Store store = DomainFixture.getStore();
        Events.clear();
        String storeName = DomainFixture.STORE_NAME;
        String storeOwner = DomainFixture.STORE_OWNER;
        String storeAddress = DomainFixture.STORE_ADDRESS;
        String storeEmail = DomainFixture.STORE_EMAIL;
        String storePhone = DomainFixture.STORE_PHONE;
        LocalDateTime updatedTime = DomainFixture.getUpdatedTime();

        // when
        store.upload(storeName, storeOwner, storeAddress, storeEmail, storePhone, updatedTime);

        // then
        List<DomainEvent> events = Events.getEvents();
        DomainEvent domainEvent = events.get(0);
        Assertions.assertAll(
                () -> assertThat(domainEvent.getClass()).isEqualTo(StoreUploadedEvent.class),
                () -> assertThat(((StoreUploadedEvent) domainEvent)).extracting(StoreUploadedEvent::getStoreId)
                        .isEqualTo(store.getStoreId()),
                () -> assertThat(((StoreUploadedEvent) domainEvent)).extracting(StoreUploadedEvent::getStoreName)
                        .isEqualTo(storeName),
                () -> assertThat(((StoreUploadedEvent) domainEvent)).extracting(StoreUploadedEvent::getOwnerName)
                        .isEqualTo(storeOwner),
                () -> assertThat(((StoreUploadedEvent) domainEvent)).extracting(StoreUploadedEvent::getAddress)
                        .isEqualTo(storeAddress),
                () -> assertThat(((StoreUploadedEvent) domainEvent)).extracting(StoreUploadedEvent::getEmail)
                        .isEqualTo(storeEmail),
                () -> assertThat(((StoreUploadedEvent) domainEvent)).extracting(StoreUploadedEvent::getPhoneNumber)
                        .isEqualTo(storePhone),
                () -> assertThat(((StoreUploadedEvent) domainEvent)).extracting(StoreUploadedEvent::getUpdatedTime)
                        .isEqualTo(updatedTime)
        );
    }

    @DisplayName("스토어가 등록하면 스토어 등록 이벤트가 발생한다")
    @Test
    void register_store_event_published() {
        // given
        StoreId storeId = DomainFixture.getStoreId();
        StoreRegisterCommand storeRegisterCommand = DomainFixture.getStoreRegisterCommand();
        UserId userId = DomainFixture.getUserId();

        // when
        Store.from(storeId, storeRegisterCommand, userId);

        //then
        List<DomainEvent> events = Events.getEvents();
        DomainEvent domainEvent = events.get(0);
        Assertions.assertAll(
                () -> assertThat(domainEvent.getClass()).isEqualTo(StoreRegisteredEvent.class),
                () -> assertThat((StoreRegisteredEvent) domainEvent)
                        .extracting(StoreRegisteredEvent::getStoreId)
                        .isEqualTo(storeId),
                () -> assertThat((StoreRegisteredEvent) domainEvent)
                        .extracting(StoreRegisteredEvent::getUserId)
                        .isEqualTo(userId),
                () -> assertThat((StoreRegisteredEvent) domainEvent)
                        .extracting(StoreRegisteredEvent::getStoreName)
                        .isEqualTo(storeRegisterCommand.getStoreName()),
                () -> assertThat((StoreRegisteredEvent) domainEvent)
                        .extracting(StoreRegisteredEvent::getAddress)
                        .isEqualTo(storeRegisterCommand.getAddress()),
                () -> assertThat((StoreRegisteredEvent) domainEvent)
                        .extracting(StoreRegisteredEvent::getPhoneNumber)
                        .isEqualTo(storeRegisterCommand.getPhoneNumber()),
                () -> assertThat((StoreRegisteredEvent) domainEvent)
                        .extracting(StoreRegisteredEvent::getEmail)
                        .isEqualTo(storeRegisterCommand.getEmail()),
                () -> assertThat((StoreRegisteredEvent) domainEvent)
                        .extracting(StoreRegisteredEvent::getOwnerName)
                        .isEqualTo(storeRegisterCommand.getOwnerName())
        );
    }
}
