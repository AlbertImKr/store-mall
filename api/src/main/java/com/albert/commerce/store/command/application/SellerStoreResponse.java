package com.albert.commerce.store.command.application;

import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
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

    @Builder
    private SellerStoreResponse(StoreId storeId, String storeName, Links links) {
        this.storeId = storeId;
        this.storeName = storeName;
        super.add(links);
    }

    public static SellerStoreResponse from(Store store) {
        return SellerStoreResponse.builder()
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .links(Links.of(BusinessLinks.MY_STORE))
                .build();
    }


}
