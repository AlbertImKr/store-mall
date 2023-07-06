package com.albert.commerce.product.command.application.dto;

import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.product.command.application.ProductRequest;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.command.domain.ProductRepository;
import com.albert.commerce.product.infra.persistence.ProductNotFoundException;
import com.albert.commerce.product.query.ProductDao;
import com.albert.commerce.store.StoreNotFoundException;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.query.domain.StoreDao;
import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.query.domain.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserDao userDao;
    private final StoreDao storeDao;
    private final ProductDao productDao;

    @Transactional
    public ProductCreatedResponse addProduct(ProductRequest productRequest, String userEmail) {
        User user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Store store = storeDao.findStoreByUserId(user.getId())
                .orElseThrow(StoreNotFoundException::new);
        Product product = productRepository.save(
                productRequest.toProduct(store.getStoreId(), productRepository.nextId()));
        return ProductCreatedResponse.from(product);
    }

    @Transactional
    public ProductResponse update(String userEmail, ProductId productId,
            ProductRequest productRequest) {
        if (!userDao.existsByEmail(userEmail)) {
            throw new UserNotFoundException();
        }
        Product product = productDao.findByUserEmailAndProductId(userEmail,
                productId).orElseThrow(ProductNotFoundException::new);

        Product changedProduct = product.update(
                productRequest.productName(),
                productRequest.price(),
                productRequest.brand(),
                productRequest.category(),
                productRequest.description());
        return ProductResponse.from(changedProduct).add(BusinessLinks.MY_STORE);
    }
}
