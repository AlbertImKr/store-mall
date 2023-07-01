package com.albert.commerce.store.command.domain;

import com.albert.commerce.common.infra.persistence.BaseEntity;
import com.albert.commerce.user.command.domain.UserId;
import jakarta.persistence.Column;
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
    @Column(nullable = false)
    private String storeName;
    @Column(nullable = false)
    private UserId userId;
    @Column(nullable = false)
    private String ownerName;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String email;

    @Builder
    private Store(StoreId storeId, String storeName, UserId userId, String ownerName,
            String address, String phoneNumber, String email) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.userId = userId;
        this.ownerName = ownerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
