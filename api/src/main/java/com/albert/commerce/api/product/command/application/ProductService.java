package com.albert.commerce.api.product.command.application;

import com.albert.commerce.api.common.infra.persistence.Money;
import com.albert.commerce.api.product.ProductNotFoundException;
import com.albert.commerce.api.product.command.application.dto.ProductRequest;
import com.albert.commerce.api.product.command.domain.Product;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.product.command.domain.ProductRepository;
import com.albert.commerce.api.store.StoreNotFoundException;
import com.albert.commerce.api.store.query.domain.StoreData;
import com.albert.commerce.api.store.query.domain.StoreDataDao;
import com.albert.commerce.api.user.UserNotFoundException;
import com.albert.commerce.api.user.query.domain.UserDao;
import com.albert.commerce.api.user.query.domain.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserDao userDao;
    private final StoreDataDao storeDataDao;

    @Transactional
    public ProductId addProduct(String userEmail, ProductRequest productRequest) {
        UserData user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        StoreData store = storeDataDao.getMyStoreByUserEmail(user.getUserId()).orElseThrow(
                StoreNotFoundException::new);

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
        UserData user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Product product = productRepository.findByUserIdAndProductId(user.getUserId(),
                productId).orElseThrow(ProductNotFoundException::new);

        product.update(
                productRequest.productName(),
                new Money(productRequest.price()),
                productRequest.brand(),
                productRequest.category(),
                productRequest.description());
    }
}
