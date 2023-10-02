package com.albert.commerce.application.service.product;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.albert.commerce.application.port.out.ProductRepository;
import com.albert.commerce.application.service.ApplicationFixture;
import com.albert.commerce.application.service.exception.error.ProductNotFoundException;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.StoreId;
import java.util.Optional;
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


    @DisplayName("상품 아이디로 상품을 조회시 상품이 존재하지 않으면 예외를 던진다")
    @Test
    void get_product_by_id_when_product_does_not_exist_then_throw_exception() {
        // given
        ProductId notExistsProductId = ApplicationFixture.getNotExistsProductId();
        given(productRepository.findByProductId(notExistsProductId))
                .willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> productService.getProductById(notExistsProductId))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("상품 아이디를 거증 할 때 상품이 존재하지 않으면 예외를 던진다")
    @Test
    void check_product_exist_when_product_does_not_exist_then_throw_exception() {
        // given
        ProductId notExistsProductId = ApplicationFixture.getNotExistsProductId();
        given(productRepository.existsById(notExistsProductId))
                .willReturn(false);

        // when
        assertThatThrownBy(() -> productService.checkProductExist(notExistsProductId))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("상품 아이디와 스토어 아이더로 상품을 조회시 상품이 존재하지 않으면 예외를 던진다")
    @Test
    void get_product_by_id_and_store_id_when_product_does_not_exist_then_throw_exception() {
        // given
        ProductId notExistsProductId = ApplicationFixture.getNotExistsProductId();
        StoreId notExistsStoreId = ApplicationFixture.getNotExistsStoreId();
        given(productRepository.findByIdAndStoreId(notExistsProductId, notExistsStoreId))
                .willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> productService.getProductByIdAndStoreId(notExistsProductId, notExistsStoreId))
                .isInstanceOf(ProductNotFoundException.class);
    }
}
