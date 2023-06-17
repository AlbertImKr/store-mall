package com.albert.commerce.product.command.application;

import com.albert.commerce.common.units.BusinessLinks;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ProductsResponse extends RepresentationModel<ProductsResponse> {

    public Page<ProductResponse> productsResponse;

    @Builder
    private ProductsResponse(Page<ProductResponse> productsResponse, Links links) {
        this.productsResponse = productsResponse;
        super.add(links);
    }

    public static ProductsResponse from(Page<ProductResponse> productsResponse) {
        return ProductsResponse.builder()
                .productsResponse(productsResponse)
                .links(Links.of(BusinessLinks.MY_STORE))
                .build();
    }


}
