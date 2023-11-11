package com.albert.commerce.application.service.product;


import static com.albert.commerce.domain.units.CommandChannelNames.PRODUCT_CREATE_CHANNEL;
import static com.albert.commerce.domain.units.CommandChannelNames.PRODUCT_DELETE_CHANNEL;
import static com.albert.commerce.domain.units.CommandChannelNames.PRODUCT_UPDATE_CHANNEL;

import com.albert.commerce.adapter.out.persistence.Money;
import com.albert.commerce.application.port.out.ProductRepository;
import com.albert.commerce.application.service.exception.error.ProductNotFoundException;
import com.albert.commerce.application.service.store.StoreService;
import com.albert.commerce.application.service.user.UserService;
import com.albert.commerce.application.service.utils.Success;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.store.StoreId;
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
    @ServiceActivator(inputChannel = PRODUCT_CREATE_CHANNEL)
    public String register(ProductCreateCommand productCreateCommand) {
        var userId = userService.getUserIdByEmail(productCreateCommand.getUserEmail());
        var store = storeService.getStoreByUserId(userId);
        var now = LocalDateTime.now();
        var product = createFrom(productCreateCommand, store, now);
        return productRepository.save(product)
                .getProductId()
                .getValue();
    }

    @Transactional
    @ServiceActivator(inputChannel = PRODUCT_UPDATE_CHANNEL)
    public Success upload(ProductUpdateCommand productUpdateCommand) {
        var userEmail = productUpdateCommand.getUserEmail();
        var productId = productUpdateCommand.getProductId();
        var product = getProduct(userEmail, productId);
        uploadProduct(productUpdateCommand, product);
        return Success.getInstance();
    }

    @Transactional
    @ServiceActivator(inputChannel = PRODUCT_DELETE_CHANNEL)
    public Success delete(ProductDeleteCommand productDeleteCommand) {
        var userEmail = productDeleteCommand.getUserEmail();
        var productId = productDeleteCommand.getProductId();
        var product = getProduct(userEmail, productId);
        productRepository.delete(product);
        return Success.getInstance();
    }

    public Product getProductById(ProductId productId) {
        return productRepository.findByProductId(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    public void checkProductExist(ProductId productId) {
        if (productRepository.existsById(productId)) {
            return;
        }
        throw new ProductNotFoundException();
    }

    public Product getProductByIdAndStoreId(ProductId productId, StoreId storeId) {
        return productRepository.findByIdAndStoreId(productId, storeId)
                .orElseThrow(ProductNotFoundException::new);
    }

    private ProductId getNewProductId() {
        return productRepository.nextId();
    }

    private Product getProduct(String email, String productIdValue) {
        var userId = userService.getUserIdByEmail(email);
        var storeId = storeService.getStoreIdByUserId(userId);
        var productId = ProductId.from(productIdValue);
        return getProductByIdAndStoreId(productId, storeId);
    }

    private Product createFrom(ProductCreateCommand productCreateCommand, Store store, LocalDateTime createdTime) {
        return new Product(
                getNewProductId(),
                store.getStoreId(),
                productCreateCommand.getProductName(),
                new Money(productCreateCommand.getPrice()),
                productCreateCommand.getDescription(),
                productCreateCommand.getBrand(),
                productCreateCommand.getCategory(),
                createdTime,
                createdTime
        );
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
}
