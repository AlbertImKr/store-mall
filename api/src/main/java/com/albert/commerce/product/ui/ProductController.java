package com.albert.commerce.product.ui;

import com.albert.commerce.product.command.application.ProductService;
import com.albert.commerce.product.command.application.dto.ProductRequest;
import com.albert.commerce.product.command.domain.ProductId;
import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Map<String, String>> addProduct(
            @RequestBody ProductRequest productRequest,
            Principal principal) {
        String userEmail = principal.getName();
        ProductId productId = productService.addProduct(userEmail, productRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("productId", productId.getId()));
    }


    @PutMapping(value = "/{productId}")
    public ResponseEntity<Map<String, String>> updateProduct(Principal principal,
            @PathVariable String productId, @RequestBody ProductRequest productRequest) {
        String userEmail = principal.getName();
        productService.update(userEmail, ProductId.from(productId), productRequest);
        return ResponseEntity.ok(Map.of("productId", productId));
    }
}
