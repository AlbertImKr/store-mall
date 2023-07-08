package com.albert.commerce.product.command.application.dto;

import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.product.ProductNotFoundException;
import com.albert.commerce.product.command.application.ProductRequest;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.command.domain.ProductRepository;
import com.albert.commerce.user.command.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductCreatedResponse addProduct(Product product) {
        return ProductCreatedResponse.from(productRepository.save(product));
    }

    @Transactional
    public ProductResponse update(UserId userId, ProductId productId,
            ProductRequest productRequest) {
        Product product = productRepository.findByUserIdAndProductId(userId,
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
