package com.albert.commerce.api.product.command.application.dto;

import com.albert.commerce.api.product.query.domain.ProductData;
import com.albert.commerce.api.product.ui.ProductController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProductsAssembler extends
        RepresentationModelAssemblerSupport<ProductData, ProductResponse> {

    public ProductsAssembler() {
        super(ProductController.class, ProductResponse.class);
    }

    @Override
    public ProductResponse toModel(ProductData product) {
        return ProductResponse.from(product);
    }
}
