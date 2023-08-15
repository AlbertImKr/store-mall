package com.albert.commerce.api.product.query.application;

import com.albert.commerce.api.product.command.application.dto.ProductResponse;
import com.albert.commerce.api.product.command.application.dto.ProductsAssembler;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.product.query.application.dto.ProductUpdateRequest;
import com.albert.commerce.api.product.query.domain.ProductDao;
import com.albert.commerce.api.product.query.domain.ProductData;
import com.albert.commerce.api.store.query.application.StoreFacade;
import com.albert.commerce.api.store.query.domain.StoreData;
import com.albert.commerce.api.user.query.domain.UserDao;
import com.albert.commerce.common.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ProductFacade {

    private final PagedResourcesAssembler<ProductData> pagedResourcesAssembler;
    private final ProductsAssembler productsAssembler;
    private final ProductDao productDao;
    private final StoreFacade storeFacade;
    private final UserDao userDao;

    @Transactional(readOnly = true)
    public PagedModel<ProductResponse> findProductsByUserId(String userEmail,
            Pageable pageable) {
        StoreData store = storeFacade.getMyStoreByUserEmail(userEmail);
        Page<ProductData> products = productDao.findByStoreId(store.getStoreId(), pageable);
        return pagedResourcesAssembler.toModel(products, productsAssembler);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductId(ProductId productId) {
        ProductData product = productDao.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
        return ProductResponse.from(product);
    }

    @Transactional(readOnly = true)
    public void checkId(ProductId productId) {
        if (!productDao.exists(productId)) {
            throw new ProductNotFoundException();
        }
    }

    @Transactional
    public void save(ProductData productData) {
        productDao.save(productData);
    }

    @Transactional
    public void update(ProductUpdateRequest productUpdateRequest) {
        ProductData productData = productDao.findById(productUpdateRequest.getProductId())
                .orElseThrow(ProductNotFoundException::new);
        productData.update(
                productUpdateRequest.getProductName(),
                productUpdateRequest.getDescription(),
                productUpdateRequest.getBrand(),
                productUpdateRequest.getPrice(),
                productUpdateRequest.getCategory(),
                productUpdateRequest.getUpdateTime()
        );
    }
}
