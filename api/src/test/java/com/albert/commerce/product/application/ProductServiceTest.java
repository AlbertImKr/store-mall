package com.albert.commerce.product.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.command.domain.ProductRepository;
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
        ProductRequest productRequest = new ProductRequest("testProductName",
                1000, "test", "testBrand", "test");
        Product product = productRequest.toProduct();
        given(productRepository.save(any(Product.class))).willReturn(product);

        // when
        productService.addProduct(productRequest);

        // then
        verify(productRepository).save(any(Product.class));
    }

    @DisplayName("product를 제거한다")
    @Test
    void removeProduct() {
        // given
        ProductId productId = new ProductId();
        given(productRepository.deleteProductByProductId(any(ProductId.class))).willReturn(true);

        // when
        productService.removeProduct(productId);

        // then
        verify(productRepository).deleteProductByProductId(any(ProductId.class));
    }
}
