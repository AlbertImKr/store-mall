package com.albert.commerce.command.application.service;

import com.albert.commerce.command.adapter.in.web.dto.ProductRequest;
import com.albert.commerce.command.application.port.out.ProductRepository;
import com.albert.commerce.command.domain.product.Product;
import com.albert.commerce.command.domain.product.ProductId;
import com.albert.commerce.command.domain.store.Store;
import com.albert.commerce.command.domain.user.UserId;
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
        Store store = storeService.getStoreByUserId(userId);

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
        Store store = storeService.getStoreByUserId(userId);
        Product product = productRepository.findByStoreIdAndProductId(store.getStoreId(),
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
