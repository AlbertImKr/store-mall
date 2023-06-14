package com.albert.commerce.product.ui;

import com.albert.commerce.product.application.ProductRequest;
import com.albert.commerce.product.application.ProductResponse;
import com.albert.commerce.product.application.ProductService;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreUserId;
import com.albert.commerce.store.query.StoreDao;
import com.albert.commerce.store.ui.StoreController;
import com.albert.commerce.user.command.domain.UserId;
import com.albert.commerce.user.query.UserDataDao;
import com.albert.commerce.user.query.UserProfileResponse;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
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
                                WebMvcLinkBuilder.methodOn(StoreController.class).getMyStore(null))
                        .withRel("my-store"),
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(StoreController.class)
                                        .addStore(null, null, null))
                        .withRel("add-store"),
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(StoreController.class)
                                        .getStore(null))
                        .withRel("other-store")
        );

        return ResponseEntity.created(selfRel.toUri()).body(productResponse);
    }
}
