package com.albert.commerce.product.application;

import com.albert.commerce.common.BusinessLinks;
import java.util.List;
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
public class ProductsResponse extends RepresentationModel<ProductsResponse> {

    public List<ProductResponse> productsResponse;

    @Builder
    private ProductsResponse(List<ProductResponse> productsResponse, Links links) {
        this.productsResponse = productsResponse;
        super.add(links);
    }

    public static ProductsResponse from(List<ProductResponse> productsResponse) {
        return ProductsResponse.builder()
                .productsResponse(productsResponse)
                .links(Links.of(BusinessLinks.MY_STORE))
                .build();
    }
}
