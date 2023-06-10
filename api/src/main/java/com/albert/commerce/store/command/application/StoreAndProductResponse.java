package com.albert.commerce.store.command.application;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.albert.commerce.product.ui.ProductController;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.query.StoreAndProduct;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class StoreAndProductResponse extends RepresentationModel<StoreAndProductResponse> {

    private StoreId storeId;
    private String storeName;

    public StoreAndProductResponse() {
    }

    @Builder
    public StoreAndProductResponse(StoreId storeId, String storeName) {
        this.storeId = storeId;
        this.storeName = storeName;
    }

    public static StoreAndProductResponse from(StoreAndProduct storeAndProduct) {
        Set<Link> collect = storeAndProduct.getProductIds().stream()
                .map(productId -> linkTo(ProductController.class).slash(productId.getId())
                        .withRel("product" + productId.getId()))
                .collect(Collectors.toSet());
        return StoreAndProductResponse.builder()
                .storeId(storeAndProduct.getStoreId())
                .storeName(storeAndProduct.getStoreName())
                .build()
                .add(collect);
    }


}
