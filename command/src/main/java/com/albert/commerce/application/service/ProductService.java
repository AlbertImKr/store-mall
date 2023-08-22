package com.albert.commerce.application.service;

import com.albert.commerce.adapter.out.persistence.Money;
import com.albert.commerce.application.port.out.ProductRepository;
import com.albert.commerce.application.service.command.ProductCreateCommand;
import com.albert.commerce.application.service.command.ProductUpdateCommand;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.exception.error.ProductNotFoundException;
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
    public String register(ProductCreateCommand productCreateCommand) {
        var userId = userService.getUserIdByEmail(productCreateCommand.getUserEmail());
        var store = storeService.getStoreByUserId(userId);
        var product = Product.from(getNewProductId(), productCreateCommand, store, LocalDateTime.now());
        return productRepository.save(product)
                .getProductId()
                .getValue();
    }

    @Transactional
    @ServiceActivator(inputChannel = "ProductUpdateCommand")
    public void upload(ProductUpdateCommand productUpdateCommand) {
        var userId = userService.getUserIdByEmail(productUpdateCommand.getUserEmail());
        var storeId = storeService.getStoreIdByUserId(userId);

        var productId = ProductId.from(productUpdateCommand.getProductId());
        var product = productRepository.findByStoreIdAndProductId(storeId, productId)
                .orElseThrow(ProductNotFoundException::new);
        uploadProduct(productUpdateCommand, product);
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

    private static void uploadProduct(ProductUpdateCommand productUpdateCommand, Product product) {
        product.upload(
                productUpdateCommand.getProductName(),
                new Money(productUpdateCommand.getPrice()),
                productUpdateCommand.getBrand(),
                productUpdateCommand.getCategory(),
                productUpdateCommand.getDescription(),
                LocalDateTime.now()
        );
    }

    private ProductId getNewProductId() {
        return productRepository.nextId();
    }
}
