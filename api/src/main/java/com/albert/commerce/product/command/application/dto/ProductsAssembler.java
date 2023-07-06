package com.albert.commerce.product.command.application.dto;

import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.ui.ProductController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProductsAssembler extends
        RepresentationModelAssemblerSupport<Product, ProductResponse> {

    public ProductsAssembler() {
        super(ProductController.class, ProductResponse.class);
    }

    @Override
    public ProductResponse toModel(Product product) {
        return ProductResponse.from(product);
    }

}
