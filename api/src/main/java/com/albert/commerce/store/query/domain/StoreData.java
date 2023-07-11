package com.albert.commerce.store.query.domain;

import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "store")
public class StoreData {

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
}
