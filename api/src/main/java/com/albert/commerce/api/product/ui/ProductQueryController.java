package com.albert.commerce.api.product.ui;

import com.albert.commerce.api.product.command.application.dto.ProductResponse;
import com.albert.commerce.api.product.query.application.ProductFacade;
import com.albert.commerce.common.domain.DomainId;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/products", produces = MediaTypes.HAL_JSON_VALUE)
public class ProductQueryController {

    private final ProductFacade productFacade;

    @GetMapping
    public ResponseEntity<PagedModel<ProductResponse>> getAllProducts(Principal principal,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page
    ) {
        String userEmail = principal.getName();
        Pageable pageable = PageRequest.of(
                page == null ? 0 : page,
                size == null ? 0 : size
        );
        return ResponseEntity.ok(
                productFacade.findProductsByUserId(userEmail, pageable));
    }


    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable DomainId productId) {
        return ResponseEntity.ok(productFacade.findById(productId));
    }
}
