package com.albert.commerce.application.service.product;

import com.albert.commerce.adapter.out.persistence.Money;
import com.albert.commerce.application.port.out.ProductRepository;
import com.albert.commerce.application.service.exception.error.ProductNotFoundException;
import com.albert.commerce.application.service.store.StoreService;
import com.albert.commerce.application.service.user.UserService;
import com.albert.commerce.application.service.utils.Success;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.product.ProductId;
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
    public Success upload(ProductUpdateCommand productUpdateCommand) {
        var userId = userService.getUserIdByEmail(productUpdateCommand.getUserEmail());
        var storeId = storeService.getStoreIdByUserId(userId);

        var productId = ProductId.from(productUpdateCommand.getProductId());
        var product = productRepository.findByStoreIdAndProductId(storeId, productId)
                .orElseThrow(ProductNotFoundException::new);
        uploadProduct(productUpdateCommand, product);
        return Success.getInstance();
    }

    @Transactional
    @ServiceActivator(inputChannel = "ProductDeleteCommand")
    public Success delete(ProductDeleteCommand productDeleteCommand) {
        var userId = userService.getUserIdByEmail(productDeleteCommand.getUserEmail());
        var storeId = storeService.getStoreIdByUserId(userId);

        var productId = ProductId.from(productDeleteCommand.getProductId());
        var product = productRepository.findByStoreIdAndProductId(storeId, productId)
                .orElseThrow(ProductNotFoundException::new);
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
