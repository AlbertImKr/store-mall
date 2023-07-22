package com.albert.commerce.product.ui;

import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.product.command.application.ProductService;
import com.albert.commerce.product.command.application.dto.ProductCreatedResponse;
import com.albert.commerce.product.command.application.dto.ProductRequest;
import com.albert.commerce.product.command.application.dto.ProductResponse;
import com.albert.commerce.product.command.domain.ProductId;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<ProductCreatedResponse> addProduct(
            @RequestBody ProductRequest productRequest,
            Principal principal) {
        String userEmail = principal.getName();
        ProductCreatedResponse productResponse = productService.addProduct(userEmail,
                productRequest);
        return ResponseEntity.created(BusinessLinks.MY_STORE.toUri())
                .body(productResponse);
    }


    @PutMapping(value = "/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(Principal principal,
            @PathVariable String productId, @RequestBody ProductRequest productRequest) {
        String userEmail = principal.getName();
        return ResponseEntity.ok(
                productService.update(userEmail, ProductId.from(productId), productRequest));
    }


}
