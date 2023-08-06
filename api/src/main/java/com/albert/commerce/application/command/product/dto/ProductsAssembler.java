package com.albert.commerce.application.command.product.dto;

import com.albert.commerce.domain.query.product.ProductData;
import com.albert.commerce.ui.command.product.ProductController;
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
