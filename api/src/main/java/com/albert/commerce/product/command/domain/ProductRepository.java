package com.albert.commerce.product.command.domain;

public interface ProductRepository {

    Product save(Product product);

    ProductId nextId();
}
