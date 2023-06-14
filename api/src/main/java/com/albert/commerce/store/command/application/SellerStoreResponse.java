package com.albert.commerce.store.command.application;

import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class SellerStoreResponse extends RepresentationModel<SellerStoreResponse> {

    private StoreId storeId;
    private String storeName;

    public SellerStoreResponse() {
    }

    @Builder
    public SellerStoreResponse(StoreId storeId, String storeName, Links links) {
        this.storeId = storeId;
        this.storeName = storeName;
        super.add(links);
    }

    public static SellerStoreResponse from(Store store) {
        return SellerStoreResponse.builder()
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .links(Links.of(StoreLinks.MY_STORE))
                .build();
    }


}
