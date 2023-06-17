package com.albert.commerce.product.command.application;

import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.ui.ProductController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProductAssembler extends
        RepresentationModelAssemblerSupport<Product, ProductResponse> {

    public ProductAssembler() {
        super(ProductController.class, ProductResponse.class);
    }

    private static Link getProductSelfRefLink(ProductResponse productResponse) {
        return WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).findAll(null, null))
                .slash(productResponse.getProductId().getId()).withSelfRel();
    }

    @Override
    public ProductResponse toModel(Product product) {
        ProductResponse productResponse = ProductResponse.from(product);
        productResponse.add(getProductSelfRefLink(productResponse));
        return productResponse;
    }

}
