package com.albert.commerce.application.service.product;

import static com.albert.commerce.domain.units.MessageChannelName.PRODUCT_CREATE_CHANNEL;
import static com.albert.commerce.domain.units.MessageChannelName.PRODUCT_DELETE_CHANNEL;
import static com.albert.commerce.domain.units.MessageChannelName.PRODUCT_UPDATE_CHANNEL;

import com.albert.commerce.adapter.out.persistence.Money;
import com.albert.commerce.application.port.out.ProductRepository;
import com.albert.commerce.application.service.exception.error.ProductNotFoundException;
import com.albert.commerce.application.service.store.StoreService;
import com.albert.commerce.application.service.user.UserService;
import com.albert.commerce.application.service.utils.Success;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.product.ProductId;
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
        var product = Product.from(getNewProductId(), productCreateCommand, store, now);
        return productRepository.save(product)
                .getProductId()
                .getValue();
    }

    @Transactional
    @ServiceActivator(inputChannel = PRODUCT_UPDATE_CHANNEL)
    public Success upload(ProductUpdateCommand productUpdateCommand) {
        var product = getProduct(productUpdateCommand.getUserEmail(), productUpdateCommand.getProductId());
        uploadProduct(productUpdateCommand, product);
        return Success.getInstance();
    }

    @Transactional
    @ServiceActivator(inputChannel = PRODUCT_DELETE_CHANNEL)
    public Success delete(ProductDeleteCommand productDeleteCommand) {
        var product = getProduct(productDeleteCommand.getUserEmail(), productDeleteCommand.getProductId());
        productRepository.delete(product);
        return Success.getInstance();
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

    private ProductId getNewProductId() {
        return productRepository.nextId();
    }

    private Product getProduct(String productUpdateCommand, String productUpdateCommand1) {
        var userId = userService.getUserIdByEmail(productUpdateCommand);
        var storeId = storeService.getStoreIdByUserId(userId);
        var productId = ProductId.from(productUpdateCommand1);
        var product = getProduct(storeId, productId);
        return product;
    }

    private Product getProduct(StoreId storeId, ProductId productId) {
        return productRepository.findByStoreIdAndProductId(storeId, productId)
                .orElseThrow(ProductNotFoundException::new);
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
