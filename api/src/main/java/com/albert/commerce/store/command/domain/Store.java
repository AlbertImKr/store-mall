package com.albert.commerce.store.command.domain;

import com.albert.commerce.common.model.BaseEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "store")
public class Store extends BaseEntity {


    @EmbeddedId
    private StoreId storeId;
    private String storeName;
    private StoreUserId storeUserId;
    private String ownerName;
    private String address;
    private String phoneNumber;
    private String email;

    @Builder
    private Store(StoreId storeId, String storeName, StoreUserId storeUserId, String ownerName,
            String address, String phoneNumber, String email) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeUserId = storeUserId;
        this.ownerName = ownerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
