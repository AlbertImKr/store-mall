package com.albert.commerce.api.store.command.application.dto;

import com.albert.commerce.api.common.domain.DomainId;
import com.albert.commerce.api.common.units.BusinessLinks;
import com.albert.commerce.api.store.command.domain.Store;
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
public class ConsumerStoreResponse extends RepresentationModel<ConsumerStoreResponse> {

    private DomainId storeId;
    private String storeName;


    @Builder
    private ConsumerStoreResponse(DomainId storeId, String storeName, Links links) {
        this.storeId = storeId;
        this.storeName = storeName;
        super.add(links);
    }

    public static ConsumerStoreResponse from(Store store) {
        return ConsumerStoreResponse.builder()
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .links(Links.of(BusinessLinks.GET_STORE))
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConsumerStoreResponse that)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equals(getStoreId(), that.getStoreId()) && Objects.equals(getStoreName(),
                that.getStoreName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getStoreId(), getStoreName());
    }
}
