package com.albert.commerce.product.query;

import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDao {

    Page<Product> findProductsByUserEmail(String userEmail, Pageable pageable);

    Product findByUserEmailAndProductId(String name, ProductId productId);

    Product findById(ProductId productId);

    List<Product> findProductsByProductsId(List<ProductId> productsId, StoreId storeId);
}
