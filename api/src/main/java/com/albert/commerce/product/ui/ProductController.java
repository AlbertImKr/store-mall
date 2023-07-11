package com.albert.commerce.product.ui;

import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.product.command.application.ProductRequest;
import com.albert.commerce.product.command.application.dto.ProductCreatedResponse;
import com.albert.commerce.product.command.application.dto.ProductResponse;
import com.albert.commerce.product.command.application.dto.ProductService;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.query.application.ProductFacade;
import com.albert.commerce.store.StoreNotFoundException;
import com.albert.commerce.store.query.domain.StoreData;
import com.albert.commerce.store.query.domain.StoreDataDao;
import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.query.domain.UserDao;
import com.albert.commerce.user.query.domain.UserData;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/products", produces = MediaTypes.HAL_JSON_VALUE)
public class ProductController {

    private final ProductService productService;
    private final ProductFacade productFacade;
    private final UserDao userDao;
    private final StoreDataDao storeDataDao;

    @PostMapping
    public ResponseEntity<ProductCreatedResponse> addProduct(
            @RequestBody ProductRequest productRequest,
            Principal principal) {
        String userEmail = principal.getName();
        UserData user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        StoreData store = storeDataDao.findStoreByUserId(user.getUserId()).orElseThrow(
                StoreNotFoundException::new);
        ProductCreatedResponse productResponse = productService.addProduct(
                productRequest.toProduct(store.getStoreId()));
        return ResponseEntity.created(BusinessLinks.MY_STORE.toUri())
                .body(productResponse);
    }

    @GetMapping
    public ResponseEntity<PagedModel<ProductResponse>> getAllProducts(Principal principal,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page
    ) {
        UserData user = userDao.findByEmail(principal.getName())
                .orElseThrow(UserNotFoundException::new);
        Pageable pageable = PageRequest.of(
                page == null ? 0 : page,
                size == null ? 0 : size
        );
        return ResponseEntity.ok(
                productFacade.findProductsByUserId(user.getUserId(), pageable));
    }

    @PutMapping(value = "/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(Principal principal,
            @PathVariable String productId, @RequestBody ProductRequest productRequest) {
        String userEmail = principal.getName();
        UserData user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        return ResponseEntity.ok(
                productService.update(user.getUserId(), ProductId.from(productId), productRequest));
    }


    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable ProductId productId) {
        return ResponseEntity.ok(productFacade.findById(productId));
    }
}
