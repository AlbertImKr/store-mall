package com.albert.commerce.api.store.query.domain;

import com.albert.commerce.api.store.command.domain.StoreId;
import com.albert.commerce.api.user.command.domain.UserId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
@Table(name = "store_query")
public class StoreData {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "store_id"))
    private StoreId storeId;
    @Column(nullable = false)
    private String storeName;
    @Embedded
    @Column(nullable = false)
    @AttributeOverride(name = "value", column = @Column(name = "user_id"))
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
    private StoreData(StoreId storeId, String storeName, UserId userId, String ownerName, String address,
            String phoneNumber,
            String email) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.userId = userId;
        this.ownerName = ownerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public void update(String storeName, String ownerName, String address, String phoneNumber, String email) {
        this.storeName = storeName;
        this.ownerName = ownerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
