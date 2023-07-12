package com.albert.commerce.product.query.application;

import com.albert.commerce.product.ProductNotFoundException;
import com.albert.commerce.product.command.application.dto.ProductResponse;
import com.albert.commerce.product.command.application.dto.ProductsAssembler;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.query.domain.ProductDao;
import com.albert.commerce.product.query.domain.ProductData;
import com.albert.commerce.user.command.domain.UserId;
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

    @Transactional(readOnly = true)
    public PagedModel<ProductResponse> findProductsByUserId(UserId userId,
            Pageable pageable) {
        Page<ProductData> products = productDao.findProductsByUserId(userId, pageable);
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
