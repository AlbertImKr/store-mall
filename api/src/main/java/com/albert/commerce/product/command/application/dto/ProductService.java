package com.albert.commerce.product.command.application.dto;

import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.product.ProductNotFoundException;
import com.albert.commerce.product.command.application.ProductRequest;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.command.domain.ProductRepository;
import com.albert.commerce.store.StoreNotFoundException;
import com.albert.commerce.store.query.domain.StoreData;
import com.albert.commerce.store.query.domain.StoreDataDao;
import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.query.domain.UserDao;
import com.albert.commerce.user.query.domain.UserData;
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
    public ProductCreatedResponse addProduct(String userEmail, ProductRequest productRequest) {
        UserData user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        StoreData store = storeDataDao.getMyStoreByUserEmail(user.getUserId()).orElseThrow(
                StoreNotFoundException::new);
        return ProductCreatedResponse.from(productRepository.save(productRequest.toProduct(store.getStoreId())));
    }

    @Transactional
    public ProductResponse update(String userEmail, ProductId productId,
            ProductRequest productRequest) {
        UserData user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Product product = productRepository.findByUserIdAndProductId(user.getUserId(),
                productId).orElseThrow(ProductNotFoundException::new);

        Product changedProduct = product.update(
                productRequest.productName(),
                new Money(productRequest.price()),
                productRequest.brand(),
                productRequest.category(),
                productRequest.description());
        return ProductResponse.from(changedProduct).add(BusinessLinks.MY_STORE);
    }
}
