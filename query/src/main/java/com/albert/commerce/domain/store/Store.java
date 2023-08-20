package com.albert.commerce.domain.store;

import com.albert.commerce.domain.user.UserId;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "store")
public class Store {

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private LocalDateTime createdTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private LocalDateTime updatedTime;

    @Builder
    private Store(StoreId storeId, String storeName, UserId userId, String ownerName, String address,
            String phoneNumber, String email, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.userId = userId;
        this.ownerName = ownerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public void update(String storeName, String ownerName, String address, String phoneNumber, String email,
            LocalDateTime updatedTime) {
        this.storeName = storeName;
        this.ownerName = ownerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.updatedTime = updatedTime;
    }
}
