package com.albert.commerce.application.service;

import com.albert.commerce.adapter.out.persistence.Money;
import com.albert.commerce.application.port.out.ProductRepository;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.UserId;
import com.albert.commerce.exception.ProductNotFoundException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;
    private final StoreService storeService;

    @Transactional
    @ServiceActivator(inputChannel = "ProductCreateCommand")
    public String addProduct(ProductCreateCommand productCreateCommand) {
        UserId userId = userService.getUserIdByEmail(productCreateCommand.getUserEmail());
        Store store = storeService.getStoreByUserId(userId);

        Product product = Product.builder()
                .storeId(store.getStoreId())
                .productName(productCreateCommand.getProductName())
                .price(new Money(productCreateCommand.getPrice()))
                .description(productCreateCommand.getDescription())
                .brand(productCreateCommand.getBrand())
                .category(productCreateCommand.getCategory())
                .build();
        return productRepository.save(product)
                .getProductId()
                .getValue();
    }

    @Transactional
    @ServiceActivator(inputChannel = "ProductUpdateCommand")
    public void update(ProductUpdateCommand productUpdateCommand) {
        UserId userId = userService.getUserIdByEmail(productUpdateCommand.getUserEmail());
        Store store = storeService.getStoreByUserId(userId);
        StoreId storeId = store.getStoreId();

        ProductId productId = ProductId.from(productUpdateCommand.getProductId());
        Product product = productRepository.findByStoreIdAndProductId(storeId, productId)
                .orElseThrow(ProductNotFoundException::new);

        product.update(
                productUpdateCommand.getProductName(),
                new Money(productUpdateCommand.getPrice()),
                productUpdateCommand.getBrand(),
                productUpdateCommand.getCategory(),
                productUpdateCommand.getDescription(),
                LocalDateTime.now()
        );
    }

    @Transactional(readOnly = true)
    public Product getProductById(ProductId productId) {
        return productRepository.findByProductId(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public void checkId(ProductId productId) {
        if (productRepository.existsById(productId)) {
            return;
        }
        throw new ProductNotFoundException();
    }
}
