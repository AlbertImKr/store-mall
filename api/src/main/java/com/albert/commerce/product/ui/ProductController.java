package com.albert.commerce.product.ui;

import com.albert.commerce.product.application.ProductRequest;
import com.albert.commerce.product.application.ProductResponse;
import com.albert.commerce.product.application.ProductService;
import com.albert.commerce.product.application.ProductsResponse;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.query.ProductDao;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreUserId;
import com.albert.commerce.store.query.StoreDao;
import com.albert.commerce.store.ui.ConsumerStoreController;
import com.albert.commerce.store.ui.SellerStoreController;
import com.albert.commerce.user.command.domain.UserId;
import com.albert.commerce.user.query.UserDataDao;
import com.albert.commerce.user.query.UserProfileResponse;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/products", produces = MediaTypes.HAL_JSON_VALUE)
public class ProductController {

    private final ProductService productService;
    private final UserDataDao userDataDao;
    private final StoreDao storeDao;
    private final ProductDao productDao;

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest,
            Principal principal) {
        String email = principal.getName();
        UserProfileResponse user = userDataDao.findByEmail(email).orElseThrow();
        UserId id = user.getId();
        Store store = storeDao.findByStoreUserId(new StoreUserId(id)).orElseThrow();
        ProductResponse productResponse = productService
                .addProduct(productRequest, store.getStoreId());
        ProductId productId = productResponse.getProductId();
        Link selfRel = WebMvcLinkBuilder
                .linkTo(ProductController.class).slash(productId.getId()).withSelfRel();

        productResponse.add(selfRel,
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(SellerStoreController.class).getMyStore(null))
                        .withRel("my-store"),
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(SellerStoreController.class)
                                        .createStore(null, null, null))
                        .withRel("add-store"),
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(ConsumerStoreController.class)
                                        .getStore(null))
                        .withRel("other-store")
        );

        return ResponseEntity.created(selfRel.toUri()).body(productResponse);
    }

    @GetMapping
    public ResponseEntity<ProductsResponse> findProduct(Principal principal) {
        String email = principal.getName();
        ProductsResponse products = productDao.findProductsByUserEmail(email);
        Link selfRel = WebMvcLinkBuilder
                .linkTo(ProductController.class).withSelfRel();
        products.add(selfRel);
        return ResponseEntity.ok(products);
    }
}
