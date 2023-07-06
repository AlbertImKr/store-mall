package com.albert.commerce.product.ui;

import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.product.command.application.ProductRequest;
import com.albert.commerce.product.command.application.dto.ProductCreatedResponse;
import com.albert.commerce.product.command.application.dto.ProductResponse;
import com.albert.commerce.product.command.application.dto.ProductService;
import com.albert.commerce.product.command.application.dto.ProductsAssembler;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.query.ProductDao;
import com.albert.commerce.store.command.application.dto.SellerStoreResponse;
import com.albert.commerce.store.query.application.StoreFacade;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.query.domain.UserDao;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/products", produces = MediaTypes.HAL_JSON_VALUE)
public class ProductController {

    private final ProductService productService;
    private final StoreFacade storeFacade;
    private final UserDao userDao;
    private final ProductDao productDao;
    private final PagedResourcesAssembler<Product> pagedResourcesAssembler;
    private final ProductsAssembler productsAssembler;

    @PostMapping
    public ResponseEntity<ProductCreatedResponse> addProduct(
            @RequestBody ProductRequest productRequest,
            Principal principal) {
        User user = userDao.findByEmail(principal.getName());
        SellerStoreResponse store = storeFacade.findStoreByUserEmail(user.getEmail());
        ProductCreatedResponse productResponse = productService.addProduct(productRequest,
                store.getStoreId());
        return ResponseEntity.created(BusinessLinks.MY_STORE.toUri())
                .body(productResponse);
    }

    @GetMapping
    public ResponseEntity<PagedModel<ProductResponse>> getAllProducts(Principal principal,
            Pageable pageable) {
        Page<Product> products =
                productDao.findProductsByUserEmail(principal.getName(), pageable);

        PagedModel<ProductResponse> productResponses = pagedResourcesAssembler
                .toModel(products, productsAssembler);

        return ResponseEntity.ok(productResponses);
    }

    @PutMapping(value = "/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(Principal principal,
            @PathVariable ProductId productId, @RequestBody ProductRequest productRequest) {
        Product product = productDao.findByUserEmailAndProductId(principal.getName(), productId);

        ProductResponse productResponse = productService.update(product, productRequest);

        productResponse.add(BusinessLinks.MY_STORE);
        return ResponseEntity.ok(productResponse);
    }


    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable ProductId productId) {
        Product product = productDao.findById(productId);
        return ResponseEntity.ok(ProductResponse.from(product));
    }
}
