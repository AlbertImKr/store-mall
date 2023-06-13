package com.albert.commerce.product.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductRepository;
import com.albert.commerce.store.command.domain.StoreId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

    ProductService productService;
    ProductRepository productRepository;

    @BeforeEach
    void setProductService() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @DisplayName("새로온 product를 추가한다")
    @Test
    void addProduct() {
        // given
        StoreId storeId = new StoreId();
        ProductRequest productRequest = new ProductRequest("testProductName",
                1000, "test", "testBrand", "test");
        Product product = productRequest.toProduct(storeId);
        given(productRepository.save(any(Product.class))).willReturn(product);

        // when
        productService.addProduct(productRequest, storeId);

        // then
        verify(productRepository).save(any(Product.class));
    }
}
