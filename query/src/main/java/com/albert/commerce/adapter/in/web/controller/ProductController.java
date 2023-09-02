package com.albert.commerce.adapter.in.web.controller;

import com.albert.commerce.adapter.in.web.facade.ProductFacade;
import com.albert.commerce.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductFacade productFacade;

    @GetMapping("/products/{id}")
    public Product getById(@PathVariable String id) {
        return productFacade.getById(id);
    }
}
