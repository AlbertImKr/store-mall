package com.albert.commerce.api.store.command.application.dto;

import com.albert.commerce.api.store.command.domain.Store;
import com.albert.commerce.api.store.command.domain.StoreId;
import com.albert.commerce.common.units.BusinessLinks;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class SellerStoreResponse extends RepresentationModel<SellerStoreResponse> {

    private StoreId storeId;
    private String storeName;
    private String address;
    private String phoneNumber;
    private String email;
    private String ownerName;

    @Builder
    private SellerStoreResponse(StoreId storeId, String storeName, String address,
            String phoneNumber,
            String email, String ownerName, Links links) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.ownerName = ownerName;
        this.add(links);
    }


    public static SellerStoreResponse from(Store store) {
        return SellerStoreResponse.builder()
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .address(store.getAddress())
                .email(store.getEmail())
                .phoneNumber(store.getPhoneNumber())
                .ownerName(store.getOwnerName())
                .links(Links.of(BusinessLinks.MY_STORE))
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SellerStoreResponse that)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equals(getStoreId(), that.getStoreId()) && Objects.equals(getStoreName(),
                that.getStoreName()) && Objects.equals(getAddress(), that.getAddress())
                && Objects.equals(getPhoneNumber(), that.getPhoneNumber()) && Objects.equals(getEmail(),
                that.getEmail()) && Objects.equals(getOwnerName(), that.getOwnerName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getStoreId(), getStoreName(), getAddress(), getPhoneNumber(), getEmail(),
                getOwnerName());
    }
}
