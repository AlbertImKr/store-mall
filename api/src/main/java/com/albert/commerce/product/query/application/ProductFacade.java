package com.albert.commerce.product.query.application;

import com.albert.commerce.product.ProductNotFoundException;
import com.albert.commerce.product.command.application.dto.ProductResponse;
import com.albert.commerce.product.command.application.dto.ProductsAssembler;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.query.domain.ProductDao;
import com.albert.commerce.product.query.domain.ProductData;
import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.query.domain.UserDao;
import com.albert.commerce.user.query.domain.UserData;
import java.util.List;
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
    private final UserDao userDao;

    @Transactional(readOnly = true)
    public PagedModel<ProductResponse> findProductsByUserId(String userEmail,
            Pageable pageable) {
        UserData userData = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Page<ProductData> products = productDao.findProductsByUserId(userData.getUserId(), pageable);
        return pagedResourcesAssembler.toModel(products, productsAssembler);
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(ProductId productId) {
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

    @Transactional(readOnly = true)
    public long getAmount(List<ProductId> productsId) {
        if (!productDao.isValidProductsId(productsId)) {
            throw new ProductNotFoundException();
        }
        return productDao.getAmount(productsId);
    }
}
