package com.albert.commerce.api.product.command.application;

import com.albert.commerce.api.product.command.application.dto.ProductRequest;
import com.albert.commerce.api.product.command.domain.Product;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.product.command.domain.ProductRepository;
import com.albert.commerce.api.store.command.application.StoreService;
import com.albert.commerce.api.store.command.domain.Store;
import com.albert.commerce.api.user.command.application.UserService;
import com.albert.commerce.api.user.command.domain.UserId;
import com.albert.commerce.common.exception.ProductNotFoundException;
import com.albert.commerce.common.infra.persistence.Money;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;
    private final StoreService storeService;

    @Transactional
    public ProductId addProduct(String userEmail, ProductRequest productRequest) {
        UserId userId = userService.findIdByEmail(userEmail);
        Store store = storeService.getStoreByUserEmail(userId);

        Product product = Product.builder()
                .storeId(store.getStoreId())
                .productName(productRequest.productName())
                .price(new Money(productRequest.price()))
                .description(productRequest.description())
                .brand(productRequest.brand())
                .category(productRequest.category())
                .build();
        return productRepository.save(product).getProductId();
    }

    @Transactional
    public void update(String userEmail, ProductId productId,
            ProductRequest productRequest) {
        UserId userId = userService.findIdByEmail(userEmail);
        Product product = productRepository.findByUserIdAndProductId(userId,
                productId).orElseThrow(ProductNotFoundException::new);

        product.update(
                productRequest.productName(),
                new Money(productRequest.price()),
                productRequest.brand(),
                productRequest.category(),
                productRequest.description(),
                LocalDateTime.now()
        );
    }

    public Product getProductById(ProductId productId) {
        return productRepository.findByProductId(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    public void checkId(ProductId productId) {
        if (productRepository.existsById(productId)) {
            return;
        }
        throw new ProductNotFoundException();
    }
}
