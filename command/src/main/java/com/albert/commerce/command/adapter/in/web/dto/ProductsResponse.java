package com.albert.commerce.command.adapter.in.web.dto;

import java.util.Objects;
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

    private Page<ProductResponse> pageProductsResponse;

    @Builder
    private ProductsResponse(Page<ProductResponse> pageProductsResponse, Links links) {
        this.pageProductsResponse = pageProductsResponse;
        super.add(links);
    }

    public static ProductsResponse from(Page<ProductResponse> productsResponse) {
        return ProductsResponse.builder()
                .pageProductsResponse(productsResponse)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductsResponse that)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equals(getPageProductsResponse(), that.getPageProductsResponse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPageProductsResponse());
    }
}
