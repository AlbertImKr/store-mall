package com.albert.commerce.product.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.albert.commerce.product.command.application.ProductRequest;
import com.albert.commerce.product.command.application.ProductService;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.command.domain.ProductRepository;
import com.albert.commerce.store.command.domain.StoreId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductService productService;
    @Mock
    ProductRepository productRepository;

    @DisplayName("새로온 product를 추가한다")
    @Test
    void addProduct() {
        // given
        StoreId storeId = mock(StoreId.class);
        ProductId productId = mock(ProductId.class);
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
