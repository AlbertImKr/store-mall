package com.albert.commerce.product.application;

import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.ui.ProductController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProductAssembler extends
        RepresentationModelAssemblerSupport<Product, ProductResponse> {

    public ProductAssembler() {
        super(ProductController.class, ProductResponse.class);
    }

    @Override
    public ProductResponse toModel(Product product) {
        ProductResponse productResponse = ProductResponse.from(product);
        productResponse.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).findAll(null, null))
                .slash(productResponse.getProductId().getId()).withSelfRel());
        return productResponse;
    }

}
