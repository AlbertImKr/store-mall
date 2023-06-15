package com.albert.commerce.product.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.albert.commerce.common.model.SequenceGeneratorImpl;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.command.domain.ProductRepository;
import com.albert.commerce.store.command.domain.StoreId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

    ProductService productService;
    ProductRepository productRepository;
    SequenceGeneratorImpl sequenceGenerator;

    @BeforeEach
    void setProductService() {
        productRepository = mock(ProductRepository.class);
        sequenceGenerator = new SequenceGeneratorImpl();
        productService = new ProductService(productRepository, sequenceGenerator);
    }

    @DisplayName("새로온 product를 추가한다")
    @Test
    void addProduct() {
        // given
        StoreId storeId = new StoreId(sequenceGenerator.generate());
        ProductId productId = new ProductId(sequenceGenerator.generate());
        ProductRequest productRequest = new ProductRequest("testProductName",
                1000, "test", "testBrand", "test");
        Product product = productRequest.toProduct(storeId, productId);
        given(productRepository.save(any(Product.class))).willReturn(product);

        // when
        productService.addProduct(productRequest, storeId);

        // then
        verify(productRepository).save(any(Product.class));
    }
}
